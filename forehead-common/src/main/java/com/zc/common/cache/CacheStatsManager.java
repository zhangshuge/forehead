package com.zc.common.cache;

import com.google.common.cache.CacheStats;
import com.zc.common.util.SpringContextUtil;
import com.zc.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Cache缓存管理工具类，负责监控缓存操作
 * Created by zhangchi9 on 2016/12/21.
 */
public class CacheStatsManager {

    @Autowired
    private SpringContextUtil springContextUtil;
    //定义LoadingCache对象数组，可以根据cacheName获取cache对象
    public static Map<String, ? extends AbstractLoadingCache<Object, Object>> cacheMaps;

    /**
     * 获取所有AbstractLoadingCache子类实例对象
     *
     * @return
     */
    public static Map<String, ? extends AbstractLoadingCache<Object, Object>> getCacheMaps() {
        if (cacheMaps == null) {
            cacheMaps = (Map<String, ? extends AbstractLoadingCache<Object, Object>>)
                    SpringContextUtil.getBeanOfType(AbstractLoadingCache.class);
        }
        return cacheMaps;
    }

    /**
     * 获取所有缓存的名字（即缓存实现类的名称）
     *
     * @return
     */
    public static Set<String> getCacheNames() {
        return getCacheMaps().keySet();
    }

    /**
     * 根据cacheName获取cache对象
     *
     * @param cacheName
     * @return
     */
    public static AbstractLoadingCache<Object, Object> getAbstractLoadingCache(String cacheName) {
        return (AbstractLoadingCache<Object, Object>) getCacheMaps().get(cacheName);
    }

    /**
     * 返回一个缓存的统计数据
     *
     * @param cacheName
     * @return Map<统计指标，统计数据>
     */
    public static Map<String, Object> getCacheStatsToMap(String cacheName) {
        Map<String, Object> map = new LinkedHashMap<>();
        AbstractLoadingCache<Object, Object> cache = getAbstractLoadingCache(cacheName);
        CacheStats cs = cache.getCache().stats();
        NumberFormat percent = NumberFormat.getPercentInstance(); // 建立百分比格式化用
        percent.setMaximumFractionDigits(1); // 百分比小数点后的位数
        map.put("cacheName", cacheName);//缓存名称
        map.put("size", cache.getCache().size());//已缓存容量

        Map<String, String> specMap = StringUtils.strintToMapByStandard(cache.getSpec());
        map.put("maximumSize", specMap.get("maximumSize"));//缓存最大容量
        if (specMap.get("expireAfterWrite") != null) {
            map.put("survivalDuration", specMap.get("expireAfterWrite"));//存在时长
        }
        map.put("hitCount", cs.hitCount());//命中数
        map.put("hitRate", percent.format(cs.hitRate()));//命中率
        map.put("missRate", percent.format(cs.missRate()));//未命中率
        map.put("loadSuccessCount", cs.loadSuccessCount());//缓存查找方法成功加载新值的次数
        map.put("loadExceptionCount", cs.loadExceptionCount());//缓存查找方法在加载新值时抛出异常的次数。
        map.put("totalLoadTime", cs.totalLoadTime() / 1000000);  //缓存已加载新值所用的纳秒ms总数。
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (cache.getResetTime() != null) {//缓存初始化创建时间
            map.put("resetTime", df.format(cache.getResetTime()));
        }
        map.put("highestSize", cache.getHighestSize());//最大历史容量即条目数
        if (cache.getHighestTime() != null) {//缓存数据的创建和修改时间
            map.put("highestTime", df.format(cache.getHighestTime()));
        }
        return map;
    }

    /**
     * 返回所有缓存的统计数据
     *
     * @return
     */
    public static ArrayList<Map<String, Object>> getAllCacheStats() {
        Map<String, ? extends Object> cacheMap = getCacheMaps();
        List<String> cacheNameList = new ArrayList<String>(cacheMap.keySet());
        Collections.sort(cacheNameList);//按照字母排序

        //遍历所有缓存，获取统计数据
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (String cacheName : cacheNameList) {
            list.add(getCacheStatsToMap(cacheName));
        }

        return list;
    }
}
