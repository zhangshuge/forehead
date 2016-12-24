package com.zc.demo.zookeeper;

import org.apache.log4j.Logger;
import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by zhangchi9 on 2016/12/23.
 */
public class JavaApiSample implements Watcher {

    Logger logger = Logger.getLogger(JavaApiSample.class);

    private ZooKeeper zooKeeper = null;

    //同步工具类，它允许一个或多个线程一直等待，直到其他线程的操作执行完后再执行。
    private CountDownLatch connectedSemaphore = new CountDownLatch(1);

    /**
     * 收到来自server的watcher通知的处理
     *
     * @param event
     */
    @Override
    public void process(WatchedEvent event) {
        logger.info("收到观察者watcher信息通知：" + event.getState());
        if (Event.KeeperState.SyncConnected == event.getState()) {
            connectedSemaphore.countDown();
        }
    }

    /**
     * 创建zookeeper链接
     *
     * @param connectString  zookeeper服务器链接地址列表
     * @param sessionTimeOut 超时时间
     */
    public void createConnection(String connectString, int sessionTimeOut) {
        try {
            zooKeeper = new ZooKeeper(connectString, sessionTimeOut, this);
            connectedSemaphore.await();
        } catch (IOException e) {
            logger.error("zookper链接创建异常");
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.error("zookper链接创建异常");
            e.printStackTrace();
        }
    }

    /**
     * 关闭zookeeper
     */
    public void releaseConnection() {
        if (zooKeeper != null) {
            try {
                zooKeeper.close();
            } catch (InterruptedException e) {
                logger.error("zookeeper链接关闭操作失败");
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建节点
     *
     * @param path 节点路径
     * @param data 数据内容
     * @return
     */
    public boolean createPath(String path, String data) {
        boolean flag = false;
        try {
            /**
             * CreateMode.PERSISTENT：客户端断开连接时，znode不会自动删除。
             */
            zooKeeper.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            flag = true;
        } catch (KeeperException e) {
            logger.error("创建节点失败");
            e.printStackTrace();
        } catch (InterruptedException e) {
            logger.error("创建节点失败");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 读取指定节点数据内容
     *
     * @param path 节点path
     * @return
     */
    public String readData(String path) {
        try {
            logger.info("获取数据成功，path：" + path);
            return new String(this.zooKeeper.getData(path, false, null));
        } catch (KeeperException e) {
            System.out.println("读取数据失败，发生KeeperException，path: " + path);
            e.printStackTrace();
            return "";
        } catch (InterruptedException e) {
            System.out.println("读取数据失败，发生 InterruptedException，path: " + path);
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取节点
     *
     * @param path
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChild(String path) {
        List<String> list = null;
        try {
            list = this.zooKeeper.getChildren(path, false);
            if (list.isEmpty()) {
                logger.debug(path + "中没有节点");
            } else {
                logger.debug(path + "中存在节点");
                for (String child : list) {
                    logger.debug("节点为：" + child);
                }
            }
        } catch (KeeperException.NoNodeException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
        return list;
    }
}
