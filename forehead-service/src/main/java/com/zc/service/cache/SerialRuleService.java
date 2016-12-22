package com.zc.service.cache;

import com.zc.common.cache.LocalCache;

import java.util.Map;

/**
 * Created by zhangchi9 on 2016/12/20.
 */
public interface SerialRuleService<String, Object> extends LocalCache<String, String> {
    /**
     * 显式删除缓存
     *
     * @param cacheName
     */
    void resetCacheAll(String cacheName);

    Map<String, Object> getCacheStats(String cacheName);
}
