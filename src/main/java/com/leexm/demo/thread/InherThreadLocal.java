package com.leexm.demo.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author leexm
 * @date 2020-04-15 08:13
 */
public class InherThreadLocal {

    public static void main(String[] args) throws InterruptedException {
        InheritableThreadLocal<Person> inheritableThreadLocal = new InheritableThreadLocal<>();
        ThreadLocal<Person> threadLocal = new ThreadLocal<>();
        AtomicReference<Thread> sonThread = new AtomicReference<>();
        Thread thread1 = new Thread(() -> {
            inheritableThreadLocal.set(new Person(30));
            System.out.println("father: " + inheritableThreadLocal.get());
            sonThread.set(new Thread(() -> {
                threadLocal.set(inheritableThreadLocal.get());
                while (true) {
                    System.out.println("son: " + inheritableThreadLocal.get());
                    System.out.println("son local:" + threadLocal.get());
                    try {
                        TimeUnit.MILLISECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }));
            System.out.println("father: " + inheritableThreadLocal.get());
            try {
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            inheritableThreadLocal.get().setAge(40);
            System.out.println("father: " + inheritableThreadLocal.get());
        });
        thread1.start();

        boolean flag = false;
        while (!flag) {
            if (sonThread.get() != null) {
                sonThread.get().start();
                flag = true;
            }
        }
        TimeUnit.SECONDS.sleep(1);
    }

    static class Person {
        private int age;

        public Person(int age) {
            this.age = age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "age=" + age +
                    '}';
        }
    }

}
