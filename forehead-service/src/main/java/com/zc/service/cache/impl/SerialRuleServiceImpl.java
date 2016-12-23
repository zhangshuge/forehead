package com.zc.service.cache.impl;

import com.zc.common.cache.AbstractLoadingCache;
import com.zc.common.cache.CacheStatsManager;
import com.zc.service.cache.SerialRuleService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhangchi9 on 2016/12/20.
 */
@Service("SerialRuleServiceImpl")
public class SerialRuleServiceImpl extends AbstractLoadingCache<String, String> implements SerialRuleService<String, Object> {
    public SerialRuleServiceImpl() {
        super.setSpec("maximumSize=10000,expireAfterWrite=5m");
    }

    @Override
    public String get(String key) {
        String value = null;
        try {
            value = getValue(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取数据来源方法
     *
     * @param s
     * @return
     */
    @Override
    protected String fetchData(String s) {
        super.setHighestTime(new Date());//新增数据是记录创建时间
        return "data is not null !";
    }

    @Override
    public void putAll() throws Exception{
        //配置中心获取缓存配置
        Map<String, String> map = new HashMap<String, String>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        super.putAllValue(map);
    }


    @Override
    public void resetCacheAll(String cacheName) {
        AbstractLoadingCache<Object, Object> cache = CacheStatsManager.getAbstractLoadingCache(cacheName);
        cache.getCache().invalidateAll();
        cache.setResetTime(new Date());
    }

    @Override
    public Map<String, Object> getCacheStats(String cacheName) {
        return CacheStatsManager.getCacheStatsToMap(cacheName);
    }
}
