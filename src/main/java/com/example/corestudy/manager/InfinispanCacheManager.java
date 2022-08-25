package com.example.corestudy.manager;

import com.example.corestudy.configuration.InfinispanConfiguration;
import com.example.corestudy.listener.InfinispanCacheListener;
import org.infinispan.Cache;
import org.infinispan.configuration.cache.CacheMode;
import org.infinispan.configuration.cache.ConfigurationBuilder;
import org.infinispan.configuration.cache.IndexStorage;
import org.infinispan.configuration.global.GlobalConfiguration;
import org.infinispan.configuration.global.GlobalConfigurationBuilder;
import org.infinispan.eviction.EvictionStrategy;
import org.infinispan.jboss.marshalling.commons.GenericJBossMarshaller;
import org.infinispan.lifecycle.ComponentStatus;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.transaction.TransactionMode;
import org.infinispan.util.concurrent.IsolationLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.concurrent.TimeUnit;


/**
 * CacheManager API는,
 * Infinispan 캐시와 상호 작용하기 위한 시작점입니다. 캐시 관리자는 캐시 수명 주기를 제어합니다. 캐시 인스턴스 생성, 수정 및 삭제
 * <p>
 * Infinispan 캐시 관리자는 다른 모드를 사용하는 여러 캐시를 만들고 제어할 수 있습니다.
 * 예를 들어, 로컬 캐시, 분산 캐시 및 무효화 모드의 캐시에 대해 동일한 캐시 관리자를 사용할 수 있습니다.
 */
@Service
public class InfinispanCacheManager {
    private final static Logger logger = LoggerFactory.getLogger(InfinispanCacheManager.class);

    private final DefaultCacheManager manager;

    private final InfinispanConfiguration infinispanConfiguration;

    @Autowired
    public InfinispanCacheManager(InfinispanConfiguration infinispanConfiguration) {
        this.infinispanConfiguration = infinispanConfiguration;

        GlobalConfigurationBuilder builder = new GlobalConfigurationBuilder();
        builder.cacheContainer().nonClusteredDefault();
        builder.serialization().marshaller(new GenericJBossMarshaller())
                .allowList().addRegexps("com.example.corestudy.*", "java.util.*", "java.sql.*");
        GlobalConfiguration global = builder.build();
        this.manager = new DefaultCacheManager(global);
        this.manager.addListener(new InfinispanCacheListener());
    }

    public <K, V> Cache<K, V> getCoreCache() {
        final String cacheType = "coreCache";
        Cache<K, V> cache = manager.getCache(cacheType, false);
        if (cache != null) {
            return cache;
        }

        manager.defineConfiguration(cacheType, new ConfigurationBuilder()
                .simpleCache(true)
//                .memory().evictionStrategy(EvictionStrategy.REMOVE).size(20000)
                .expiration().wakeUpInterval(30001).lifespan(36000000).maxIdle(36000000).build());
        cache = manager.getCache(cacheType);
        cache.start();
        return cache;
    }

    @PostConstruct
    public void init() {
//        logger.info("init coreCache:: {}", getCoreCache());
    }

    public synchronized <K, V> Cache<K, V> cacheInit(final String serviceId, final String cacheType, long size, Class clazz) {
        File cacheDir = infinispanConfiguration.getCachePath().toFile();
        if (!cacheDir.exists() || !cacheDir.canWrite()) {
            logger.error("Cache Repository Path Permission Error: {}", cacheDir.getAbsolutePath());
        } else {
            logger.info("Cache Repository Path : {}", cacheDir.getAbsolutePath());
        }

        manager.defineConfiguration(cacheType, new ConfigurationBuilder()
                .clustering().cacheMode(CacheMode.LOCAL)
                .memory().maxCount(size).whenFull(EvictionStrategy.REMOVE)
                .expiration().wakeUpInterval(-1)
                .persistence().passivation(false)
                .addSingleFileStore()
                .shared(false)
                .preload(false)
                .fetchPersistentState(false)
                .location(infinispanConfiguration.getCachePath().toString() + "/" + serviceId)
                .locking()
                .useLockStriping(true)
                .isolationLevel(IsolationLevel.READ_UNCOMMITTED)
                .lockAcquisitionTimeout(10, TimeUnit.SECONDS)
                .transaction().transactionMode(TransactionMode.NON_TRANSACTIONAL) //마이그 실행시 주석
                .indexing()
                .enable()
                .storage(IndexStorage.FILESYSTEM)
                .path(infinispanConfiguration.getCachePath().toString() + "/" + serviceId + "/" + cacheType)
                .addIndexedEntity(clazz)
                .addProperty("hibernate.search.backend.analysis.configurer", "class:com.example.corestudy.schema.query.lucene.AnalysisConfigurer")
                .writer().commitInterval(1000)
                .build());

        logger.info("cache init : " + cacheType);
        Cache<K, V> cache = manager.getCache(cacheType);
        cache.start();
        logger.info("cache start : " + cacheType + " " + cache.size());
        return cache;
    }

    public ComponentStatus getStatus() {
        return this.manager.getStatus();
    }
}
