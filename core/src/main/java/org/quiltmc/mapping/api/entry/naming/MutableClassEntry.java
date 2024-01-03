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
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.quiltmc.mapping.api.entry.mutable.MutableNamedMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableParentMappingEntry;

/**
 * A Mutable representation for a Class Entry.
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
public interface MutableClassEntry extends ClassEntry, MutableParentMappingEntry<ClassEntry>, MutableNamedMappingEntry<ClassEntry> {
	@Override
	Collection<? extends MutableFieldEntry> fields();

	@Override
	Map<String, ? extends MutableFieldEntry> fieldsByName();

	@Override
	Optional<? extends MutableFieldEntry> field(String fromName);

	/**
	 * Creates and adds a new field to this class
	 *
	 * @param fromName   the from name for the field
	 * @param toNames    the to names for the field
	 * @param descriptor the field descriptor
	 * @return the new field entry
	 */
	MutableFieldEntry createFieldEntry(String fromName, List<String> toNames, String descriptor);

	/**
	 * Gets the field if it exists or creates and adds a new field to this class
	 *
	 * @param fromName   the from name for the field
	 * @param toNames    the to names for the field
	 * @param descriptor the field descriptor
	 * @return the field entry
	 */
	default MutableFieldEntry getOrCreateFieldEntry(String fromName, List<String> toNames, String descriptor) {
		return this.hasField(fromName) ? this.field(fromName).get() : this.createFieldEntry(fromName, toNames, descriptor);
	}

	@Override
	Collection<? extends MutableMethodEntry> methods();

	@Override
	Map<String, ? extends MutableMethodEntry> methodsByName();

	@Override
	Optional<? extends MutableMethodEntry> method(String fromName);

	/**
	 * Creates and adds a new method to this class
	 *
	 * @param fromName   the from name for the method
	 * @param toNames    the to names for the method
	 * @param descriptor the method descriptor
	 * @return the new method entry
	 */
	MutableMethodEntry createMethodEntry(String fromName, List<String> toNames, String descriptor);

	/**
	 * Gets the method if it exists or creates and adds a new method to this class
	 *
	 * @param fromName   the from name for the method
	 * @param toNames    the to names for the method
	 * @param descriptor the method descriptor
	 * @return the method entry
	 */
	default MutableMethodEntry getOrCreateMethodEntry(String fromName, List<String> toNames, String descriptor) {
		return this.hasMethod(fromName) ? this.method(fromName).get() : this.createMethodEntry(fromName, toNames, descriptor);
	}

	@Override
	Collection<? extends MutableClassEntry> classes();

	@Override
	Map<String, ? extends MutableClassEntry> classesByName();

	@Override
	Optional<? extends MutableClassEntry> classEntry(String fromName);

	/**
	 * Creates and adds a new subclass to this class
	 *
	 * @param fromName the from name for the class
	 * @param toNames  the to names for the class
	 * @return the new class entry
	 */
	MutableClassEntry createClassEntry(String fromName, List<String> toNames);

	/**
	 * Gets the class if it exists or creates and adds a new subclass to this class
	 *
	 * @param fromName the from name for the class
	 * @param toNames  the to names for the class
	 * @return the class entry
	 */
	default MutableClassEntry getOrCreateClassEntry(String fromName, List<String> toNames) {
		return this.hasClass(fromName) ? this.classEntry(fromName).get() : this.createClassEntry(fromName, toNames);
	}
}
