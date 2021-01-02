package com.leexm.demo.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author leexm
 * @date 2021-01-02 10:53
 */
public class MoveInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("args: " + objects[0]);
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("move over!");
        return result;
    }

}
