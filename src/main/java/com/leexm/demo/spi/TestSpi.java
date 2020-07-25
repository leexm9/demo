package com.leexm.demo.spi;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author leexm
 * @date 2020-04-16 23:35
 */
public class TestSpi {

    public static void main(String[] args) {
        ServiceLoader<Animal> animals = ServiceLoader.load(Animal.class);
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            animal.voice();
        }
    }

}
