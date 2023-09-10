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

package org.quiltmc.annotation_replacement.api.entry;

import java.util.Collection;

import org.objectweb.asm.AnnotationVisitor;
import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.EnumAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.LiteralAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.NestedAnnotationValue;

public interface WriteableAnnotationInformation {
	String descriptor();

	Collection<? extends AnnotationValue<?, ?>> values();

	default void visit(AnnotationVisitor visitor) {
		for (AnnotationValue<?, ?> value : values()) {
			if (value instanceof LiteralAnnotationValue literal) {
				if (literal.descriptor().startsWith("[")) {
					if (literal.value().getClass().componentType().isPrimitive()) {
						visitor.visit(literal.name(), literal.value());
					} else {
						AnnotationVisitor arrayVisitor = visitor.visitArray(literal.name());
						for (Object arrayValue : (Object[]) literal.value()) {
							arrayVisitor.visit(null, arrayValue);
						}
					}
				} else {
					visitor.visit(literal.name(), literal.value());
				}
			} else if (value instanceof NestedAnnotationValue annotation) {
				AnnotationVisitor annotationVisitor = visitor.visitAnnotation(annotation.name(), annotation.descriptor());
				annotation.visit(annotationVisitor);
				annotationVisitor.visitEnd();
			} else if (value instanceof EnumAnnotationValue enumValue) {
				visitor.visitEnum(enumValue.name(), enumValue.descriptor(), enumValue.value());
			}
		}
	}
}
