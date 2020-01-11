package com.leexm.demo.java8.stream;

import com.leexm.demo.java8.stream.object.Dish;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 收集
 *
 * @author leexm
 * @date 2020-01-04 11:35
 */
public class Collecting {

    public static void main(String[] args) {
        // 计算数量
        long howManyDishes = Dish.menu.stream().collect(Collectors.counting());
        System.out.println(howManyDishes);
        howManyDishes = Dish.menu.stream().count();
        System.out.println(howManyDishes);

        // 最大、最小、求和
        Dish.menu.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)))
            .ifPresent(System.out::println);
        Dish.menu.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
        Dish.menu.stream().max(Comparator.comparingInt(Dish::getCalories)).ifPresent(System.out::println);
        Dish.menu.stream().min(Comparator.comparingInt(Dish::getCalories)).ifPresent(System.out::println);

        // 求和、平均数
        int totalCalories = Dish.menu.stream().collect(Collectors.summingInt(Dish::getCalories));
        System.out.println(totalCalories);
        double avgCalories = Dish.menu.stream().collect(Collectors.averagingInt(Dish::getCalories));
        System.out.println(avgCalories);

        // 统计汇总信息
        IntSummaryStatistics statistics = Dish.menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        System.out.println(statistics.getCount());
        System.out.println(statistics);

        // 一般方式实现
        totalCalories = Dish.menu.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        System.out.println(totalCalories);

        Optional<Dish> maxCalories = Dish.menu.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2));
        maxCalories.ifPresent(System.out::println);
    }

}
