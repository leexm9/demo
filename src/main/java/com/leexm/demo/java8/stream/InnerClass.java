package com.leexm.demo.java8.stream;

/**
 * 匿名内部类
 *
 * @author leexm
 * @date 2020-01-02 15:17
 */
public class InnerClass {

    public final int value = 4;

    public static void main(String[] args) {
        InnerClass innerClass = new InnerClass();
        innerClass.doIt();
    }

    public void doIt() {
        int value = 6;
        Runnable r = new Runnable() {
            public final int value = 5;
            @Override
            public void run() {
                int value = 10;
                // 此处的 this，指的是包含 run 方法的类，而不是 InnerClass
                System.out.println(this.value);
            }
        };
        r.run();
    }

}
