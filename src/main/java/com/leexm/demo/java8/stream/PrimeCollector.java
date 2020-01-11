package com.leexm.demo.java8.stream;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * 质数收集器
 *
 * @author leexm
 * @date 2020-01-04 17:43
 */
public class PrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> {

    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {
            {
                put(true, new ArrayList<>());
                put(false, new ArrayList<>());
            }
        };
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return (acc, candidate) -> acc.get(isPrime(acc.get(true), candidate)).add(candidate);
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (map1, map2) -> {
            map1.get(true).addAll(map2.get(true));
            map1.get(false).addAll(map2.get(false));
            return map1;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    static boolean isPrime(List<Integer> primes, int candidate) {
        int temp = (int) Math.sqrt(candidate);
        return takeWhile(primes, item -> item <= temp).stream().noneMatch(item -> candidate % item == 0);
    }

    private static <T> List<T> takeWhile(List<T> list, Predicate<T> p) {
        int i = 0;
        for (T item : list) {
            if (!p.test(item)) {
                return list.subList(0, i);
            }
            i++;
        }
        return list;
    }

}
