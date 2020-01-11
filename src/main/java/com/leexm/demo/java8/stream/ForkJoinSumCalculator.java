package com.leexm.demo.java8.stream;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/**
 * @author leexm
 * @date 2020-01-05 00:09
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private static final long serialVersionUID = -8724664570020388008L;

    /**
     * 任务不在分的数组大小
     */
    private static final long THREAD_HOLD = 10000;

    /**
     * 要求和的数组
     */
    private long[] numbers;
    /**
     * 子任务处理数组的起始位置
     */
    private int start;
    private int end;

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers, 0, numbers.length);
    }

    public ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THREAD_HOLD) {
            return this.computeSequentially();
        }

        // 分解任务
        // 创建一个子任务来为数组的前一半求和
        ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2);
        // 利用另一个 ForkJoinPool 线程异步执行新创建的子任务
        leftTask.fork();

        ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end);
        // 同步执行第二个子任务，有可能允许进一步递归。这里同步执行，减少一次线程切换
        long rightSum = rightTask.compute();
        // 读取第一个子任务的结果，如果未完成就等待
        long leftSum = leftTask.join();
        return leftSum + rightSum;
    }

    private long computeSequentially() {
        long sum = 0;
        for (int i = start; i < end; i++) {
            sum += numbers[i];
        }
        return sum;
    }

    public static void main(String[] args) {
        long[] numbers = LongStream.rangeClosed(0, 100000).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        ForkJoinPool joinPool = new ForkJoinPool();
        long sum = joinPool.invoke(task);
        System.out.println(sum);
    }

}
