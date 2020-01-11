package com.leexm.demo.nio.channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Demonstrate gathering write using many buffers.
 *
 * @author xm
 * @date 2019-06-30 15:30
 */
public class Marketing {

    private static final String DEMOGRAPHIC = "blahblah.txt";

    private static String newline = System.getProperty("line.separator");

    private static Random rand = new Random();

    private static String[] col1 = {
            "Aggregate", "Enable", "Leverage",
            "Facilitate", "Synergize", "Repurpose",
            "Strategize", "Reinvent", "Harness"
    };

    private static String[] col2 = {
            "cross-platform", "best-of-breed", "frictionless",
            "ubiquitous", "extensible", "compelling",
            "mission-critical", "collaborative", "integrated"
    };

    private static String[] col3 = {
            "methodologies", "infomediaries", "platforms",
            "schemas", "mindshare", "paradigms",
            "functionalities", "web services", "infrastructures"
    };

    public static void main(String[] args) throws Exception {
        int reps = 10;
        if (args.length > 0) {
            reps = Integer.parseInt(args[0]);
        }
        FileOutputStream fos = new FileOutputStream(DEMOGRAPHIC);
        GatheringByteChannel gatherChannel = fos.getChannel();
        // Generate some brilliant marcom, er, repurposed content
        ByteBuffer[] bs = utterBS(reps);
        // Deliver the message to the waiting market
        while (gatherChannel.write(bs) > 0) {
            // Empty body
            // Loop until write() returns zero
        }
        System.out.println("Mindshare paradigms synergized to " + DEMOGRAPHIC);
        fos.close();
    }

    // The Marcom-atic 9000
    private static ByteBuffer[] utterBS(int howMany) throws Exception {
        List<ByteBuffer> list = new LinkedList<>();
        for (int i = 0; i < howMany; i++) {
            list.add(pickRandom(col1, " "));
            list.add(pickRandom(col2, " "));
            list.add(pickRandom(col3, newline));
        }
        ByteBuffer[] bufs = new ByteBuffer[list.size()];
        list.toArray(bufs);
        return bufs;
    }

    // Pick one, make a buffer to hold it and the suffix, load it with
    // the byte equivalent of the strings (will not work properly for
    // non-Latin characters), then flip the loaded buffer so it's ready
    // to be drained
    private static ByteBuffer pickRandom(String[] strings, String suffix) throws Exception {
        String string = strings[rand.nextInt(strings.length)];
        int total = string.length() + suffix.length();
        ByteBuffer buf = ByteBuffer.allocate(total);
        buf.put(string.getBytes(StandardCharsets.UTF_8));
        buf.put(suffix.getBytes(StandardCharsets.UTF_8));
        buf.flip();
        return buf;
    }

}
