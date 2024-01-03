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

package org.quiltmc.mapping.impl.entry;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableNamedMappingEntry;

public abstract class MutableAbstractNamedParentMappingEntry<T extends NamedMappingEntry<T> & ParentMappingEntry<T>> extends MutableAbstractParentMappingEntry<T> implements MutableNamedMappingEntry<T> {
	protected final String fromName;
	protected List<String> toNames;

	protected MutableAbstractNamedParentMappingEntry(String fromName, List<String> toNames, Collection<MutableMappingEntry<?>> children) {
		super(children);
		this.fromName = fromName;
		this.toNames = toNames;
	}

	@Override
	public void setToName(int toNamespace, @Nullable String name) {
		this.toNames.set(toNamespace, name);
	}

	@Override
	public String fromName() {
		return this.fromName;
	}

	@Override
	public boolean hasToName(int toNamespace) {
		return toNamespace < this.toNames.size() && 0 <= toNamespace && this.toNames.get(toNamespace) != null;
	}

	@Override
	public Optional<String> toName(int toNamespace) {
		return this.hasToName(toNamespace) ? Optional.of(this.toNames.get(toNamespace)) : Optional.empty();
	}

	@Override
	public List<String> toNames() {
		return List.copyOf(this.toNames);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean shouldMerge(MappingEntry<?> other) {
		return super.shouldMerge(other) && this.fromName.equals(((T) other).fromName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MutableAbstractNamedParentMappingEntry<?> that = (MutableAbstractNamedParentMappingEntry<?>) o;
		return Objects.equals(fromName, that.fromName) && Objects.equals(toNames, that.toNames) && this.children.containsAll(that.children) && that.children.containsAll(this.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), fromName, toNames);
	}
}
