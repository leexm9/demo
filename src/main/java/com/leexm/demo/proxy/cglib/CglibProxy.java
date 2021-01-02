package com.leexm.demo.proxy.cglib;

import com.leexm.demo.proxy.Car;
import com.leexm.demo.proxy.Cat;
import com.leexm.demo.proxy.Dog;
import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

/**
 * https://www.iteye.com/blog/shensy-1867588
 *
 * @author leexm
 * @date 2021-01-02 10:54
 */
public class CglibProxy {

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/leexm/workspace/java/demo");

        Callback[] callbacks = new Callback[4];
        callbacks[0] = new MoveInterceptor();
        callbacks[1] = new SayInterceptor();
        callbacks[2] = NoOp.INSTANCE;
        callbacks[3] = new MoveFixedValue();

        CallbackFilter filter = new ProxyCallbackFilter();

        Enhancer enhancer1 = new Enhancer();
        enhancer1.setSuperclass(Dog.class);
        enhancer1.setCallbacks(callbacks);
        enhancer1.setCallbackFilter(filter);
        Dog dog = (Dog) enhancer1.create();
        System.out.println(dog.move(23));
        dog.say();
        System.out.println();

        Enhancer enhancer2 = new Enhancer();
        enhancer2.setSuperclass(Car.class);
        enhancer2.setCallback(callbacks[0]);
        Car car = (Car) enhancer2.create();
        System.out.println(car.move(23));
        System.out.println();

        Enhancer enhancer3 = new Enhancer();
        enhancer3.setSuperclass(Cat.class);
        enhancer3.setCallback(callbacks[1]);
        Cat cat = (Cat) enhancer3.create();
        cat.say();
        System.out.println();

        Enhancer enhancer4 = new Enhancer();
        enhancer4.setSuperclass(Dog.class);
        enhancer4.setCallback(callbacks[1]);
        Dog dog2 = (Dog) enhancer4.create();
        System.out.println(dog2.move(89));
        dog2.say();
        System.out.println();
    }

}
