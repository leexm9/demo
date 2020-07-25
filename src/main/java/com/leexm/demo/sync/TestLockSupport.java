package com.leexm.demo.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author leexm
 * @date 2019-09-17 14:50
 */
public class TestLockSupport {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                System.out.println(sum + "----------");
                LockSupport.park();
                sum += i;
            }
            System.out.println("计算完成......");
            LockSupport.park();
            System.out.println("result:" + sum);
        });

        thread.start();
        for (int i = 0; i < 11; i++) {
            TimeUnit.SECONDS.sleep(1);
            LockSupport.unpark(thread);
        }
//        System.out.println("中断");
//        thread.interrupt();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("main");
    }

}
