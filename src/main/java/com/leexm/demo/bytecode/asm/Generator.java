package com.leexm.demo.bytecode.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author leexm
 * @date 2020-04-19 20:48
 */
public class Generator {

    public Account generate() throws IOException, IllegalAccessException, InstantiationException {
        // 读取
        ClassReader classReader = new ClassReader("com.leexm.demo.bytecode.asm.Account");
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        // 处理
        ClassAdapter classAdapter = new SecurityClassAdapter(classWriter);
        classReader.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = classWriter.toByteArray();
//        File file = new File("/Users/leexm/Desktop/Account.class");
//        try (FileOutputStream fout = new FileOutputStream(file)) {
//            fout.write(data);
//        }
        AccountClassLoader classLoader = new AccountClassLoader();
        Class clazz = classLoader.defineClassFromClassFile("Account$EnhancedByASM", data);
        return (Account) clazz.newInstance();
    }

    private class AccountClassLoader extends ClassLoader {
        public Class defineClassFromClassFile(String className, byte[] data) throws ClassFormatError {
            return defineClass(className, data, 0, data.length);
        }
    }

    public static void main(String[] args) {
        Generator generator = new Generator();
        try {
            generator.generate().operation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        account.operation();
    }

}
