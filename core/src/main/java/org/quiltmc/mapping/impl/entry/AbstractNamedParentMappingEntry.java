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

package org.quiltmc.mapping.impl.entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;

public abstract class AbstractNamedParentMappingEntry<T extends NamedMappingEntry<T> & ParentMappingEntry<T>> extends AbstractParentMappingEntry<T> implements NamedMappingEntry<T> {
	protected final String fromName;
	protected final List<String> toNames;

	protected AbstractNamedParentMappingEntry(String fromName, List<String> toNames, Collection<MappingEntry<?>> children) {
		super(children);
		this.fromName = fromName;
		this.toNames = toNames;
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
		return super.shouldMerge(other) && this.fromName.equals(((NamedMappingEntry<T>) other).fromName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractNamedParentMappingEntry<?> that = (AbstractNamedParentMappingEntry<?>) o;
		return Objects.equals(fromName, that.fromName) && Objects.equals(toNames, that.toNames) && this.children.containsAll(that.children) && that.children.containsAll(this.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), fromName, toNames);
	}

	public static List<String> joinNames(List<String> myNames, List<String> otherNames) {
		List<String> names = new ArrayList<>();

		Iterator<String> myNamesIter = myNames.iterator();
		Iterator<String> otherNamesIter = otherNames.iterator();

		while (myNamesIter.hasNext() || otherNamesIter.hasNext()) {
			String myName = myNamesIter.hasNext() ? myNamesIter.next() : null;
			String otherName = otherNamesIter.hasNext() ? otherNamesIter.next() : null;

			names.add(myName != null ? myName : otherName);
		}

		return List.copyOf(names);
	}
}
