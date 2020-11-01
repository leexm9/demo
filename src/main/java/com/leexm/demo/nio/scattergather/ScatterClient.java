package com.leexm.demo.nio.scattergather;

import com.leexm.demo.object.Person;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

/**
 * 基于 scatter 功能，从数据中解析结构化数据
 *
 * @author xm
 * @date 2019-07-06 14:44
 */
public class ScatterClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress(8088));

        ByteBuffer ageBuffer = ByteBuffer.allocate(4);
        ByteBuffer nameBuffer = ByteBuffer.allocate(40);
        ByteBuffer[] bufferArray = {ageBuffer, nameBuffer};
        IntBuffer iAgeBuffer = ageBuffer.asIntBuffer();

        socketChannel.read(bufferArray);
        ageBuffer.flip();
        nameBuffer.flip();
        Person persion = new Person();
        persion.setAge(iAgeBuffer.get());
        persion.setName(new String(nameBuffer.array(), 0, nameBuffer.limit(), "UTF-8"));
        System.out.println(persion);
        socketChannel.close();
    }

}
