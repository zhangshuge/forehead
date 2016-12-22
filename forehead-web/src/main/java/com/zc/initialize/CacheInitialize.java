package com.zc.initialize;

import com.zc.common.cache.AbstractLoadingCache;
import com.zc.common.cache.CacheStatsManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;


/**
 * 项目启动后初始化缓存
 * Created by zhangchi9 on 2016/12/22.
 */
@Component
public class CacheInitialize implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            Set<String> cacheSets = CacheStatsManager.getCacheNames();
            for (String impl : cacheSets) {
                AbstractLoadingCache abstractLoadingCache = CacheStatsManager.getAbstractLoadingCache(impl);
                abstractLoadingCache.putAll();
            }
        }
    }
}
