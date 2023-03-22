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

package org.quiltmc.annotation_replacement.api.entry;

import java.util.List;

import org.quiltmc.annotation_replacement.api.entry.value.AnnotationValue;
import org.quiltmc.annotation_replacement.impl.entry.AnnotationAdditionEntryImpl;
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.serialization.Builder;
import org.quiltmc.mapping.api.serialization.Serializer;

public interface AnnotationAdditionEntry extends DescriptorMappingEntry<AnnotationAdditionEntry>, WriteableAnnotationInformation {
	Serializer<AnnotationAdditionEntry> SERIALIZER = Builder.EntryBuilder.<AnnotationAdditionEntry>entry()
			.string("descriptor", AnnotationAdditionEntry::descriptor)
			.field("values", AnnotationValue.COMBINED_SERIALIZER.list(), value -> List.copyOf(value.values()))
			.build(args -> new AnnotationAdditionEntryImpl(args.get("descriptor"), args.get("values")));

	MappingType<AnnotationAdditionEntry> ANNOTATION_ADDITION_MAPPING_TYPE = new MappingType<>(
			"additions",
			AnnotationAdditionEntry.class,
			type -> type.equals(AnnotationModificationEntry.ANNOTATION_MODIFICATION_MAPPING_TYPE),
			SERIALIZER);

	@Override
	default MappingType<AnnotationAdditionEntry> getType() {
		return ANNOTATION_ADDITION_MAPPING_TYPE;
	}
}
