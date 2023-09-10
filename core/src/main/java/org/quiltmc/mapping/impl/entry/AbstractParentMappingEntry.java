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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.NotNull;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;

public abstract class AbstractParentMappingEntry<T extends ParentMappingEntry<T>> implements ParentMappingEntry<T> {
	protected final Collection<MappingEntry<?>> children;

	protected AbstractParentMappingEntry(Collection<MappingEntry<?>> children) {
		this.children = children;
	}

	@NotNull
	public static <T extends MappingEntry<?>> Collection<T> mergeChildren(Collection<? extends MappingEntry<?>> children, Collection<? extends MappingEntry<?>> otherChildren) {
		List<T> newChildren = new ArrayList<>();
		mergeChildrenIntoList(children, newChildren);
		mergeChildrenIntoList(otherChildren, newChildren);
		return newChildren;
	}

	@SuppressWarnings("unchecked")
	private static <T extends MappingEntry<?>> void mergeChildrenIntoList(Collection<? extends MappingEntry<?>> otherChildren, List<T> newChildren) {
		for (MappingEntry<?> newChild : otherChildren) {
			for (int i = 0; i < newChildren.size(); i++) {
				MappingEntry<?> child = newChildren.get(i);
				if (child.shouldMerge(newChild)) {
					newChild = child.merge(newChild);
					newChildren.remove(i);
					break;
				}
			}
			newChildren.add((T) newChild);
		}
	}

	@Override
	public Collection<MappingEntry<?>> children() {
		return children;
	}

	@Override
	public boolean shouldMerge(MappingEntry<?> other) {
		return ParentMappingEntry.super.shouldMerge(other);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractParentMappingEntry<?> that = (AbstractParentMappingEntry<?>) o;
		return this.children.containsAll(that.children) && that.children.containsAll(this.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(children);
	}
}
