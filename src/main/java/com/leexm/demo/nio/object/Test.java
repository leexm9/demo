package com.leexm.demo.nio.object;

import java.nio.ByteBuffer;

/**
 * @author xm
 * @date 2019-06-29 23:10
 */
public class Test {

    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        System.out.println(byteBuffer.order());
        System.out.println(":" + System.getProperty("line.separator") + ":");
    }

}
