package com.leexm.demo.java8.stream;

import com.leexm.demo.java8.stream.object.Dish;

/**
 * @author leexm
 * @date 2020-01-03 18:00
 */
public class Filtering {

    public static void main(String[] args) {
        boolean flag = Dish.menu.stream().filter(Dish::isVegetarian)
                .findAny()
                .isPresent();
        System.out.println(flag);

        Dish.menu.stream().filter(Dish::isVegetarian).findAny()
                .ifPresent(System.out::println);
    }

}
