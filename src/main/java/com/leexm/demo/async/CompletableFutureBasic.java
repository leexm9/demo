package com.leexm.demo.async;

import java.util.concurrent.*;

/**
 * 使用 CompletableFuture 异步实现与结果转换
 *
 * @author leexm
 * @date 2020-01-12 10:58
 */
public class CompletableFutureBasic {

    private static final ExecutorService EXECUTORS = Executors.newFixedThreadPool(4);

    public static void main(String[] args) {
//        waiting();
//        timedWaiting();
//        runAsync();
//        EXECUTORS.shutdown();
//        supplyAsync();
//        thenRun();
//        thenAccept();
//        thenApply();
//        whenComplete();
        throwing(true);
    }

    private static void waiting() {
        CompletableFuture<String> future = new CompletableFuture<>();
        EXECUTORS.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("--- %s set future result ---", Thread.currentThread().getName()));
            future.complete("hello world!");
        });

        // 同步等待计算结果
        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

    private static void timedWaiting() {
        CompletableFuture<String> future = new CompletableFuture<>();
        EXECUTORS.execute(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("--- %s set future result ---", Thread.currentThread().getName()));
            future.complete("hello world!");
        });

        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(future.get(4000, TimeUnit.MILLISECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

    /**
     * 无返回值的异步计算
     */
    private static void runAsync() {
        CompletableFuture future = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("over");
        }, EXECUTORS);

        // 同步等待计算结果
        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

    /**
     * 有返回值的异步执行
     */
    private static void supplyAsync() {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello world!";
        });

        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

    /**
     * 基于 thenRun 实异现异步任务 A，执行完毕后，激活异步任务 B 执行
     * 注意：这种方式激活的异步任务 B 是拿不到任务 A 的执行结果的
     */
    private static void thenRun() {
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        CompletableFuture futureB = futureA.thenRun(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("--- after futureA over doSomething ---");
        });

        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(futureA.get());
            System.out.println(futureB.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

    /**
     * 与 thenRun 的区别在于任务 B 可以拿到任务 A 的结果
     */
    private static void thenAccept() {
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        CompletableFuture futureB = futureA.thenAccept(s -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(String.format("--- after futureA over doSomething:%s ---", s));
        });

        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(futureA.get());
            System.out.println(futureB.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

    /**
     * 与 thenAccept 的区别在于 futureB 的结果可以拿到
     */
    private static void thenApply() {
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello";
        });

        CompletableFuture<String> futureB = futureA.thenApply(s -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return String.format("%s world!", s);
        });

        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(futureA.get());
            System.out.println(futureB.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

    /**
     * 设置回调函数，当异步任务执行完毕后进行回调，不会阻塞调用线程
     */
    private static void whenComplete() {
        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Hello world!";
        });

        futureA.whenComplete((s, t) -> {
            if (t == null) {
                System.out.println(s);
            } else {
                t.printStackTrace();
            }
        });

        try {
            Thread.currentThread().join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 异常的处理
     */
    private static void throwing(final boolean flag) {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (flag) {
                throw new RuntimeException("test exception.");
            }
            return "Hello world!";
        }).exceptionally(e -> e.getMessage());

        System.out.println("--- main thread wait future result ---");
        try {
            System.out.println(future.get());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("--- main thread got future result ---");
    }

}
