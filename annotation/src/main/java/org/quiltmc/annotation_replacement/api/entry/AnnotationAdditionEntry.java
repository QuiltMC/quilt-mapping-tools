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
import java.util.List;

import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.impl.entry.AnnotationAdditionEntryImpl;
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.parse.Parsers;

@SuppressWarnings("unchecked")
public interface AnnotationAdditionEntry extends DescriptorMappingEntry<AnnotationAdditionEntry>, ParentMappingEntry<AnnotationAdditionEntry>, WriteableAnnotationInformation {

	MappingType<AnnotationAdditionEntry> ANNOTATION_ADDITION_MAPPING_TYPE = MappingTypes.register(new MappingType<>(
		"addition",
		AnnotationAdditionEntry.class,
		type -> type.equals(AnnotationModificationEntry.ANNOTATION_MODIFICATION_MAPPING_TYPE),
		Parsers.createParent(
			Parsers.STRING.field("descriptor", AnnotationAdditionEntry::descriptor),
			() -> AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE,
			(descriptor, values) -> {
				List<AnnotationValue<?, ?>> list = (List<AnnotationValue<?, ?>>) (Object) values.stream().map(AnnotationValue.class::cast).toList();
				return new AnnotationAdditionEntryImpl(descriptor, list);
			}
		)
	));

	@Override
	default Collection<? extends MappingEntry<?>> children() {
		return this.values();
	}

	@Override
	default MappingType<AnnotationAdditionEntry> getType() {
		return ANNOTATION_ADDITION_MAPPING_TYPE;
	}
}
