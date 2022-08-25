package com.example.corestudy.initialize;

import com.example.corestudy.manager.InfinispanCacheManager;
import com.example.corestudy.node.Node;
import org.hibernate.Transaction;
import org.infinispan.Cache;
import org.infinispan.query.Search;
import org.infinispan.query.dsl.Query;
import org.infinispan.query.dsl.QueryFactory;
import org.infinispan.query.dsl.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Service
public class InitializeService {

    private final static Logger logger = LoggerFactory.getLogger(InitializeService.class);

    private final InfinispanCacheManager manager;

    @Autowired
    public InitializeService(InfinispanCacheManager manager) {
        this.manager = manager;
    }

    @PostConstruct
    public void init() {
        logger.info("init coreCache:: {}", this.manager.getCoreCache());
        logger.info("initializeService cacheManager Status:: {}", this.manager.getStatus());
        Cache<String, Node> core_service = this.manager.cacheInit("core", "core_service", 100000, Node.class);
        logger.info("init core_service:: {}", core_service);
        if (core_service.size() == 0) {
            Node node01 = new Node();
            node01.setNodeId("1");
            node01.setTypeId("2");
            node01.setOwner("3");
            node01.setModifier("4");
            core_service.putIfAbsent("node01", node01);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        logger.info("core_service {}", core_service);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        QueryFactory queryFactory = Search.getQueryFactory(core_service);
        Query<Node> query = queryFactory.create("from com.example.corestudy.node.Node where nodeId = 1");
        QueryResult<Node> queryResult = query.execute();
        logger.info("queryResult Node:: {}", queryResult);
    }
}
