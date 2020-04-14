package com.leexm.demo.jvm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * -Xmx32m -Xms16m
 * 发生 OOM 的线程一般情况下会死亡，也会被终结，该线程持有的对象占有的 heap 都会被 gc 从而释放内存
 * 此时其他线程依然正常，只是会受到 gc 的影响
 *
 * @author leexm
 * @date 2020-04-13 17:06
 */
public class JVMThread {

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            List<byte[]> list = new ArrayList<>();
            while (true) {
                System.out.println(LocalDateTime.now() + " OOM");
                byte[] bytes = new byte[1024 * 1024];
                list.add(bytes);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            while (true) {
                System.out.println(LocalDateTime.now() + " " + Thread.currentThread().getName());
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
