package com.leexm.demo.sync;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

/**
 * 输出对象的内存布局
 *
 * @author leexm
 * @date 2020-05-04 15:00
 */
public class ObjectLayout {

    public static void main(String[] args) throws InterruptedException {
        /**
         * 偏向锁默认打开，但需要 4 秒的时间延迟
         */
        TimeUnit.SECONDS.sleep(5);

        Object obj = new Object();
        System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        System.out.println();

        synchronized (obj) {
            System.out.println(ClassLayout.parseInstance(obj).toPrintable());
        }
    }

}
