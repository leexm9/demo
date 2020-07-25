package com.leexm.demo.bytecode.asm;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author leexm
 * @date 2020-04-19 22:17
 */
public class ChangeToChildMethodAdapter extends MethodAdapter {

    private String superName;

    public ChangeToChildMethodAdapter(MethodVisitor methodVisitor, String superName) {
        super(methodVisitor);
        this.superName = superName;
    }



    @Override
    public void visitMethodInsn(int i, String s, String s1, String s2) {
        // 调用父类的构造函数时
        if (i == Opcodes.INVOKESPECIAL && s1.equals("<init>")) {
            s = superName;
        }
        // 改写父类为 superClassName
        super.visitMethodInsn(i, s, s1, s2);
    }
}
