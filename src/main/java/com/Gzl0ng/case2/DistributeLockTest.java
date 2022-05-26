package com.Gzl0ng.case2;

import org.apache.zookeeper.KeeperException;

import java.io.IOException;

/**
 * @author 郭正龙
 * @date 2021-10-21
 */
public class DistributeLockTest {
    public static void main(String[] args) throws IOException, InterruptedException, KeeperException {
        final DistributeLock lock1 = new DistributeLock();

        final DistributeLock lock2 = new DistributeLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock1.zkLock();
                    System.out.println("线程1 启动 获取到锁");
                    Thread.sleep(5 * 1000);

                    lock1.unZkLock();
                    System.out.println("线程1 释放锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock2.zkLock();
                    System.out.println("线程2 启动 获取到锁");
                    Thread.sleep(5 * 1000);

                    lock2.unZkLock();
                    System.out.println("线程2 释放锁");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
