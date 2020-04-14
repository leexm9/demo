package com.leexm.demo.thread;

/**
 * 验证线程 waiting、timed_waiting 到 blocked 状态的切换
 *
 * @author leexm
 * @date 2020-04-10 10:32
 */
public class ThreadState {

    public static void main(String[] args) {
        System.out.println("Hello World");
        Object lock = new Object();
        Thread threadA = new Thread(new RunA(lock), "A");
        threadA.start();
        getThreadState(threadA);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        Thread threadB = new Thread(new RunB(lock), "B");
        threadB.start();
        getThreadState(threadB);

        int cnt = 0;
        while (cnt < 5) {
            getThreadState(threadA);
            getThreadState(threadB);
            cnt++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    private static void getThreadState(Thread t) {
        System.out.println(t.getName() + " thread state: " + t.getState());
    }

    static class RunA implements Runnable {

        private Object lock;

        public RunA(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

            synchronized (lock) {
                try {
                    System.out.println("A begin");
                    lock.wait(2000); // 等待了2秒之后，继续执行下去
                    System.out.println("A end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class RunB implements Runnable {

        private Object lock;

        public RunB(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {

            synchronized (lock) {
                System.out.println("b come");
                while (true) {
                }
            }
        }
    }

}
