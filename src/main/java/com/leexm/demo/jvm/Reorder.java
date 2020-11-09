package com.leexm.demo.jvm;

import java.util.concurrent.CompletableFuture;

/**
 * 验证 CPU 的指令重排序
 *
 * @author leexm
 * @date 2020-05-05 11:21
 */
public class Reorder {

    private static int a = 0, b = 0;
    private static int x = 0, y = 0;

    public static void main(String[] args) {
        int i = 0;
        for (;;) {
            i++;
            x = 0;
            y = 0;
            a = 0;
            b = 0;
            // (x, y) 可能的组合是 (1, 1)、(0, 1)、(1, 0)
            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                a = 1;
                x = b;
            });
            CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                b = 1;
                y = a;
            });
            CompletableFuture<Void> future = CompletableFuture.allOf(future1, future2);
            future.join();
            // 一旦出现 (0, 0) 证明出现了指令重排
            if (x == 0 && y == 0) {
                System.out.println(String.format("%d (%d, %d)", i, x, y));
                break;
            }
        }
    }

}
