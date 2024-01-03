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

package org.quiltmc.mapping.api.tree;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;

/**
 * Represents a full set of mappings.
 */
public interface MappingTree {
	/**
	 * @return the base namespace of the tree
	 */
	String fromNamespace();

	/**
	 *
	 * @return the destination namespaces of the tree
	 */
	List<String> toNamespaces();

	/**
	 *
	 * @return a collection of all the root level entries in the tree
	 */
	Collection<? extends MappingEntry<?>> getEntries();

	/**
	 * Collects all root level entries of the given type from the tree.
	 *
	 * @param type the type to find
	 * @return the collected entries
	 * @param <C> the entry type
	 */
	default <C extends MappingEntry<C>> Collection<C> getEntriesOfType(MappingType<C> type) {
		return getEntries().stream().filter(mapping -> mapping.getType().equals(type)).map(type.targetEntry()::cast).toList();
	}

	/**
	 * Streams all root level entries of the given type from the tree.
	 *
	 * @param type the type to find
	 * @return the streamed entries
	 * @param <C> the entry type
	 */
	default <C extends MappingEntry<C>> Stream<C> streamEntriesOfType(MappingType<C> type) {
		return getEntries().stream().filter(mapping -> mapping.getType().equals(type)).map(type.targetEntry()::cast);
	}

	/**
	 * @return all the root level classes in the tree
	 */
	Collection<? extends ClassEntry> classes();

	/**
	 * @param fromName the name of the class
	 * @return the class in an optional if found, empty otherwise
	 */
	Optional<? extends ClassEntry> classEntry(String fromName);

	/**
	 * @param fromName the class name
	 * @return true if the class exists
	 */
	boolean hasClassEntry(String fromName);

	/**
	 *
	 * @return a mutable representation of the tree
	 */
	MutableMappingTree makeMutable();

	/**
	 * Combines the other mapping tree with this tree. Merge conflicts default to this tree.
	 * @param with the other mapping tree
	 * @return the merged tree
	 */
	MappingTree merge(MappingTree with);

	/**
	 * Combines the other mapping tree into the destination tree. Merge conflicts default to this tree.
	 * @param with the other mapping tree
	 * @param dest the mapping tree to merge into
	 * @return the merged tree
	 */
	MappingTree merge(MappingTree with, MutableMappingTree dest);

	/**
	 * @return creates a deep copy of this mapping tree
	 */
	MappingTree copy();
}
