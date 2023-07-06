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

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.impl.entry.value.EnumAnnotationValueImpl;
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.serialization.Builder;
import org.quiltmc.mapping.api.serialization.Serializer;

public interface EnumAnnotationValue extends AnnotationValue<String, EnumAnnotationValue> {
	Serializer<EnumAnnotationValue> SERIALIZER = Builder.EntryBuilder.<EnumAnnotationValue>entry()
			.string("name", AnnotationValue::name)
			.string("descriptor", DescriptorMappingEntry::descriptor)
			.string("value", AnnotationValue::value)
			.build(args -> new EnumAnnotationValueImpl(args.get("name"), args.get("value"), args.get("descriptor")));

	MappingType<EnumAnnotationValue> ENUM_MAPPING_TYPE =
			MappingTypes.register(
					new MappingType<>(
							"enum_annotation_value",
							EnumAnnotationValue.class,
							type -> type.equals(AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE),
							SERIALIZER
					));

	@Override
	default ValueType type() {
		return ValueType.ENUM;
	}

	@Override
	default MappingType<EnumAnnotationValue> getType() {
		return ENUM_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		boolean sameType = AnnotationValue.super.shouldMerge(other);
		if (sameType) {
			EnumAnnotationValue eav = ((EnumAnnotationValue) other);
			return this.name().equals(eav.name());
		}
		return false;
	}

	@Override
	default EnumAnnotationValue merge(MappingEntry<?> other) {
		// TODO: Always prefer this overriding the other entry? maybe adding a strategy system for this?
		return this;
	}
}
