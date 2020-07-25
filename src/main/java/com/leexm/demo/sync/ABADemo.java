package com.leexm.demo.sync;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author leexm
 * @date 2019-09-17 11:06
 */
public class ABADemo {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(100);
        System.out.println("thread1 线程运行前，value:" + atomicInteger.get());
        Thread thread1 = new Thread(() -> {
            // 更新为200
            atomicInteger.compareAndSet(100, 200);
            // 更新为100
            atomicInteger.compareAndSet(200, 100);
        });
        thread1.start();
        thread1.join();
        System.out.println("thread1 线程运行后，value:" + atomicInteger.get());

        boolean flag = atomicInteger.compareAndSet(100, 500);
        System.out.println("是否更新成功:" + flag + ", 当前值:" + atomicInteger.get());
        System.out.println();

        AtomicStampedReference<Integer> atomicStampe = new AtomicStampedReference<>(100, 0);
        int stamp = atomicStampe.getStamp();
        System.out.println(String.format("thread2 线程运行前(%d, %d)", atomicStampe.getReference(), atomicStampe.getStamp()));
        Thread thread2 = new Thread(() -> {
            // 更新为200
            atomicStampe.compareAndSet(100, 101,
                    atomicStampe.getStamp(), atomicStampe.getStamp() + 1);
            // 更新为100
            atomicStampe.compareAndSet(101, 100,
                    atomicStampe.getStamp(), atomicStampe.getStamp() + 1);
        });
        thread2.start();
        thread2.join();
        System.out.println(String.format("thread2 线程运行后(%d, %d)", atomicStampe.getReference(), atomicStampe.getStamp()));

        flag = atomicStampe.compareAndSet(100, 500, stamp, stamp + 1);
        System.out.println(String.format("是否更新成功:%s，当前值(%d, %d)", flag, atomicStampe.getReference(), atomicStampe.getStamp()));
    }
}
