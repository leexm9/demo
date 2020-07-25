package com.leexm.demo.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author leexm
 * @date 2020-07-07 08:03
 */
public class LockSupportDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 5 || i == 8) {
                    LockSupport.park();
                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
        LockSupport.unpark(t);
        TimeUnit.SECONDS.sleep(20);
        LockSupport.unpark(t);
        TimeUnit.SECONDS.sleep(2);
    }

}
