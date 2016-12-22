package com.zc.common.cache;

import java.util.Map;

/**
 * 定义统一缓存接口
 * Created by zhangchi9 on 2016/12/20.
 */
public interface LocalCache<K, V> {
    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public V get(K key);

    /**
     * 初始化缓存时显式插入缓存
     */
    public void putAll();
}
