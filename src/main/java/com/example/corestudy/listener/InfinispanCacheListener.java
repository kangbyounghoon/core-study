package com.example.corestudy.listener;


import org.infinispan.notifications.Listener;
import org.infinispan.notifications.cachemanagerlistener.annotation.CacheStarted;
import org.infinispan.notifications.cachemanagerlistener.event.CacheStartedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Listener
public class InfinispanCacheListener {
    private final static Logger logger = LoggerFactory.getLogger(InfinispanCacheListener.class);

    @CacheStarted
    public void print(CacheStartedEvent event) {
        logger.debug("New entry {} created in the cache", event.getCacheName());
    }
}
