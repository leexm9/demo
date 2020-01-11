package com.leexm.demo.java8.stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 质数筛选
 *
 * @author leexm
 * @date 2020-01-04 15:11
 */
public class FilterPrime {

    public static void main(String[] args) {
        int num = 100;

        List<Integer> primes = IntStream.range(2, num).filter(item -> isPrime(item)).boxed().collect(Collectors.toList());
        System.out.println(primes);

        Map<Boolean, List<Integer>> partitionResult = IntStream.range(2, num).boxed().collect(Collectors.partitioningBy(item -> isPrime(item)));;
        System.out.println(partitionResult);

        partitionResult = IntStream.range(2, num).boxed().collect(new PrimeCollector());
        System.out.println(partitionResult);

        partitionResult = IntStream.range(2, num).boxed().collect(
            () -> {
                Map<Boolean, List<Integer>> acc = new HashMap<>();
                acc.put(true, new ArrayList<>());
                acc.put(false, new ArrayList<>());
                return acc;
            },
            (acc, candidate) -> acc.get(PrimeCollector.isPrime(acc.get(true), candidate)).add(candidate),
            (map1, map2) -> {
                map1.get(true).addAll(map2.get(true));
                map1.get(false).addAll(map2.get(false));
            }
        );
        System.out.println(partitionResult);

//        long t1 = System.currentTimeMillis();
//        for (int i = 0; i < 10; i++) {
//            IntStream.range(2, num).boxed().collect(Collectors.partitioningBy(item -> isPrime(item)));
//        }
//        long t2 = System.currentTimeMillis();
//        System.out.println(t2 - t1);
//
//        for (int i = 0; i < 10; i++) {
//            IntStream.range(2, num).boxed().collect(new PrimeCollector());
//        }
//        System.out.println(System.currentTimeMillis() - t2);
    }

    /**
     * 判断 n 是否是质数
     * @param num
     * @return
     */
    private static boolean isPrime(int num) {
        int candiate = (int) Math.sqrt(num) + 1;
        return IntStream.range(2, candiate).noneMatch(i -> num % i == 0);
    }

}
