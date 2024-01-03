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

package org.quiltmc.mapping.impl.tree;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.tree.MappingTree;
import org.quiltmc.mapping.api.tree.MutableMappingTree;

public class MappingTreeImpl implements MappingTree {
	private final Collection<MappingEntry<?>> entries;
	private final String fromNamespace;
	private final List<String> toNamespaces;

	private final Collection<ClassEntry> classes;
	private final Map<String, ClassEntry> classesByName;

	public MappingTreeImpl(Collection<MappingEntry<?>> entries, String fromNamespace, List<String> toNamespaces) {
		this.entries = entries;
		this.fromNamespace = fromNamespace;
		this.toNamespaces = toNamespaces;

		classes = this.getEntriesOfType(ClassEntry.CLASS_MAPPING_TYPE);
		classesByName = this.streamEntriesOfType(ClassEntry.CLASS_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ClassEntry::fromName, Function.identity()));
	}

	@Override
	public String fromNamespace() {
		return this.fromNamespace;
	}

	@Override
	public List<String> toNamespaces() {
		return this.toNamespaces;
	}

	@Override
	public Collection<? extends MappingEntry<?>> getEntries() {
		return this.entries;
	}

	@Override
	public Collection<? extends ClassEntry> classes() {
		return this.classes;
	}

	@Override
	public Optional<? extends ClassEntry> classEntry(String fromName) {
		return this.hasClassEntry(fromName) ? Optional.of(this.classesByName.get(fromName)) : Optional.empty();
	}

	@Override
	public boolean hasClassEntry(String fromName) {
		return this.classesByName.containsKey(fromName);
	}

	@Override
	public MutableMappingTree makeMutable() {
		return null;
	}

	@Override
	public MappingTree merge(MappingTree with) {
		return null;
	}

	@Override
	public MappingTree merge(MappingTree with, MutableMappingTree dest) {
		return null;
	}

	@Override
	public MappingTree copy() {
		return null;
	}
}
