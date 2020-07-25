package com.leexm.demo.sync;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author leexm
 * @date 2020-03-23 12:12
 */
public class AtomicReferenceDemo {

    private static AtomicReference<User> atomicUserRef = new AtomicReference<>();

    public static void main(String[] args) {
        User user = new User("Tom", 18);
        atomicUserRef.set(user);
        User updateUser = new User("Helen", 25);
        atomicUserRef.compareAndSet(user, updateUser);
        // 执行结果:User{name='Helen', age=25}
        System.out.println(atomicUserRef.get().toString());
    }

    static class User {
        public String name;
        private int age;

        public User(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}
