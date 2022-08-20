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
