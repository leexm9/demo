package com.leexm.demo.proxy;

import com.leexm.demo.proxy.intf.Move;

/**
 * @author leexm
 * @date 2021-01-02 10:35
 */
public class Car implements Move {

    @Override
    public String move(int speed) {
        return "the car speed is " + speed;
    }

}
