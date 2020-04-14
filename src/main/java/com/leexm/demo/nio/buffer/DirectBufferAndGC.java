package com.leexm.demo.nio.buffer;

import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.time.Duration;
import java.time.Instant;

/**
 * 直接内存与垃圾回收
 * -XX:+PrintGCDetails -XX:MaxDirectMemorySize=1024M
 *
 * 解决方式：
 * 1、手动释放，((DirectBuffer) byteBuffer).cleaner().clean()
 * 2、参数 -XX:+DisableExplicitGC 禁用 System.gc()，有可能会导致直接内存得不到回收，而发生OOM（此时只有发生堆内存回收的时候，才会顺带的回收直接内存）
 *
 * @author leexm
 * @date 2020-03-20 14:24
 */
public class DirectBufferAndGC {

    private static final int SIZE = 250 * 1024 * 1024;

    public static void main(String[] args) {
        Instant start = Instant.now();
        DirectBuffer directBuffer = null;
        for (int i = 0; i < 10; i++) {
            directBuffer = (DirectBuffer) ByteBuffer.allocateDirect(SIZE);

            /**
             * 这个方法注释之后，循环 4 次就会触发一次 FullGC
             * 原因：ByteBuffer.allocateDirect() -> DirectByteBuffer构造函数 -> Bits.reserveMemory() -> System.gc()
             * 分配时如果堆外内存剩余不多就会触发 FullGC
             *
             * Cleaner.clean()方法，手动释放对外内存。jvm即使接到System.gc()命令，但是认为没必要进行gc，就不会触发
             */
            directBuffer.cleaner().clean();
        }
        Instant end = Instant.now();
        System.out.println(String.format("耗时：%s ms", Duration.between(start, end).toMillis()));
    }

}
