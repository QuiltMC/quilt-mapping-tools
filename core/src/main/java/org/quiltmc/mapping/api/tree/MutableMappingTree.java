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

package org.quiltmc.mapping.api.tree;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;

/**
 * A mutable representation of a Mapping Tree.
 */
public interface MutableMappingTree extends MappingTree {
	@Override
	Collection<? extends MutableMappingEntry<?>> getEntries();

	@Override
	Collection<? extends MutableClassEntry> classes();

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
	@SuppressWarnings("OptionalGetWithoutIsPresent")
	default MutableClassEntry getOrCreateClassEntry(String fromName, List<String> toNames) {
		return this.hasClassEntry(fromName) ? this.classEntry(fromName).get() : this.createClassEntry(fromName, toNames);
	}

	/**
	 * @param entry an entry to add to the tree
	 */
	void addEntry(MutableMappingEntry<?> entry);
}
