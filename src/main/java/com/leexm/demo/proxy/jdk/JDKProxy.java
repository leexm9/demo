package com.leexm.demo.proxy.jdk;

import com.leexm.demo.proxy.Car;
import com.leexm.demo.proxy.Cat;
import com.leexm.demo.proxy.Dog;
import com.leexm.demo.proxy.intf.Move;
import com.leexm.demo.proxy.intf.Say;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @author leexm
 * @date 2021-01-02 10:40
 */
public class JDKProxy {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        Class<?>[] inter1 = new Class[2];
        inter1[0] = Move.class;
        inter1[1] = Say.class;

        Class<?>[] inter2 = new Class[1];
        inter2[0] = Move.class;

        Class<?>[] inter3 = new Class[1];
        inter3[0] = Say.class;

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();

        Dog dog = new Dog();
        InvocationHandler handler1 = new MoveHandler(dog);
        Move proxy1 = (Move) Proxy.newProxyInstance(classLoader, inter1, handler1);
        System.out.println(proxy1.move(34));
        System.out.println();

        Car car = new Car();
        InvocationHandler handler2 = new MoveHandler(car);
        Move proxy2 = (Move) Proxy.newProxyInstance(classLoader, inter2, handler2);
        System.out.println(proxy2.move(89));
        System.out.println();

        Cat cat = new Cat();
        InvocationHandler handler3 = new SayHandler(cat);
        Say proxy3 = (Say) Proxy.newProxyInstance(classLoader, inter3, handler3);
        proxy3.say();
        System.out.println();

        Class[] argsClass = new Class[1];
        argsClass[0] = InvocationHandler.class;
        Class<?> zlass1 = classLoader.loadClass("com.sun.proxy.$Proxy1");
        Constructor cons1 = zlass1.getConstructor(argsClass);
        Move move1 = (Move) cons1.newInstance(handler1);
        System.out.println(move1.move(56));
        System.out.println();

        Class[] argsClass2 = new Class[1];
        argsClass2[0] = InvocationHandler.class;
        Class<?> zlass2 = classLoader.loadClass("com.sun.proxy.$Proxy2");
        Constructor cons2 = zlass2.getConstructor(argsClass2);
        Say say1 = (Say) cons2.newInstance(handler3);
        say1.say();
        System.out.println();

        Class[] argsClass3 = new Class[1];
        argsClass3[0] = InvocationHandler.class;
        Class<?> zlass3 = classLoader.loadClass("com.sun.proxy.$Proxy0");
        Constructor cons3 = zlass3.getConstructor(argsClass3);
        Move move2 = (Move) cons3.newInstance(handler2);
        System.out.println(move2.move(89));
        System.out.println();
    }

}
