package com.leexm.demo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author leexm
 * @date 2021-01-02 10:39
 */
public class SayHandler implements InvocationHandler {

    private Object subject;

    public SayHandler(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Before invoke " + method.getName());
        Object result = method.invoke(subject, args);
        System.out.println("Before after " + method.getName());
        return result;
    }

}
