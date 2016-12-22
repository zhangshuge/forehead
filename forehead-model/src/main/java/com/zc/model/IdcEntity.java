package com.zc.model;

import java.util.Arrays;

/**
 * Created by zhangchi9 on 2016/12/22.
 */
public class IdcEntity {
    private String idc;
    private int[] db;
    private int[] table;

    public String getIdc() {
        return idc;
    }

    public void setIdc(String idc) {
        this.idc = idc;
    }

    public int[] getDb() {
        return db;
    }

    public void setDb(int[] db) {
        this.db = db;
    }

    public int[] getTable() {
        return table;
    }

    public void setTable(int[] table) {
        this.table = table;
    }
}
