package com.zc.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangchi9 on 2016/12/21.
 */
public class StringUtils {
    /**
     * 用于将字符串按照统一的格式转换成map
     *
     * @param spec 例如spec = "maximumSize=10000,expireAfterWrite=5s";
     * @return
     */
    public static Map<String, String> strintToMapByStandard(String spec) {
        Map<String, String> resultMap = new HashMap<String, String>();
        String[] strs = spec.split(",");
        for (int i = 0; i < strs.length; i++) {
            String[] sm = strs[i].split("=");
            resultMap.put(sm[0], sm[1]);
        }
        return resultMap;
    }
}
