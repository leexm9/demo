package com.leexm.demo.jvm.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 自定义类加载器
 *
 * @author leexm
 * @date 2020-10-31 08:00
 */
public class LXMClassLoader extends ClassLoader {

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String clazzName = name.replace(".", File.separator).concat(".class");
        File file = new File(this.getClass().getResource("/").getPath(), clazzName);
        Class<?> clazz;
        try (FileInputStream fileInputStream = new FileInputStream(file);
             FileChannel fileChannel = fileInputStream.getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
            fileChannel.read(buffer);
            buffer.flip();
            clazz = this.defineClass(name, buffer.array(), 0, buffer.limit());
        } catch (IOException e) {
            throw new ClassNotFoundException(String.format("%s not found", name));
        }
        return clazz != null ? clazz : super.findClass(name);
    }

    public static void main(String[] args) throws ClassNotFoundException {
        ClassLoader loader = new LXMClassLoader();
        Class clazz1 = loader.loadClass("com.leexm.demo.object.Person");
        Class clazz2 = loader.loadClass("com.leexm.demo.object.Person");
        System.out.println(clazz1.hashCode());
        System.out.println(clazz2.hashCode());
        System.out.println(clazz1 == clazz2);

        System.out.println(clazz1.getClassLoader());
        System.out.println(loader.getClass().getClassLoader());
        System.out.println(loader.getParent());
        System.out.println(getSystemClassLoader());
    }

}
