package com.leexm.demo.jvm;

/**
 * Class.forName()加载类时要对类进行初始化——执行静态代码块、静态变量赋值等
 * ClassLoader.loadClass()加载类时不会进行初始化，仅仅是把类加载到虚拟机中
 *
 * @author leexm
 * @date 2020-04-13 17:57
 */
public class ForName {

    // 静态代码块
    static {
        System.out.println("执行了静态代码块");
    }
    // 静态变量
    private static String staticFiled = staticMethod();

    // 赋值静态变量的静态方法
    public static String staticMethod() {
        System.out.println("执行了静态方法");
        return "hello,world";
    }

}
