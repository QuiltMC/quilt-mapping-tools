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

package org.quiltmc.mapping.impl.entry.info;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.MutableArgEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;
import org.quiltmc.mapping.impl.entry.MutableAbstractParentMappingEntry;

public class MutableArgEntryImpl extends MutableAbstractParentMappingEntry<ArgEntry> implements MutableArgEntry {
	protected int index;
	protected List<String> names;

	public MutableArgEntryImpl(int index, List<String> names, Collection<? extends MutableMappingEntry<?>> children) {
		super(new ArrayList<>(children));
		this.index = index;
		this.names = names;
	}

	@Override
	public ArgEntry merge(MappingEntry<?> other) {
		ArgEntry arg = (ArgEntry) other;
		return new MutableArgEntryImpl(this.index, AbstractNamedParentMappingEntry.joinNames(this.names, arg.names()), AbstractNamedParentMappingEntry.mergeChildren(this.children, arg.children()));
	}

	@Override
	public ArgEntry remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<ArgEntry> makeMutable() {
		return this;
	}

	public int index() {
		return index;
	}

	public List<String> names() {
		return names;
	}

	public boolean hasName(int namespace) {
		return namespace < this.names.size() && 0 <= namespace && this.names.get(namespace) != null;
	}

	@Override
	public Optional<String> name(int namespace) {
		return this.hasName(namespace) ? Optional.of(this.names.get(namespace)) : Optional.empty();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (MutableArgEntryImpl) obj;
		return this.index == that.index &&
			   Objects.equals(this.names, that.names) &&
			   Objects.equals(this.children, that.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), index, names);
	}

	@Override
	public String toString() {
		return "MutableArgEntry[" +
			   "index=" + index + ", " +
			   "names=" + names + ", " +
			   "children=" + children + ']';
	}

	@Override
	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public void setName(int namespace, @Nullable String name) {
		this.names.set(namespace, name);
	}

	@Override
	public ArgEntry makeFinal() {
		return new ArgEntryImpl(this.index, this.names, List.copyOf(this.children));
	}
}
