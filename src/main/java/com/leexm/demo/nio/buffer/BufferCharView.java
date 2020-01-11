package com.leexm.demo.nio.buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * @author xm
 * @date 2019-06-29 22:08
 */
public class BufferCharView {

    public static void main(String[] args) {
//        System.out.println(ByteOrder.nativeOrder());
//        System.out.println(CharBuffer.allocate(10).order());
        ByteBuffer byteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
        CharBuffer charBuffer = byteBuffer.asCharBuffer();

        // Load the ByteBuffer with some bytes
        byteBuffer.put(0, (byte) 0);
        byteBuffer.put(1, (byte) 'H');
        byteBuffer.put(2, (byte) 0);
        byteBuffer.put(3, (byte) 'i');
        byteBuffer.put(4, (byte) 0);
        byteBuffer.put(5, (byte) '!');
        byteBuffer.put(6, (byte) 0);
        println(byteBuffer);
        println(charBuffer);

        System.out.println(byteBuffer.remaining());
        while (byteBuffer.hasRemaining()) {
            System.out.print(byteBuffer.get());
        }
        System.out.println("\n====================");
        System.out.println(charBuffer.remaining());
        while (charBuffer.hasRemaining()) {
            System.out.print(charBuffer.get());
        }
        System.out.println("\n====================");
    }

    private static void println(Buffer buffer) {
        System.out.println("pos=" + buffer.position()
                + ", limit=" + buffer.limit()
                + ", capacity=" + buffer.capacity()
                + ": '" + buffer.toString() + "'");
    }

}
