package com.leexm.demo.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * 虚引用中引用队列的作用，即虚引用特点二
 * -Xmx10m
 *
 * @author leexm
 * @date 2020-04-14 21:28
 */
public class PhantomQueue {

    public static void main(String[] args) throws InterruptedException {
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<TestReference.Person> phantomReference =
                new PhantomReference<>(new TestReference.Person("Tom"), queue);
        CompletableFuture.runAsync(() -> {
            boolean flag = false;
            while (!flag) {
                // gc 发生时，会将虚引用入队，这里拿到的就是虚引用本身，上文中的 phantomReference
                Reference reference = queue.poll();
                if (reference != null) {
                    System.out.println("虚引用被回收了：" + reference);
                    flag = true;
                }
            }
            System.out.println("线程退出");
        });

        // 线程 new 对象，导致 gc
        List<byte[]> list = new ArrayList<>();
        CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 20; i++) {
                System.out.println("......add......");
                list.add(new byte[1024 * 1204 * 1]);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("hello, world!");
    }

}
