package com.leexm.demo.jvm.agent;

/**
 * 使用 agent 获取对象的占用的字节数
 * -javaagent:${path}/${project-name}-jar-with-dependencies.jar
 *
 * @author leexm
 * @date 2020-11-03 00:59
 */
public class TestAgent {

    public static void main(String[] args) {
        System.out.println(ObjectSizeAgent.sizeOf(new Object()));
        System.out.println(ObjectSizeAgent.sizeOf(new int[]{}));
        System.out.println(ObjectSizeAgent.sizeOf(new P()));
    }

    private static class P {
                        // 8 _markword
                        // 4 _oop 指针
        int id;         // 4
        String name;    // 4
        int age;        // 4

        byte b1;        // 1
        byte b2;        // 1

        Object obj;     // 4
        byte b3;        // 1
    }

}
