/*
 * Copyright 2022 QuiltMC
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

package org.quiltmc.mapping.impl.entry.info;

import java.util.Collection;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractParentMappingEntry;

public final class ArgEntryImpl extends AbstractParentMappingEntry<ArgEntry> implements ArgEntry {
	private final int index;
	private final @Nullable String name;

	public ArgEntryImpl(int index, @Nullable String name, Collection<MappingEntry<?>> children) {
		super(children);
		this.index = index;
		this.name = name;
	}

	@Override
	public ArgEntry merge(MappingEntry<?> other) {
		ArgEntry arg = (ArgEntry) other;
		return new ArgEntryImpl(this.index, this.name != null ? this.name : arg.name(), AbstractNamedParentMappingEntry.mergeChildren(this.children, arg.children()));
	}

	@Override
	public MutableMappingEntry<ArgEntry> makeMutable() {
		return new MutableArgEntryImpl(this.index, this.name, this.children.stream().map(MappingEntry::makeMutable).toList());
	}

	@Override
	public ArgEntryImpl remap() {
		return null;
	}

	public int index() {
		return index;
	}

	public @Nullable String name() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (ArgEntryImpl) obj;
		return this.index == that.index &&
			   Objects.equals(this.name, that.name) &&
			   Objects.equals(this.children, that.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), index, name);
	}

	@Override
	public String toString() {
		return "ArgEntry[" +
			   "index=" + index + ", " +
			   "name=" + name + ", " +
			   "children=" + children + ']';
	}
}
