package com.leexm.demo.java8.stream;

import com.leexm.demo.java8.stream.object.Dish;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 分组
 *
 * @author leexm
 * @date 2020-01-04 13:40
 */
public class Grouping {

    public static void main(String[] args) {

        /**
         * 分组函数的几种用法
         */
        // 按种类分组
        Map<Dish.Type, List<Dish>> dishByType = Dish.menu.stream().collect(Collectors.groupingBy(Dish::getType));
        System.out.println(dishByType);

        // 按热量分组
        Map<CaloricLevel, List<Dish>> dishByCaloric = Dish.menu.stream()
                .collect(Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                }));
        System.out.println(dishByCaloric);

        // 多级分组，按热量、类型分组
        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> dishByTypeCaloric = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                })));
        System.out.println(dishByTypeCaloric);

        // 按子组收集数据
        Map<Dish.Type, Long> typeCount = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));
        System.out.println(typeCount);

        // 每类中热量最高的菜
        Map<Dish.Type, Optional<Dish>> mostCaloricByType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparing(Dish::getCalories))));
        System.out.println(mostCaloricByType);

        // 类型转换
        Map<Dish.Type, Dish> mostCaloricByType2 = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.collectingAndThen(Collectors.maxBy(Comparator.comparing(Dish::getCalories)), Optional::get)));
        System.out.println(mostCaloricByType2);

        Map<Dish.Type, Set<CaloricLevel>> caloricByType = Dish.menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(dish -> {
                    if (dish.getCalories() <= 400) {
                        return CaloricLevel.DIET;
                    } else if (dish.getCalories() <= 700) {
                        return CaloricLevel.NORMAL;
                    } else {
                        return CaloricLevel.FAT;
                    }
                }, Collectors.toSet())));
        System.out.println(caloricByType);
        //  以上是分组函数用法

        /**
         * 分区函数的用法，分区是分组的一种特殊情况
         * partitioningBy 需要一个谓词
         */
        // 按荤素分类
        Map<Boolean, List<Dish>> dishes = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian));
        System.out.println(dishes);

        Map<Boolean, Map<Dish.Type, List<Dish>>> dishes2 = Dish.menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
        System.out.println(dishes2);

    }

    private enum CaloricLevel {
        DIET, NORMAL, FAT
    }

}
