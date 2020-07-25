package com.leexm.demo.sync;

/**
 * @author leexm
 * @date 2019-09-17 14:57
 */
public class TestObjWait {

    public static void main(String[] args) throws InterruptedException {
        final Object obj = new Object();
        Thread thread = new Thread(() -> {
            int sum = 0;
            for (int i = 0; i < 10; i++) {
                sum += i;
            }
            System.out.println("计算完成......");
            try {
                synchronized(obj) {
                    obj.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("result:" + sum);
        });
        thread.start();
        Thread.sleep(5000);

        synchronized(obj) {
            obj.notify();
        }

    }

}
