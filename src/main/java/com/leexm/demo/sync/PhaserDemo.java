package com.leexm.demo.sync;

import java.util.concurrent.Phaser;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 分阶段的栅栏
 *
 * @author leexm
 * @date 2020-07-07 00:37
 */
public class PhaserDemo {

    public static void main(String[] args) {
        MarriagePhaser phaser = new MarriagePhaser();
        phaser.bulkRegister(7);
        for (int i = 0; i < 5; i++) {
            new Thread(new Person("P" + i, phaser)).start();
        }
        new Thread(new Person("新郎", phaser)).start();
        new Thread(new Person("新娘", phaser)).start();
    }

    static class MarriagePhaser extends Phaser {

        @Override
        protected boolean onAdvance(int phase, int registeredParties) {
            switch (phase) {
                case 0:
                    System.out.println("所有人都到期了!" + registeredParties);
                    return false;
                case 1:
                    System.out.println("所有人都吃完了!" + registeredParties);
                    return false;
                case 2:
                    System.out.println("所有人都离开了!" + registeredParties);
                    return false;
                case 3:
                    System.out.println("婚礼结束！新郎、新娘彼此相拥!" + registeredParties);
                    return true;
                default:
                    return true;
            }
        }
    }

    static class Person implements Runnable {

        private String name;

        private Phaser phaser;

        public Person(String name, Phaser phaser) {
            this.name = name;
            this.phaser = phaser;
        }

        public void arrive() {
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s 到达现场!", name));
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s 吃完饭!", name));
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            try {
                TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("%s 离开!", name));
            phaser.arriveAndAwaitAdvance();
        }

        public void hug() {
            if (name.equals("新郎") || name.equals("新娘")) {
                try {
                    TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("%s 到达现场!", name));
                phaser.arriveAndAwaitAdvance();
            } else {
                phaser.arriveAndDeregister();
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }

    }

}
