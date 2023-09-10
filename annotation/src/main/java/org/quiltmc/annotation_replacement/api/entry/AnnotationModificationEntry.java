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
import java.util.Set;

import org.quiltmc.annotation_replacement.impl.entry.AnnotationModificationEntryImpl;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.ReturnEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.parse.Parsers;

public interface AnnotationModificationEntry extends ParentMappingEntry<AnnotationModificationEntry> {
	MappingType<AnnotationModificationEntry> ANNOTATION_MODIFICATION_MAPPING_TYPE =
		MappingTypes.register(new MappingType<>(
			"annotation_modifications",
			AnnotationModificationEntry.class,
			type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE) ||
					type.equals(MethodEntry.METHOD_MAPPING_TYPE) ||
					type.equals(FieldEntry.FIELD_MAPPING_TYPE) ||
					type.equals(ArgEntry.ARG_MAPPING_TYPE) ||
					type.equals(ReturnEntry.RETURN_MAPPING_TYPE),
			Parsers.createParent(
				Parsers.STRING.set().field("removals", AnnotationModificationEntry::removals),
				() -> AnnotationModificationEntry.ANNOTATION_MODIFICATION_MAPPING_TYPE,
				(removals, additions) -> new AnnotationModificationEntryImpl(removals, additions.stream().map(AnnotationAdditionEntry.class::cast).toList())
			)));

	Set<String> removals();

	Collection<? extends AnnotationAdditionEntry> additions();

	@Override
	default MappingType<AnnotationModificationEntry> getType() {
		return ANNOTATION_MODIFICATION_MAPPING_TYPE;
	}

	@Override
	default Collection<? extends MappingEntry<?>> children() {
		return this.additions();
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return ParentMappingEntry.super.shouldMerge(other);
	}

	@Override
	default AnnotationModificationEntry merge(MappingEntry<?> other) {
		return null; // TODO Implement me
	}
}
