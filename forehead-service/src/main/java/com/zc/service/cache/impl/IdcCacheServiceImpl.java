package com.zc.service.cache.impl;

import com.zc.common.cache.AbstractLoadingCache;
import com.zc.model.IdcEntity;
import com.zc.service.cache.IdcCacheService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Created by zhangchi9 on 2016/12/22.
 */
@Service("IdcCacheServiceImpl")
public class IdcCacheServiceImpl extends AbstractLoadingCache<String, IdcEntity> implements IdcCacheService {
    public IdcCacheServiceImpl() {
        super.setSpec("maximumSize=10000,expireAfterWrite=5m");
    }

    @Override
    public IdcEntity get(String key) {
        IdcEntity value = null;
        try {
            value = getValue(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return value;
    }

    @Override
    public void putAll() {
        IdcEntity idcEntity = new IdcEntity();
        idcEntity.setIdc("00");
        int[] db = new int[]{00, 01, 02};
        int[] table = new int[]{00, 01, 02};
        idcEntity.setDb(db);
        idcEntity.setTable(table);
        Map<String, IdcEntity> map = new HashMap<String, IdcEntity>();
        map.put("000010", idcEntity);
        super.putAllValue(map);
        System.out.println("----- idc cache is initialize ---------");
    }


    @Override
    protected IdcEntity fetchData(String s) {
        super.setHighestTime(new Date());//新增数据是记录创建时间
        return new IdcEntity();
    }
}
