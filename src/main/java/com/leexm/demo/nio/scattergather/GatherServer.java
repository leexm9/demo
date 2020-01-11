package com.leexm.demo.nio.scattergather;

import com.leexm.demo.nio.object.Persion;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 使用 gather 按顺序发送结构化数据
 *
 * @author xm
 * @date 2019-07-06 14:44
 */
public class GatherServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8088));
        serverSocketChannel.configureBlocking(false);

        ByteBuffer ageBuffer = ByteBuffer.allocate(4);
        ByteBuffer nameBuffer = ByteBuffer.allocate(40);
        ByteBuffer[] bufferArray = {ageBuffer, nameBuffer};

        IntBuffer iAgeBuffer = ageBuffer.asIntBuffer();

        System.out.println("服务器就绪，等待连接.....");

        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                continue;
            }
            System.out.println("客户端连接请求....");
            ageBuffer.clear();
            nameBuffer.clear();
            Persion persion = new Persion("Tom", 20);
            iAgeBuffer.put(persion.getAge());
            nameBuffer.put(persion.getName().getBytes("UTF-8"));
            iAgeBuffer.flip();
            nameBuffer.flip();
            socketChannel.write(bufferArray);
            socketChannel.close();
        }
    }

}
