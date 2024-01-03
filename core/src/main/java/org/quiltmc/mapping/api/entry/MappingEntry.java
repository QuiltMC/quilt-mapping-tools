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

package org.quiltmc.mapping.api.entry;

import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

/**
 * The basic building block for all mapping entries. This builds a large tree that holds information about a mapping set.
 *
 * @param <T> The mapping entry type
 */
public interface MappingEntry<T extends MappingEntry<T>> {
	T remap(); // TODO: Remove?

	/**
	 * @return the type for the mapping
	 */
	MappingType<T> getType();

	/**
	 * Mapping entries should be able to merge if they both target the same entry.
	 *
	 * @param other the other mapping
	 * @return true if the two mappings should merge
	 */
	default boolean shouldMerge(MappingEntry<?> other) {
		return this.getType().equals(other.getType());
	}

	/**
	 * If two mapping entries target the same entry, combine them.
	 *
	 * @param other the other entry
	 * @return the merged entry
	 */
	@SuppressWarnings("unchecked")
	default T merge(MappingEntry<?> other) {
		return (T) this;
	}

	/**
	 * @return a mutable representation of the current entry
	 */
	MutableMappingEntry<T> makeMutable();
}
