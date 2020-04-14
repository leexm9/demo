package com.leexm.demo.disruptor;

import java.time.Duration;
import java.time.Instant;

/**
 * 测试缓存行
 *
 * 1、Cache(缓存行)是由很多个 Cache Line(缓存行) 组成的。每个 Cache Line 通常是 64字节，并且它有效地引用主内存中的一块地址。
 * 一个 Java 的 long 类型变量是 8 字节，因此在一个缓存行中可以可以存 8 个 long 类型的变量
 *
 * 2、CPU 每次从主内存中拉去数据时，会把相邻的数据也存入同一个Cache Line中
 *
 * 3、在访问一个 long 数组的时候，如果数组中的一个值被加载到缓存中，它会自动加载另外 7 个。因此能非常快的遍历整个数组。
 * 事实上，你可以非常快速的遍历连续内存块中分配的任意数据结构
 *
 * @author leexm
 * @date 2020-03-18 14:47
 */
public class CacheLine {

    public static void main(String[] args) {
        // 考虑一般缓存行大小是 64 字节，一个 long 类型占 8 字节
        long[][] arr = new long[1024*1024][];
        for (int i = 0; i < 1024 * 1024; i++) {
            arr[i] = new long[8];
            for (int j = 0; j < 8; j++) {
                arr[i][j] = 0L;
            }
        }

        long sum = 0L;
        Instant start = Instant.now();
        // 这种遍历方式，每次加载一整行的数据并遍历整个 Cache Line
        for (int i = 0; i < 1024 * 1024; i++) {
            for (int j = 0; j < 8; j++) {
                sum = arr[i][j];
            }
        }
        Instant end = Instant.now();
        System.out.println("Loop times:：" + Duration.between(start, end).toMillis() + "ms");

        start = Instant.now();
        // 这种遍历方式，虽然也每次加载一整行，但只取用第一个元素
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 1024 * 1024; j++) {
                sum = arr[j][i];
            }
        }
        end = Instant.now();
        System.out.println("Loop times:：" + Duration.between(start, end).toMillis() + "ms");
    }

}
