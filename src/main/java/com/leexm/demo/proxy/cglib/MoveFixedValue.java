package com.leexm.demo.proxy.cglib;

import net.sf.cglib.proxy.FixedValue;

/**
 * @author leexm
 * @date 2021-01-02 10:54
 */
public class MoveFixedValue implements FixedValue {

    @Override
    public Object loadObject() throws Exception {
        Object object = 99;
        return object;
    }

}
