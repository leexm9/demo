package com.leexm.demo.nio.buffer;

import java.nio.CharBuffer;

/**
 * @author xm
 * @date 2019-06-29 18:33
 */
public class CharBufferTest {

    public static void main(String[] args) {
        CharBuffer buffer = CharBuffer.allocate(10);
        System.out.println("after init");
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("capacity:" + buffer.capacity());
        System.out.println("====================");
        buffer.put('H').put('e').put('l').put('l').put('o');
        System.out.println("after put");
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("====================");
//        int rs = buffer.position();
//        buffer.flip();
//        buffer.position(rs);
        buffer.reset();
        System.out.println("after mark-flip-reset");
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("====================");
        buffer.flip();
        System.out.println("after flip");
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("====================");
        if (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println("");
        System.out.println("after read");
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("====================");
        buffer.rewind();
        for (int i = 0; i < buffer.length(); i++) {
            System.out.print(buffer.get());
            if (i == 2) {
                buffer.mark();
            }
        }
        System.out.println("");
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println("");
        System.out.println("====================");
        buffer.rewind();
        System.out.println("after rewind");
        System.out.println("position:" + buffer.position());
        System.out.println("limit:" + buffer.limit());
        System.out.println("====================");
        for (int i = 0; i < 2; i++) {
            buffer.get();
        }
        System.out.println("position:" + buffer.position());
        buffer.compact();
        System.out.println("after compact");
        System.out.println("position:" + buffer.position());
        buffer.flip();
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println("");
        System.out.println("====================");
        System.out.println(buffer.hasArray());
        System.out.println(buffer.array());
        char[] tmp = buffer.array();
        tmp[0] = 'A';
        buffer.rewind();
        System.out.println("offset:" + buffer.arrayOffset());
        while (buffer.hasRemaining()) {
            System.out.print(buffer.get());
        }
        System.out.println("");
        System.out.println("offset:" + buffer.arrayOffset());
        System.out.println("direct:" + buffer.isDirect());
        System.out.println("====================");
    }

}
