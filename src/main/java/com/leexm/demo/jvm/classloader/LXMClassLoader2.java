package com.leexm.demo.jvm.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 打破双亲委派机制
 *
 * @author leexm
 * @date 2020-11-01 17:24
 */
public class LXMClassLoader2 extends ClassLoader {

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            String clazzName = name.replace(".", File.separator).concat(".class");
            File file = new File(this.getClass().getResource("/").getPath(), clazzName);
            Class<?> clazz;
            // 如果 class 文件找的到，由自身 load，而不是交由上层加载器加载
            if (!file.exists()) {
                clazz = super.loadClass(name);
            } else {
                // 已经加载过的，直接返回
                clazz = findLoadedClass(name);
                if (clazz == null) {
                    try (FileChannel fileChannel = new FileInputStream(file).getChannel()) {
                        ByteBuffer buffer = ByteBuffer.allocate((int) fileChannel.size());
                        fileChannel.read(buffer);
                        buffer.flip();
                        clazz = this.defineClass(name, buffer.array(), 0, buffer.limit());
                    } catch (IOException e) {
                        throw new ClassNotFoundException(String.format("%s not found", name));
                    }
                }
            }
            return clazz;
        }
    }

}
