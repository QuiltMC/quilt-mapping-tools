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

import org.quiltmc.mapping.api.entry.info.MutableArgEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableDescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableNamedMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableParentMappingEntry;

/**
 * A mutable representation of a Method Entry.
 */
@SuppressWarnings("OptionalGetWithoutIsPresent")
public interface MutableMethodEntry extends MethodEntry, MutableNamedMappingEntry<MethodEntry>, MutableDescriptorMappingEntry<MethodEntry>, MutableParentMappingEntry<MethodEntry> {
	@Override
	Collection<? extends MutableArgEntry> args();

	@Override
	Map<Integer, ? extends MutableArgEntry> argsByIndex();

	@Override
	Optional<? extends MutableArgEntry> arg(int index);

	/**
	 * Creates and adds a new argument to this method
	 *
	 * @param index the argument index
	 * @param names the argument names
	 * @return the new argument entry
	 */
	MutableArgEntry createArgEntry(int index, List<String> names);

	/**
	 * Gets the argument if it exists or creates and adds a new argument to this method
	 *
	 * @param index the argument index
	 * @param names the argument names
	 * @return the argument entry
	 */
	default MutableArgEntry getOrCreateArgEntry(int index, List<String> names) {
		return this.hasArg(index) ? this.arg(index).get() : this.createArgEntry(index, names);
	}
}
