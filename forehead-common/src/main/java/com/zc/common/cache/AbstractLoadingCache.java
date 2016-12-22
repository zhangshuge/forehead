package com.zc.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * 抽象缓存模块
 * Created by zhangchi9 on 2016/12/20.
 */
public abstract class AbstractLoadingCache<K, V> {

    private LoadingCache<K, V> loadingCache;//定义LoadingCache接口
    private Date resetTime;     //Cache初始化或被重置的时间
    private Date highestTime;   //创造缓存具体数据条目记录的时间
    private String spec = null;//定义缓存配置属性变量
    private long highestSize = 0;//历史最高记录数

    /**
     * 初始化缓存对象
     *
     * @return
     */
    public LoadingCache<K, V> getCache() {
        if (loadingCache == null) {//双重校验锁保证只有一个cache实例
            synchronized (this) {
                if (loadingCache == null) {
                    loadingCache = CacheBuilder.from(spec)
                            .recordStats()//开启缓存统计
                            .build(new CacheLoader<K, V>() {
                                @Override
                                public V load(K k) throws Exception {
                                    return fetchData(k);
                                }
                            });
                    this.resetTime = new Date();
                    this.highestTime = new Date();
                }
            }
        }
        return loadingCache;
    }

    /**
     * 封装LoadingCache.get(key)方法调用
     * 根据key 获取value值，可以扩展当value结果为null时的处理逻辑
     *
     * @param key
     * @return
     * @throws ExecutionException
     */
    protected V getValue(K key) throws ExecutionException {
        V result = getCache().get(key);

        if (getCache().size() < 0) {
            //扩展当缓存为空时的处理逻辑
        }
        if (result instanceof Objects) {
            //扩展返回结果对象的处理逻辑
        }
        if (getCache().size() > highestSize) {
            this.highestSize = getCache().size();
        }
        return result;
    }

    protected void putAllValue(Map<K, V> map) {
        getCache().putAll(map);
    }

    /**
     * 定义抽象方法由具体子类实现重写，用于CacheBuilder的回调方法load()函数加载缓存
     *
     * @param k
     * @return
     */
    protected abstract V fetchData(K k);

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Date getResetTime() {
        return resetTime;
    }

    public void setResetTime(Date resetTime) {
        this.resetTime = resetTime;
    }

    public Date getHighestTime() {
        return highestTime;
    }

    public void setHighestTime(Date highestTime) {
        this.highestTime = highestTime;
    }

    public long getHighestSize() {
        return highestSize;
    }

    public void setHighestSize(long highestSize) {
        this.highestSize = highestSize;
    }


}
