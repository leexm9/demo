package com.leexm.demo.jvm;

/**
 * @author leexm
 * @date 2020-04-13 17:59
 */
public class TestForName {

    public static void main(String[] args) {
        try {
            Class.forName("com.leexm.demo.jvm.ForName");
            System.out.println("======================");
            ClassLoader.getSystemClassLoader().loadClass("com.leexm.demo.jvm.ForName");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~");
            Class clazz = ForName.class;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
