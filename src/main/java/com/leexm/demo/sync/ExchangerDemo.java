package com.leexm.demo.sync;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Exchanger;

/**
 * @author leexm
 * @date 2020-07-07 01:09
 */
public class ExchangerDemo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(() -> {
            String s = "T1";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s - %s", Thread.currentThread().getName(), s));
            countDownLatch.countDown();
        }, "T1").start();

        new Thread(() -> {
            String s = "T2";
            try {
                s = exchanger.exchange(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s - %s", Thread.currentThread().getName(), s));
            countDownLatch.countDown();
        }, "T2").start();

        countDownLatch.await();
        System.out.println("end");
    }

}
