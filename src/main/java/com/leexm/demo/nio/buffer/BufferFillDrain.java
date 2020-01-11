package com.leexm.demo.nio.buffer;

import java.nio.CharBuffer;

/**
 * 打印输出 String 数组内容
 *
 * @author xm
 * @date 2019-06-29 18:47
 */
public class BufferFillDrain {

    private static String[] strings = {
            "A random string value",
            "The product of an infinite number of monkeys",
            "Hey hey we're the Monkees",
            "Opening act for the Monkees: Jimi Hendrix",
            "'Scuse me while I kiss this fly",
            "Help Me! Help Me!",
    };

    public static void main(String[] args) {
        int index = 0;
        CharBuffer charBuffer = CharBuffer.allocate(100);
        while (index < strings.length) {
            charBuffer.put(strings[index], 0, strings[index].length());
            charBuffer.flip();
            while (charBuffer.hasRemaining()) {
                System.out.print(charBuffer.get());
            }
            System.out.println("");
            index++;
            charBuffer.clear();
        }
    }

}
