package com.leexm.demo.java8.stream;

import com.leexm.demo.java8.stream.object.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流的基本操作
 *
 * @author leexm
 * @date 2020-01-03 10:47
 */
public class StreamBasic {

    public static void main(String[] args) {
        List<String> threeHightCaloricDishNames = Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .limit(3)
                .collect(Collectors.toList());
        System.out.println(threeHightCaloricDishNames);

        List<String> skip = Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .map(Dish::getName)
                .skip(3)
                .collect(Collectors.toList());
        System.out.println(skip);

        // 取卡路里小于 400
        List<String> dishes = Dish.menu.stream()
                .filter(d -> d.getCalories() < 400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(Collectors.toList());
        System.out.println(dishes);
    }

}
