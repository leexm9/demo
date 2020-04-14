package com.leexm.demo.nio.buffer;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射文件
 *
 * @author leexm
 * @date 2020-03-20 13:34
 */
public class MmapBuffer {

    private static final int COUNT = 10 * 1024 * 1024;

    public static void main(String[] args) throws IOException {
        RandomAccessFile memoryMappedFile = new RandomAccessFile("/Users/leexm/Desktop/mmap.txt", "rw");
        MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, COUNT);

        for (int i = 0, index = 0; i < COUNT; i++, index++) {
            if (index >= 26) {
                index = 0;
            }
            out.put((byte) ('A' + index));
        }
        System.out.println("Writing to Memory Mapped File is completed");

        for (int i = 0; i < 10; i++) {
            System.out.println((char) out.get(i));
        }
        System.out.println("Reading from Memory Mapped File is completed");
        memoryMappedFile.close();
    }

}
