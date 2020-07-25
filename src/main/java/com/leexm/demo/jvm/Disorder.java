package com.leexm.demo.jvm;

/**
 * 验证 CPU 的指令重排序
 *
 * @author leexm
 * @date 2020-05-05 11:21
 */
public class Disorder {

    private static int a = 0, b = 0;
    private static int x = 0, y = 0;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (;;) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            Thread one = new Thread(() -> {
                a = 1;
                x = b;
            });
            Thread other = new Thread(() -> {
                b = 1;
                y = a;
            });
            one.start();
            other.start();
            one.join();
            other.join();
            if (x == 0 && y == 0) {
                System.out.println(String.format("%d (%d, %d)", i, x, y));
                break;
            }
        }
    }

}
