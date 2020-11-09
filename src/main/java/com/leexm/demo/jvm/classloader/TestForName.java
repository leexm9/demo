package com.leexm.demo.jvm.classloader;

/**
 * 测试 Class.forName 与 loadClass 的区别：
 * - Class.forName() 加载类时要对类进行初始化——执行静态代码块、静态变量赋值等
 * - ClassLoader.loadClass() 加载类时不会进行初始化，仅仅是把类加载到虚拟机中
 *
 * @author leexm
 * @date 2020-04-13 17:59
 */
public class TestForName {

    public static void main(String[] args) {
        try {
            ClassLoader.getSystemClassLoader().loadClass("com.leexm.demo.object.ObjInit");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
            Class.forName("com.leexm.demo.object.ObjInit");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
