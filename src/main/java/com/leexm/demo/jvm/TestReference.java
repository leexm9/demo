package com.leexm.demo.jvm;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * Java 中四种引用生存时间测试
 *
 * @author leexm
 * @date 2020-04-14 19:07
 */
public class TestReference {

    public static void main(String[] args) throws InterruptedException {

        /**
         * 强引用
         */
        System.out.println("-----------强引用-----------");
        Person person = new Person("Tom");
        // person 强引用在，即使发生 OOM 异常也不会将对象回收。强引用与对象之间断开连接，gc 时对象就会被回收
        person = null;
        // 通知 jvm 进行垃圾回收，但 jvm 不一定会立即执行
        System.gc();
        TimeUnit.MILLISECONDS.sleep(10);

        /**
         * 软引用，当内存不足，会触发 jvm 的 gc，如果 gc 后内存还是不足，就会把软引用的包裹的对象给干掉
         * 也就是只有在内存不足，jvm 才会回收该对象
         *
         * 测试这个时 -Xmx20m
         */
        System.out.println("\r\n-----------软引用-----------");
        SoftReference<byte[]> softReference = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(softReference.get());
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println(softReference.get());
        // 内存不足，导致 gc
        byte[] bytes = new byte[1024 * 1024 * 10];
        System.out.println(softReference.get());
        System.out.println(softReference);

        /**
         * 弱引用，不管内存是否足够，只要发生 gc，弱引用引用的对象都会被回收，弱引用自身还在
         */
        System.out.println("\r\n-----------弱引用-----------");
        WeakReference<byte[]> weakReference = new WeakReference<>(new byte[1024 * 1024 * 5]);
        System.out.println(weakReference.get());
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println(weakReference.get());
        System.out.println(weakReference);

        /**
         * 虚引用，特点：
         * 1、无法通过虚引用来获取对一个对象的真实引用
         * 2、虚引用必须与 ReferenceQueue 一起使用，当 gc 准备回收一个对象，如果发现它还有虚引用，
         *  就会在回收之前，把这个虚引用加入到与之关联的 ReferenceQueue 中，gc 后虚引用连自身也不存在了
         */
        System.out.println("\r\n-----------虚引用-----------");
        ReferenceQueue queue = new ReferenceQueue();
        PhantomReference<byte[]> phantomReference = new PhantomReference<>(new Person("Helen"), queue);
        // 特点一，无非通过虚引用得到对象
        System.out.println(phantomReference.get());
        System.gc();
        TimeUnit.MILLISECONDS.sleep(100);
    }

    static class Person {

        private String name;

        public Person(String name) {
            this.name = name;
        }

        /**
         * 实际开发中不要重写该方法，可以借助 Spring 的 DisposableBean 接口等实现
         *
         * @throws Throwable
         */
        @Override
        protected void finalize() throws Throwable {
            System.out.println("Person 被回收了");
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

}
