package com.leexm.demo.java8.stream;

import com.leexm.demo.java8.stream.object.Trader;
import com.leexm.demo.java8.stream.object.Transaction;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author leexm
 * @date 2020-01-03 19:31
 */
public class PutIntoPractice {

    public static void main(String[] args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario","Milan");
        Trader alan = new Trader("Alan","Cambridge");
        Trader brian = new Trader("Brian","Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 2011年的交易，排序（从小到大）输出
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparing(Transaction::getValue))
                .collect(Collectors.toList());
        System.out.println(tr2011);

        // 交易员分布在哪些城市
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());
        System.out.println(cities);

        // 来自剑桥的交易员，并按姓名排序
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());
        System.out.println(traders);

        // 所有交易员的姓名字符串，按字母顺序排序
        String names = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                // 效率不高
                .reduce("", (name1, name2) -> name1 + name2);
        System.out.println(names);
        names = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .collect(Collectors.joining());
        System.out.println(names);

        // 有没有在米兰工作的交易员
        Optional<Trader> milanTrader = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Milan"))
                .findAny();
        milanTrader.ifPresent(System.out::println);

        boolean flag = transactions.stream().anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(flag);

        // 输出所有在剑桥的交易员的交易额
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .forEach(transaction -> System.out.println(transaction.getValue()));

        // 最高交易额
        Optional<Integer> max = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);
        max.ifPresent(System.out::println);

        // 最小交易额的交易
        Optional<Transaction> minTransaction = transactions.stream()
                .reduce((t1, t2) -> t1.getValue() < t2.getValue() ? t1 : t2);
        minTransaction.ifPresent(System.out::println);

        minTransaction = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
        minTransaction.ifPresent(System.out::println);
    }

}
