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

package org.quiltmc.annotation_replacement.api.entry.value;

import java.util.Collection;
import java.util.List;

import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.WriteableAnnotationInformation;
import org.quiltmc.annotation_replacement.impl.entry.value.NestedAnnotationValueImpl;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.parse.Parsers;

@SuppressWarnings("unchecked")
public interface NestedAnnotationValue extends AnnotationValue<Collection<? extends AnnotationValue<?, ?>>, NestedAnnotationValue>, ParentMappingEntry<NestedAnnotationValue>, WriteableAnnotationInformation {
	MappingType<NestedAnnotationValue> NESTED_MAPPING_TYPE =
		MappingTypes.register(
			new MappingType<>(
				"nested",
				NestedAnnotationValue.class,
				type -> type.equals(AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE) || type.equals(NestedAnnotationValue.NESTED_MAPPING_TYPE),
				Parsers.createParent(
					Parsers.STRING.field("name", NestedAnnotationValue::name),
					Parsers.STRING.field("descriptor", NestedAnnotationValue::descriptor),
					() -> NestedAnnotationValue.NESTED_MAPPING_TYPE,
					(name, descriptor, values) -> {
						List<AnnotationValue<?, ?>> list = (List<AnnotationValue<?, ?>>) (Object) values.stream().map(AnnotationValue.class::cast).toList();
						return new NestedAnnotationValueImpl(name, list, descriptor);
					}
				)
			));

	@Override
	default Collection<? extends MappingEntry<?>> children() {
		return this.values();
	}

	@Override
	default MappingType<NestedAnnotationValue> getType() {
		return NESTED_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		boolean sameType = AnnotationValue.super.shouldMerge(other);
		if (sameType) {
			NestedAnnotationValue eav = ((NestedAnnotationValue) other);
			return this.name().equals(eav.name());
		}
		return false;
	}
}
