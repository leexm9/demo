package com.leexm.demo.proxy;

import com.leexm.demo.proxy.intf.Move;
import com.leexm.demo.proxy.intf.Say;

/**
 * @author leexm
 * @date 2021-01-02 10:32
 */
public class Dog implements Move, Say {

    @Override
    public String move(int speed) {
        return "the dog speed is " + speed;
    }

    @Override
    public void say() {
        System.out.println("Wa wa");
    }

}
