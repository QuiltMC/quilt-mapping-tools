/*
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.quiltmc.annotation_replacement.test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.quiltmc.annotation_replacement.AnnotationReplacement;
import org.quiltmc.annotation_replacement.api.entry.MutableAnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.MutableAnnotationModificationEntry;
import org.quiltmc.annotation_replacement.impl.entry.MutableAnnotationAdditionEntryImpl;
import org.quiltmc.annotation_replacement.impl.entry.MutableAnnotationModificationEntryImpl;
import org.quiltmc.annotation_replacement.impl.entry.value.MutableEnumAnnotationValueImpl;
import org.quiltmc.annotation_replacement.impl.entry.value.MutableLiteralAnnotationValueImpl;
import org.quiltmc.annotation_replacement.impl.entry.value.MutableNestedAnnotationValueImpl;
import org.quiltmc.annotation_replacement.impl.entry.value.LiteralAnnotationValueImpl;

public class Test {
	public static void main(String[] args) throws IOException {
		MutableAnnotationAdditionEntry addition = new MutableAnnotationAdditionEntryImpl("Lorg/quiltmc/annotation_replacement/test/TestAnnotation;", List.of());
		addition.addValue(new MutableLiteralAnnotationValueImpl("value", 1, "I"));
		addition.addValue(new MutableLiteralAnnotationValueImpl("extra", "test string", "Ljava/lang/String;"));
		addition.addValue(new MutableLiteralAnnotationValueImpl("array", new boolean[]{true, false, true}, "[Z"));
		addition.addValue(new MutableEnumAnnotationValueImpl("enumValue", "VALUE", "Lorg/quiltmc/annotation_replacement/test/TestEnum;"));
		addition.addValue(new MutableLiteralAnnotationValueImpl("clazz", Type.getType("Lorg/quiltmc/annotation_replacement/test/TestClass;"), "Ljava/lang/Class;"));
		addition.addValue(new MutableNestedAnnotationValueImpl("nestedAnnotation", List.of(new LiteralAnnotationValueImpl("value", 1.0f, "F")), "Lorg/quiltmc/annotation_replacement/test/NestedAnnotation;"));

		MutableAnnotationModificationEntry modifications = new MutableAnnotationModificationEntryImpl(
				Set.of("Lorg/quiltmc/annotation_replacement/test/NestedAnnotation;", "Ljava/lang/Deprecated;"),
				List.of(addition));



		byte[] bytes = Test.class.getClassLoader().getResourceAsStream("org/quiltmc/annotation_replacement/test/TestClass.class").readAllBytes();
		ClassReader reader = new ClassReader(bytes);
		ClassWriter writer = new ClassWriter(reader, 0);
		reader.accept(new ClassVisitor(Opcodes.ASM9, writer) {
			@Override
			public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
				if (modifications.removals().contains("Ljava/lang/Deprecated;")) {
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
		System.out.println(modifications.makeFinal());
	}
}
