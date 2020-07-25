package com.leexm.demo.bytecode.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

/**
 * @author leexm
 * @date 2020-04-19 20:36
 */
public class SecurityClassAdapter extends ClassAdapter {

    private String enhancedSuperName = null;

    /**
     * Responsechain 的下一个 ClassVisitor，这里我们将传入 ClassWriter，
     * 负责改写后代码的输出
     */
    public SecurityClassAdapter(ClassVisitor cv) {
        super(cv);
    }

    /**
     * 重写 visitMethod，访问到"operation"方法时，给出自定的 MethodVisitor，实际改写方法内容
     *
     */
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor wrapped = mv;
        if (mv != null) {
            // 使用自定的 MethodVisitor，实际改写内容
            if ("operation".equals(name)) {
                wrapped = new SecurityMethodAdapter(mv);
            } else if ("<init>".equals(name)) {
                wrapped = new ChangeToChildMethodAdapter(mv, enhancedSuperName);
            }
        }
        return wrapped;
    }

    @Override
    public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
        // 改变类命名
        String enhancedName = s + "$EnhancedByASM";
        // 改变父类，这里是 Account
        enhancedSuperName = s;
        super.visit(i, i1, enhancedName, s1, enhancedSuperName, strings);
    }
}
