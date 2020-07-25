package com.leexm.demo.thread;

import java.util.concurrent.TimeUnit;

/**
 * @author leexm
 * @date 2020-04-29 13:31
 */
public class Shutdown {

    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
           while (!Thread.currentThread().isInterrupted()) {
               System.out.println("running....");
               try {
                   TimeUnit.MILLISECONDS.sleep(100);
               } catch (InterruptedException e) {
                   System.out.println("中断异常，worker 的中断状态:" + Thread.currentThread().isInterrupted());
                   e.printStackTrace();
                   // 恢复中断状态
                   Thread.currentThread().interrupt();
                   System.out.println("恢复中断状态，worker 的中断状态:" + Thread.currentThread().isInterrupted());
               }
           }
           System.out.println("线程被中断了");
        });
        worker.start();

        TimeUnit.SECONDS.sleep(1);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("worker 的线程状态:" + worker.getState());
            // 中断 thread 线程，即将中断标志位置为 true
            worker.interrupt();
            System.out.println("worker 的中断状态:" + worker.isInterrupted());
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("系统关闭，释放资源");
        }));
        System.exit(0);
    }

}
