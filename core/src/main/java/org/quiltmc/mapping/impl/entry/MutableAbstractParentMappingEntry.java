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

import java.util.Collection;
import java.util.Objects;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableParentMappingEntry;

public abstract class MutableAbstractParentMappingEntry<T extends ParentMappingEntry<T>> implements MutableParentMappingEntry<T> {
	protected final Collection<MutableMappingEntry<?>> children;

	protected MutableAbstractParentMappingEntry(Collection<MutableMappingEntry<?>> children) {
		this.children = children;
	}

	@Override
	public Collection<MutableMappingEntry<?>> children() {
		return children;
	}

	@Override
	public boolean shouldMerge(MappingEntry<?> other) {
		return MutableParentMappingEntry.super.shouldMerge(other);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MutableAbstractParentMappingEntry<?> that = (MutableAbstractParentMappingEntry<?>) o;
		return this.children.containsAll(that.children) && that.children.containsAll(this.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(children);
	}

	@Override
	public void addChild(MutableMappingEntry<?> entry) {
		if (!entry.getType().targets().test(this.getType())) {
			System.err.println("Adding entry " + entry + ", which does not target this type: " + this.getType().key());
		}
		this.children.add(entry);
	}
}
