package com.leexm.demo.java8.stream;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * List 排序的几种实现方式
 *
 * @author leexm
 * @date 2020-01-02 17:29
 */
public class FilteringApples {

    public static void main(String[] args) {
        List<Apple> inventory = Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"));

        inventory.sort(new AppleComparator());
        System.out.println(inventory);
        // 打乱顺序
        Collections.shuffle(inventory);

        inventory.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });
        System.out.println(inventory);
        Collections.shuffle(inventory);

        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);
        Collections.shuffle(inventory);

        inventory.sort(Comparator.comparing(a -> a.getWeight()));
        System.out.println(inventory);
        Collections.shuffle(inventory);

        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory);
        Collections.shuffle(inventory);
    }

    private static class Apple {

        private Integer weight;

        private String color;

        public Apple(Integer weight, String color) {
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        @Override
        public String toString() {
            return "Apple{" + "weight=" + weight + ", color='" + color + '\'' + '}';
        }
    }

    private static class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple a1, Apple a2){
            return a1.getWeight().compareTo(a2.getWeight());
        }
    }
}
