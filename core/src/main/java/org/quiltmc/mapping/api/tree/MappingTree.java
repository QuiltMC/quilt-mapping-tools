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
import java.util.Optional;
import java.util.stream.Stream;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;

public interface MappingTree {
	String getFromNamespace();

	String getToNamespace();

	Collection<? extends MappingEntry<?>> getEntries();

	default <C extends MappingEntry<C>> Collection<C> getEntriesOfType(MappingType<C> type) {
		return getEntries().stream().filter(mapping -> mapping.getType().equals(type)).map(type.targetEntry()::cast).toList();
	}

	default <C extends MappingEntry<C>> Stream<C> streamEntriesOfType(MappingType<C> type) {
		return getEntries().stream().filter(mapping -> mapping.getType().equals(type)).map(type.targetEntry()::cast);
	}

	Collection<? extends ClassEntry> getClassEntries();

	Optional<? extends ClassEntry> getClassEntry(String fromName);

	boolean hasClassEntry(String fromName);

	MutableMappingTree makeMutable();

	MappingTree reverse();

	MappingTree merge(MappingTree with);

	MappingTree merge(MappingTree with, MappingTree dest);

	MappingTree copy();
}
