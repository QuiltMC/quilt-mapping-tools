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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.naming.ClassEntryImpl;

public interface ClassEntry extends ParentMappingEntry<ClassEntry>, NamedMappingEntry<ClassEntry> {
	MappingType<ClassEntry> CLASS_MAPPING_TYPE = MappingTypes.register(
		new MappingType<>("class",
			ClassEntry.class,
			type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE),
			Parsers.createParent(
				Parsers.STRING.field("from", ClassEntry::getFromName),
				Parsers.STRING.nullableField("to", ClassEntry::getToName),
				() -> ClassEntry.CLASS_MAPPING_TYPE,
				ClassEntryImpl::new
			)));

	@Override
	default MappingType<ClassEntry> getType() {
		return CLASS_MAPPING_TYPE;
	}

	Collection<? extends FieldEntry> getFields();

	Map<String, ? extends FieldEntry> getFieldsByName();

	Optional<? extends FieldEntry> getFieldMapping(String fromName);

	boolean hasFieldMapping(String fromName);

	Collection<? extends MethodEntry> getMethods();

	Map<String, ? extends MethodEntry> getMethodsByName();

	Optional<? extends MethodEntry> getMethodMapping(String fromName);

	boolean hasMethodMapping(String fromName);

	Collection<? extends ClassEntry> getClasses();

	Map<String, ? extends ClassEntry> getClassesByName();

	Optional<? extends ClassEntry> getClassMapping(String fromName);

	boolean hasClassMapping(String fromName);
}
