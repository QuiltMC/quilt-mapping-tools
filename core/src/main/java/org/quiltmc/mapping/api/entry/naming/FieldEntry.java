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

package org.quiltmc.mapping.api.entry.naming;

import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.naming.FieldEntryImpl;

/**
 * Represents a mapping entry for a Field.
 */
public interface FieldEntry extends NamedMappingEntry<FieldEntry>, DescriptorMappingEntry<FieldEntry>, ParentMappingEntry<FieldEntry> {
	/**
	 * The Mapping Type for Fields
	 */
	MappingType<FieldEntry> FIELD_MAPPING_TYPE = MappingTypes.register(
		new MappingType<>("field",
			FieldEntry.class,
			type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE),
			Parsers.createParent(
				Parsers.STRING.field("from", FieldEntry::fromName),
				Parsers.STRING.field("descriptor", FieldEntry::descriptor),
				Parsers.STRING.greedyList().field("to", FieldEntry::toNames),
				() -> FieldEntry.FIELD_MAPPING_TYPE,
				FieldEntryImpl::new
			)));

	@Override
	default MappingType<FieldEntry> getType() {
		return FIELD_MAPPING_TYPE;
	}
}
