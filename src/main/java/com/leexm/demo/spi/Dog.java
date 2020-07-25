package com.leexm.demo.spi;

/**
 * @author leexm
 * @date 2019-09-15 23:43
 */
public class Dog implements Animal {
    @Override
    public void voice() {
        System.out.println("Wang wang!");
    }
}
