package com.zc.demo.zookeeper;

/**
 * Created by zhangchi9 on 2016/12/23.
 */
public class JavaApiSampleTest {
    public static void main(String[] args) {
        JavaApiSample jas = new JavaApiSample();
        //jas.createConnection("192.168.133.128:2181",10000);
        jas.createConnection("192.168.133.128:2181",10000);
        String path = "/hh";
        if (jas.createPath(path,"22222")){
            System.out.println("获取节点："+jas.getChild(path));
            System.out.println("读取节点内容:"+jas.readData(path));
        }
        //jas.releaseConnection();
    }
}
