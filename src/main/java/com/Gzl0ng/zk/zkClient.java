package com.Gzl0ng.zk;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author 郭正龙
 * @date 2021-10-20
 */
public class zkClient {

    private String connectString = "node1:2181,node2:2181,node3:2181";
    private int sessionTimeout = 2000;
    private ZooKeeper zkClient;

    //创建客户端
    @Test
    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {

                System.out.println("-------------------------");
                List<String> children = null;
                try {
                    children = zkClient.getChildren("/", true);

                    for (String child : children) {
                        System.out.println(child);
                    }

                    System.out.println("-------------------------");
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //创建子节点
    @Test
    public void create() throws InterruptedException, KeeperException {
        String nodeCreated = zkClient.create("/atguigu", "ss.avi".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

    }

    //监听节点变化
    @Test
    public void getChildren() throws InterruptedException, KeeperException {
        List<String> children = zkClient.getChildren("/", true);

        for (String child : children) {
            System.out.println(child);
        }

        //延时
        Thread.sleep(Long.MAX_VALUE);
    }

    //判断是否节点是否存在
    @Test
    public void exist() throws InterruptedException, KeeperException {
        Stat stat = zkClient.exists("/atguigu", false);

        System.out.println(stat==null ? "not exist" : "exist");
        //会遍历/下的所有节点,判断是否存在相当于监听一次会调用process
    }
}
