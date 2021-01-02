package com.leexm.demo.proxy.cglib;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author leexm
 * @date 2021-01-02 10:42
 */
public class SayInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("Before invoke " + method.getName());
        Object result = methodProxy.invokeSuper(o, objects);
        System.out.println("Before after " + method.getName());
        return result;
    }

}
