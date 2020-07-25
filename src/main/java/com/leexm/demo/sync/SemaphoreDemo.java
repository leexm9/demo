package com.leexm.demo.sync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 老师需要班上10个学生到讲台上填写一张表，但是老师只有5支笔，因此同一时刻只能保证5个学生拿到笔进行填写，
 * 没有拿到笔的学生只能等前面的学生填写完毕，再去拿笔进行填写。
 *
 * @author leexm
 * @date 2019-09-20 14:02
 */
public class SemaphoreDemo {

    /** 5支笔 */
    private static Semaphore semaphore = new Semaphore(5, true);

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(10);
        // 10个学生
        for (int i = 0; i < 10; i++) {
            service.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 同学想要拿到笔===");
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 同学拿到笔---");
                    System.out.println(Thread.currentThread().getName() + " 同学填写中...");
                    TimeUnit.SECONDS.sleep(2);
                    System.out.println(Thread.currentThread().getName() + " 同学填写完毕，马上归还笔。。。");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }
        service.shutdown();
    }

}
