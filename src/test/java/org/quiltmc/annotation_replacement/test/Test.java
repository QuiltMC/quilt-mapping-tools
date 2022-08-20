package org.quiltmc.annotation_replacement.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.quiltmc.annotation_replacement.AnnotationReplacement;
import org.quiltmc.mapping.entry.annotation.AnnotationAddition;
import org.quiltmc.mapping.entry.annotation.AnnotationModifications;
import org.quiltmc.mapping.entry.annotation.AnnotationRemovalMapping;
import org.quiltmc.mapping.entry.annotation.value.AnnotationAnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.EnumAnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.LiteralAnnotationValue;

public class Test {
    public static void main(String[] args) throws IOException {
        AnnotationModifications modifications = new AnnotationModifications(
                List.of(new AnnotationRemovalMapping("Lorg/quiltmc/annotation_replacement/test/NestedAnnotation;"), new AnnotationRemovalMapping("Ljava/lang/Deprecated;")),
                List.of(new AnnotationAddition("Lorg/quiltmc/annotation_replacement/test/TestAnnotation;",
                        List.of(
                                new LiteralAnnotationValue("value", 1, "I"),
                                new LiteralAnnotationValue("extra", "test string", "Ljava/lang/String;"),
                                new LiteralAnnotationValue("array", new boolean[]{true, false, true}, "[Z"),
                                new EnumAnnotationValue("enumValue", "VALUE", "Lorg/quiltmc/annotation_replacement/test/TestEnum;"),
                                new LiteralAnnotationValue("clazz", Type.getType("Lorg/quiltmc/annotation_replacement/test/TestClass;"), "Ljava/lang/Class;"),
                                new AnnotationAnnotationValue("nestedAnnotation", List.of(
                                        new LiteralAnnotationValue("value", 1.0f, "F")
                                ), "Lorg/quiltmc/annotation_replacement/test/NestedAnnotation;")
                        ))));
        byte[] bytes = Test.class.getClassLoader().getResourceAsStream("org/quiltmc/annotation_replacement/test/TestClass.class").readAllBytes();
        ClassReader reader = new ClassReader(bytes);
        ClassWriter writer = new ClassWriter(reader, 0);
        reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                if (modifications.removals().stream().anyMatch(removal -> removal.descriptor().equals("Ljava/lang/Deprecated;"))) {
                    access &= ~Opcodes.ACC_DEPRECATED;
                }
                return new AnnotationReplacement.AnnotationReplacementMethodVisitor(super.visitMethod(access, name, descriptor, signature, exceptions), modifications);
            }
        }, ClassReader.EXPAND_FRAMES);

        bytes = writer.toByteArray();
        Files.write(Path.of(".", "OutputClass.class"), bytes);

        {
            bytes = Test.class.getClassLoader().getResourceAsStream("org/quiltmc/annotation_replacement/test/TestClassExpected.class").readAllBytes();
            reader = new ClassReader(bytes);
            writer = new ClassWriter(reader, 0);
            reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {
                        @Override
                        public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                            return new AnnotationVisitor(Opcodes.ASM9, super.visitAnnotation(descriptor, visible)) {
                                @Override
                                public void visit(String name, Object value) {
                                    super.visit(name, value);
                                }
                            };
                        }
                    };
                }
            }, ClassReader.EXPAND_FRAMES);
        }
        System.out.println(modifications);
    }
}
