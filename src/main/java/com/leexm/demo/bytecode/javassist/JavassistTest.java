package com.leexm.demo.bytecode.javassist;

import javassist.*;

/**
 * @author leexm
 * @date 2020-04-20 00:17
 */
public class JavassistTest {

    public static void main(String[] args) throws NotFoundException, CannotCompileException, IllegalAccessException, InstantiationException {
//        // Base 进行过加载的话，Javaasist 这样是无法重新加载 Base 的
//        Base base = new Base();
//        base.process();

        ClassPool classPool = ClassPool.getDefault();
        CtClass clazz = classPool.get("com.leexm.demo.bytecode.javassist.Base");
        CtMethod method = clazz.getDeclaredMethod("process");
        method.insertBefore("{System.out.println(\"Start...\");}");
        method.insertAfter("{System.out.println(\"end...\");}");
        Class enClazz = clazz.toClass();
        Base base1 = (Base) enClazz.newInstance();
        base1.process();
    }

}
