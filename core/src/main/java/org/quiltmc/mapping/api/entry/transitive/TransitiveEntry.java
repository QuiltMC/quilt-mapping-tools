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

package org.quiltmc.mapping.api.entry.transitive;

import java.util.Set;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.transitive.TransitiveEntryImpl;

/**
 * Represents a transitive entry. Transitive entries can apply to Classes, Fields, and Methods. The presence of a transitive entry on one of these entries means that the name given to target should be used. This allows for names to be linked between different entries.
 */
public interface TransitiveEntry extends MappingEntry<TransitiveEntry> {
	MappingType<TransitiveEntry> TRANSITIVE_MAPPING_TYPE = MappingTypes.register(new MappingType<>("transitive",
		TransitiveEntry.class,
		type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE) || type.equals(MethodEntry.METHOD_MAPPING_TYPE) || type.equals(FieldEntry.FIELD_MAPPING_TYPE),
		Parsers.create(Parsers.STRING.field("target", TransitiveEntry::target),
			() -> TransitiveEntry.TRANSITIVE_MAPPING_TYPE,
			target -> new TransitiveEntryImpl(target, Set.of()))));

	/**
	 * @return the target for the transitive entry
	 */
	String target();

	/**
	 * @return the subtypes to pull from the entry
	 */
	Set<MappingType<?>> transitiveTypes(); // TODO: how should this work, ie method args dont match

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return MappingEntry.super.shouldMerge(other) && this.target().equals(((TransitiveEntry) other).target());
	}

	@Override
	default MappingType<TransitiveEntry> getType() {
		return TRANSITIVE_MAPPING_TYPE;
	}
}
