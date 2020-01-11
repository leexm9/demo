package com.leexm.demo.java8.stream;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * lambda 表达式的调试方式:peek
 *
 * @author leexm
 * @date 2020-01-05 15:26
 */
public class StreamDebug {

    public static void main(String[] args) {
        IntStream.range(0, 10)
                .peek(x -> System.out.println("from stream:" + x))
                .map(x -> x + 3)
                .peek(x -> System.out.println("after map:" + x))
                .filter(x -> x % 2 == 0)
                .peek(x -> System.out.println("after filter:" + x))
                .boxed()
                .collect(Collectors.toList());
    }

}
