package com.zc.initialize;

import com.zc.common.cache.AbstractLoadingCache;
import com.zc.common.cache.CacheStatsManager;
import com.zc.common.cache.LocalCache;
import com.zc.common.util.SpringContextUtil;
import com.zc.service.cache.impl.SerialRuleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentMap;

/**
 * Created by zhangchi9 on 2016/12/22.
 */
@Component
public class CacheInitialize implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            SerialRuleServiceImpl serialRuleServiceImpl = SpringContextUtil.getBean("serialRuleServiceImpl");
            serialRuleServiceImpl.putAll();
            AbstractLoadingCache a = CacheStatsManager.getAbstractLoadingCache("serialRuleServiceImpl");
            ConcurrentMap<Object, Object> cmap =  a.getCache().asMap();
            System.out.println("-----------------");
        }
    }
}
