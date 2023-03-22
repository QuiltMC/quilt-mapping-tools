/*
 * Copyright 2022-2023 QuiltMC
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

package org.quiltmc.annotation_replacement.api.entry.value;

import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.serialization.Serializer;

public interface AnnotationValue<O, T extends AnnotationValue<O, T>> extends DescriptorMappingEntry<T> {
	Serializer<AnnotationValue<?, ?>> COMBINED_SERIALIZER = Serializer
			.dispatch(ValueType.SERIALIZER, AnnotationValue::type, ValueType::serializer);

	String name();

	O value();

	ValueType type();

	enum ValueType {
		LITERAL(LiteralAnnotationValue.SERIALIZER), ENUM(EnumAnnotationValue.SERIALIZER), ANNOTATION(NestedAnnotationValue.SERIALIZER);

		public static final Serializer<ValueType> SERIALIZER = Serializer.enumSerializer(ValueType.class);
		private final Serializer<? extends AnnotationValue<?, ?>> serializer;

		ValueType(Serializer<? extends AnnotationValue<?, ?>> serializer) {
			this.serializer = serializer;
		}

		public Serializer<? extends AnnotationValue<?, ?>> serializer() {
			return serializer;
		}
	}
}
