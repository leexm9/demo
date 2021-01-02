package com.leexm.demo.proxy.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author leexm
 * @date 2021-01-02 10:39
 */
public class MoveHandler implements InvocationHandler {

    private Object subject;

    public MoveHandler(Object subject) {
        this.subject = subject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("args: " + args[0]);
        Object result = method.invoke(subject, args);
        System.out.println("move over!");
        return result;
    }

}