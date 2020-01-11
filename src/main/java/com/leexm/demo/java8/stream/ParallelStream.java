package com.leexm.demo.java8.stream;

import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author leexm
 * @date 2020-01-04 22:26
 */
public class ParallelStream {

    public static void main(String[] args) {
        int num = 10000;

        /**
         * 并行流不一定会比同步流块
         * 第一个例子中涉及拆、装箱操作
         * iterate 很难分成多个小块操作，因为后一个元素的生成依赖前一个
         */
        long t1 = System.nanoTime();
        Stream.iterate(1L, i -> i + 1)
                .limit(num)
                .parallel()
                .reduce(0L, Long::sum);
        long t2 = System.nanoTime();
        System.out.println(t2 - t1);

        LongStream.iterate(1L, i -> i + 1)
                .limit(num)
                .parallel()
                .sum();
        long t3 = System.nanoTime();
        System.out.println(t3 - t2);

        LongStream.iterate(1L, i -> i + 1)
                .limit(num)
                .sum();
        long t4 = System.nanoTime();
        System.out.println(t4 - t3);

        /**
         * rangeClosed 直接生成原始类型，没有拆、装箱
         * rangeClosed 数字范围给定，可以拆分计算
         */
        LongStream.rangeClosed(1, num).sum();
        long t5 = System.nanoTime();
        System.out.println(t5 - t4);

        LongStream.rangeClosed(1, num).parallel().sum();
        long t6 = System.nanoTime();
        System.out.println(t6 - t5);
    }

}
