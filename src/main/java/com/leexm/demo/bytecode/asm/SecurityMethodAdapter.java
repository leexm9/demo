package com.leexm.demo.bytecode.asm;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author leexm
 * @date 2020-04-19 20:43
 */
public class SecurityMethodAdapter extends MethodAdapter {

    public SecurityMethodAdapter(MethodVisitor mv) {
        super(mv);
    }

    @Override
    public void visitCode() {
        super.visitMethodInsn(Opcodes.INVOKESTATIC, "Security", "checkSecurity", "()V");
    }
}
