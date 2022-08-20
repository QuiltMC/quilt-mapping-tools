package org.quiltmc.annotation_replacement;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.quiltmc.mapping.entry.annotation.AnnotationAddition;
import org.quiltmc.mapping.entry.annotation.AnnotationModifications;

public class AnnotationReplacement {
    // TODO: support parameter and return type annotations
    public static class AnnotationReplacementMethodVisitor extends MethodVisitor {
        private final AnnotationModifications modifications;

        protected AnnotationReplacementMethodVisitor(AnnotationModifications modifications) {
            super(Opcodes.ASM9);
            this.modifications = modifications;
        }

        public AnnotationReplacementMethodVisitor(MethodVisitor methodVisitor, AnnotationModifications modifications) {
            super(Opcodes.ASM9, methodVisitor);
            this.modifications = modifications;
        }

        @Override
        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
            if (modifications.removals().stream().anyMatch(removal -> removal.descriptor().equals(descriptor))) {
                return null;
            }
            return super.visitAnnotation(descriptor, visible);
        }

        @Override
        public void visitEnd() {
            for (AnnotationAddition addition : modifications.additions()) {
                addition.visit(this.visitAnnotation(addition.descriptor(), true));
            }
            super.visitEnd();
        }
    }
}
