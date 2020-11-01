package com.leexm.demo.jvm.classloader;

/**
 * @author leexm
 * @date 2020-11-01 17:20
 */
public class TestClassLoader {

    public static void main(String[] args) throws ClassNotFoundException {
//        ClassLoader classLoader = new LXMClassLoader();
        ClassLoader classLoader = new LXMClassLoader2();
        Class clazz = classLoader.loadClass("com.leexm.demo.object.Person");
        System.out.println(clazz.getClassLoader());
        System.out.println(clazz.hashCode());

//        classLoader = new LXMClassLoader();
        classLoader = new LXMClassLoader2();
        Class clazz2 = classLoader.loadClass("com.leexm.demo.object.Person");
        System.out.println(clazz2.getClassLoader());
        System.out.println(clazz2.hashCode());

        System.out.println(clazz == clazz2);

        Class clazz3 = classLoader.loadClass("com.leexm.demo.object.Person");
        System.out.println(clazz2.hashCode());
        System.out.println(clazz3.hashCode());
    }

}
