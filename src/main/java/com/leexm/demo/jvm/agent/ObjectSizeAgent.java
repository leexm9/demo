package com.leexm.demo.jvm.agent;

import java.lang.instrument.Instrumentation;

/**
 * 通过 agent 机制，获取一个 java 对象的大小
 *
 * @author leexm
 * @date 2020-11-03 00:45
 */
public class ObjectSizeAgent {

    private static Instrumentation inst;

    public static void premain(String agentArgs, Instrumentation _inst) {
        System.out.println("pre-main start!");
        inst = _inst;
    }

    public static long sizeOf(Object obj) {
        return inst.getObjectSize(obj);
    }

}
