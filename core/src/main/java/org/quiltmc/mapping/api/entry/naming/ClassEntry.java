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

/**
 * Represents a Mapping Entry for a Class.
 */
public interface ClassEntry extends ParentMappingEntry<ClassEntry>, NamedMappingEntry<ClassEntry> {
	/**
	 * The Mapping Type for Classes
	 */
	MappingType<ClassEntry> CLASS_MAPPING_TYPE = MappingTypes.register(
		new MappingType<>("class",
			ClassEntry.class,
			type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE),
			Parsers.createParent(
				Parsers.STRING.field("from", ClassEntry::fromName),
				Parsers.STRING.greedyList().field("to", ClassEntry::toNames),
				() -> ClassEntry.CLASS_MAPPING_TYPE,
				ClassEntryImpl::new
			)));

	@Override
	default MappingType<ClassEntry> getType() {
		return CLASS_MAPPING_TYPE;
	}

	/**
	 * @return the fields on this class
	 */
	Collection<? extends FieldEntry> fields();

	/**
	 * @return a map of the field's fromName to the field
	 */
	Map<String, ? extends FieldEntry> fieldsByName();

	/**
	 * @param fromName the field name
	 * @return the field wrapped in an optional if it exists, empty otherwise
	 */
	Optional<? extends FieldEntry> field(String fromName);

	/**
	 * @param fromName the field name
	 * @return true if the field exists
	 */
	boolean hasField(String fromName);

	/**
	 * @return the methods on this class
	 */
	Collection<? extends MethodEntry> methods();

	/**
	 * @return a map of the method's fromName to the method
	 */
	Map<String, ? extends MethodEntry> methodsByName();

	/**
	 * @param fromName the method name
	 * @return the method wrapped in an optional if it exists, empty otherwise
	 */
	Optional<? extends MethodEntry> method(String fromName);

	/**
	 * @param fromName the method name
	 * @return true if the method exists
	 */
	boolean hasMethod(String fromName);

	/**
	 * @return the inner classes to this class
	 */
	Collection<? extends ClassEntry> classes();

	/**
	 * @return a map of the class's fromName to the class
	 */
	Map<String, ? extends ClassEntry> classesByName();

	/**
	 * @param fromName the class name
	 * @return the class wrapped in an optional if it exists, otherwise empty
	 */
	Optional<? extends ClassEntry> classEntry(String fromName);

	/**
	 * @param fromName the class name
	 * @return true if the class exists
	 */
	boolean hasClass(String fromName);
}
