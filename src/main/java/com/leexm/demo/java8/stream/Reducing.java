package com.leexm.demo.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author leexm
 * @date 2020-01-03 18:38
 */
public class Reducing {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(3,4,5,1,2);

        int sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        sum = numbers.stream().reduce(0, Integer::sum);
        System.out.println(sum);

        // 减少 sum 计算的装箱
        sum = numbers.stream().mapToInt(Integer::intValue).sum();
        System.out.println(sum);

        // 无初始值
        Optional<Integer> sum1 = numbers.stream().reduce(Integer::sum);
        sum1.ifPresent(System.out::println);

        // 最大值
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        max.ifPresent(System.out::println);

        // 最小值
        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);
    }

}
