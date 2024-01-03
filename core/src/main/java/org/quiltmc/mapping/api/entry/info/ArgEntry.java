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

package org.quiltmc.mapping.api.entry.info;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.info.ArgEntryImpl;

/**
 * Represents a mapping entry for a method argument or LVT entry.
 */
public interface ArgEntry extends ParentMappingEntry<ArgEntry> {

	/**
	 * The Mapping Type for Arguments
	 */
	MappingType<ArgEntry> ARG_MAPPING_TYPE = MappingTypes.register(
		new MappingType<>(
			"arg",
			ArgEntry.class,
			mappingType -> mappingType.equals(MethodEntry.METHOD_MAPPING_TYPE),
			Parsers
				.createParent(
					Parsers.INTEGER.field("index", ArgEntry::index),
					Parsers.STRING.greedyList().field("names", ArgEntry::names),
					() -> ArgEntry.ARG_MAPPING_TYPE,
					ArgEntry::arg
				)
		));


	/**
	 * Creates a new Argument entry.
	 *
	 * @param index    the argument index
	 * @param names    the names for the argument
	 * @param children the children for the argument
	 * @return the new entry
	 */
	static ArgEntry arg(int index, List<String> names, Collection<MappingEntry<?>> children) {
		return new ArgEntryImpl(index, names, children);
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return ParentMappingEntry.super.shouldMerge(other) && this.index() == ((ArgEntry) other).index();
	}

	/**
	 * @return the index for the arguemnt
	 */
	int index();

	/**
	 * @return the names for the argument in the different namespaces
	 */
	List<String> names(); // TODO: these need to add one to the list index so that 0 can be the from name

	/**
	 * @param namespace the namespace to query
	 * @return the argument name in the specified namespace
	 */
	Optional<String> name(int namespace);

	/**
	 * @param namespace the namespace to query
	 * @return true is the argument name is not null
	 */
	boolean hasName(int namespace);

	@Override
	default MappingType<ArgEntry> getType() {
		return ARG_MAPPING_TYPE;
	}
}
