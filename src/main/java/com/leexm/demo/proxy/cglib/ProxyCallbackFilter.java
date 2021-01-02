package com.leexm.demo.proxy.cglib;

import net.sf.cglib.proxy.CallbackFilter;

import java.lang.reflect.Method;

/**
 * @author leexm
 * @date 2021-01-02 10:53
 */
public class ProxyCallbackFilter implements CallbackFilter {

    @Override
    public int accept(Method method) {
        if (method.getName().equals("move")) {
            return 0;
        } else if (method.getName().equals("say")) {
            return 1;
        }
        return 0;
    }

}
