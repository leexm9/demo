package com.leexm.demo.sync;

import java.util.concurrent.*;

/**
 * 举个报旅行团旅行的例子
 * 出发时，导游会在机场收了护照和签证，办理集体出境手续，所以，要等大家都到齐才能出发，出发前再把护照和签证发到大家手里。
 * 对应CyclicBarrier使用。
 * 每个人到达后进入barrier状态。
 * 都到达后，唤起大家一起出发去旅行。
 * 旅行出发前，导游还会有个发护照和签证的动作。
 *
 * @author leexm
 * @date 2019-09-20 00:42
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new TourGuideTask());
        ExecutorService executor = Executors.newFixedThreadPool(3);
        //登哥最大牌，到的最晚
        executor.execute(new TravelTask(cyclicBarrier, "哈登", 5));
        executor.execute(new TravelTask(cyclicBarrier, "保罗", 3));
        executor.execute(new TravelTask(cyclicBarrier, "戈登", 1));

        TimeUnit.SECONDS.sleep(5);
        executor.shutdown();
    }

    /**
     * 旅行线程
     */
    static class TravelTask implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private String name;
        //赶到的时间
        private int arriveTime;

        public TravelTask(CyclicBarrier cyclicBarrier, String name, int arriveTime) {
            this.cyclicBarrier = cyclicBarrier;
            this.name = name;
            this.arriveTime = arriveTime;
        }

        @Override
        public void run() {
            try {
                //模拟达到需要花的时间
                TimeUnit.SECONDS.sleep(1);
                System.out.println(name + "到达集合点");
                cyclicBarrier.await();
                System.out.println(name + "开始旅行啦～～");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导游线程，都到达目的地时，发放护照和签证
     */
    static class TourGuideTask implements Runnable {
        @Override
        public void run() {
            System.out.println("****导游分发护照签证****");
            try {
                //模拟发护照签证需要2秒
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
