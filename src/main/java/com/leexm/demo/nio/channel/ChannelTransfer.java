package com.leexm.demo.nio.channel;

import java.io.FileInputStream;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author xm
 * @date 2019-07-01 00:45
 */
public class ChannelTransfer {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.err.println("Usage: filename ...");
            return;
        }
        String fileName = args[0];
        FileInputStream fis = new FileInputStream(fileName);
        FileChannel channel = fis.getChannel();
        WritableByteChannel target = Channels.newChannel(System.out);
        channel.transferTo(0, channel.size(), target);
        channel.close();
        fis.close();
    }

}