package com.leexm.demo.async;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 多个 CompletableFuture 组合计算
 *
 * @author leexm
 * @date 2020-01-12 15:19
 */
public class MultiCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        });

        long t1 = System.currentTimeMillis();
//        CompletableFuture<String> result = thenCompose(futureA, () -> "worid");
//        CompletableFuture<String> result = thenCombine(futureA, futureB, (a, b) -> String.format("%s %s!", a, b));
//        CompletableFuture<Void> result = allOf(0, 10);
        CompletableFuture<Object> result = anyOf(10);

        System.out.println(result.get());
        System.out.println(String.format("耗时: %d 毫秒", (System.currentTimeMillis() - t1)));
    }

    /**
     * 实现先执行一个异步任务，并使用结果作为参数来执行另一个异步任务
     *
     *
     * @param futureA
     * @param <T>
     * @return
     */
    private static <T, U> CompletableFuture<String> thenCompose(CompletableFuture<T> futureA, Supplier<U> supplier) {
         return futureA.thenCompose(t -> CompletableFuture.supplyAsync(() -> {
             try {
                 TimeUnit.SECONDS.sleep(3);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             return String.format("%s %s!", t.toString(), supplier.get().toString());
         }));
    }

    /**
     * 并发执行两个异步任务，并将两者的结果作为参数再来执行一个异步任务
     *
     * @param futureA
     * @param futureB
     * @param fn
     * @param <T>
     * @param <U>
     * @param <R>
     * @return
     */
    private static <T, U, R> CompletableFuture<R> thenCombine(CompletableFuture<T> futureA, CompletableFuture<U> futureB, BiFunction<T, U, R> fn) {
        return futureA.thenCombine(futureB, fn);
    }

    /**
     * 等待多个并发异步任务完成
     *
     * @param start
     * @param end
     * @return
     */
    private static CompletableFuture<Void> allOf(int start, int end) {
        List<CompletableFuture<Integer>> list = IntStream.range(start, end).boxed().map(i -> CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("iterator:%s", i));
            return i * 2;
        })).collect(Collectors.toList());
        return CompletableFuture.allOf(list.toArray(new CompletableFuture[list.size()]));
    }

    /**
     * 多个并发任务，只要其中一个完成就返回
     *
     * @param num
     * @return
     */
    private static CompletableFuture<Object> anyOf(int num) {
        List<CompletableFuture<Integer>> list = IntStream.range(0, num).boxed().parallel()
                .map(i -> CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(String.format("iterator:%s", i));
                    return i * 2;
                })).collect(Collectors.toList());
        return CompletableFuture.anyOf(list.toArray(new CompletableFuture[list.size()]));
    }

}
