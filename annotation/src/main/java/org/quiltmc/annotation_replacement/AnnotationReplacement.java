/*
 * Copyright 2023 QuiltMC
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
import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.AnnotationModificationEntry;

public class AnnotationReplacement {
	// TODO: support parameter and return type annotations
	public static class AnnotationReplacementMethodVisitor extends MethodVisitor {
		private final AnnotationModificationEntry modifications;
		boolean visited = false;

		protected AnnotationReplacementMethodVisitor(AnnotationModificationEntry modifications) {
			super(Opcodes.ASM9);
			this.modifications = modifications;
		}

		public AnnotationReplacementMethodVisitor(MethodVisitor methodVisitor, AnnotationModificationEntry modifications) {
			super(Opcodes.ASM9, methodVisitor);
			this.modifications = modifications;
		}

		@Override
		public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
			if (!visited) {
				visited = true;
				for (AnnotationAdditionEntry addition : modifications.additions()) {
					addition.visit(this.visitAnnotation(addition.descriptor(), true));
				}
			}

			if (modifications.removals().contains(descriptor)) {
				return null;
			}
			return super.visitAnnotation(descriptor, visible);
		}
	}
}
