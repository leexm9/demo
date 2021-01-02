package com.leexm.demo.proxy;

import com.leexm.demo.proxy.intf.Say;

/**
 * @author leexm
 * @date 2021-01-02 10:34
 */
public class Cat implements Say {

    @Override
    public void say() {
        System.out.println("miao miao");
    }

}
