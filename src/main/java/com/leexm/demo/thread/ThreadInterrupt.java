package com.leexm.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author leexm
 * @date 2020-04-29 14:22
 */
public class ThreadInterrupt {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            System.out.println("Worker started.");
            boolean f; // 用于检测 interrupted() 第一次返回值
            int i = 0;
            Thread c = Thread.currentThread();
            System.out.println("while之前线程中断状态isInterrupted()：" + c.isInterrupted());
            /**
             * 判断是否中断，如果中断，那么跳出循环
             * interrupted() 会返回当前的中断状态，并重置中断状态为 false
             */
            while (!(f = Thread.interrupted())) {
                System.out.println("while内，还没中断，interrupted()返回值为：" + f);
                System.out.println(c.getName() + "  " + i++ + "  " + c.isInterrupted());
            }
            System.out.println("跳出循环即第一次中断interrupted()返回值：" + f);
            // 为false，因为 interrupted() 会清除中断标志位，显示为未中断
            System.out.println("while之后线程中断状态isInterrupted():" + c.isInterrupted());
            System.out.println("第二次及以后的interrupted()返回值：" + Thread.interrupted());
            c.interrupt();
            System.out.println("再次中断后查询中断状态isInterrupted():" + c.isInterrupted());
            System.out.println("Worker stopped.");
        });
        t.start();

        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t.interrupt();
        System.out.println("Main thread stopped.");
    }

}
