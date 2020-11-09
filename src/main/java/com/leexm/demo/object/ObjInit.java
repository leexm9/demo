package com.leexm.demo.object;

/**
 * @author leexm
 * @date 2020-11-09 22:35
 */
public class ObjInit {

    /**
     * 静态代码块
     */
    static {
        System.out.println("执行了静态代码块");
    }

    /**
     * 静态变量
     */
    private static String staticFiled = staticMethod();

    /**
     * 赋值静态变量的静态方法
     */
    private static String staticMethod() {
        System.out.println("执行了静态方法");
        return "hello,world";
    }

    public static String getStaticFiled() {
        return staticFiled;
    }

}
