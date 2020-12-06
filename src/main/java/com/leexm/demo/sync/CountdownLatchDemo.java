package com.leexm.demo.sync;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author leexm
 * @date 2019-09-19 17:11
 */
public class CountdownLatchDemo {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        ExecutorService service = Executors.newCachedThreadPool();
        final CountDownLatch cdOrder = new CountDownLatch(1);
        final CountDownLatch cdAnswer = new CountDownLatch(4);
        for (int i = 0; i < 4; i++) {
            service.execute(() -> {
                try {
                    System. out.println("选手" + Thread.currentThread().getName() + "正等待裁判发布口令");
                    cdOrder.await();
                    System. out.println("选手" + Thread.currentThread().getName() + "已接受裁判口令");
                    TimeUnit.SECONDS.sleep(random.nextInt(10));
                    System. out.println("选手" + Thread.currentThread().getName() + "到达终点");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cdAnswer.countDown();
                }
            });
        }

        TimeUnit.SECONDS.sleep(random.nextInt(10));

        System. out.println("裁判" + Thread.currentThread ().getName() + "即将发布口令" );
        cdOrder.countDown();
        System. out.println("裁判" + Thread.currentThread ().getName() + "已发送口令，正在等待所有选手到达终点" );

        cdAnswer.await();
        System. out.println("所有选手都到达终点" );
        System. out.println("裁判" + Thread.currentThread ().getName() + "汇总成绩排名" );

        service.shutdown();
    }

}
