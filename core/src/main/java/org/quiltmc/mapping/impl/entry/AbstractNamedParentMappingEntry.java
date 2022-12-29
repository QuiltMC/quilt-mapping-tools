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

package org.quiltmc.mapping.impl.entry;

import java.util.Collection;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;

public abstract class AbstractNamedParentMappingEntry<T extends NamedMappingEntry<T> & ParentMappingEntry<T>> extends AbstractParentMappingEntry<T> implements NamedMappingEntry<T> {
	protected final String fromName;
	protected final @Nullable String toName;

	protected AbstractNamedParentMappingEntry(String fromName, @Nullable String toName, Collection<MappingEntry<?>> children) {
		super(children);
		this.fromName = fromName;
		this.toName = toName;
	}

	@Override
	public @Nullable String getToName() {
		return this.toName;
	}

	@Override
	public String getFromName() {
		return this.fromName;
	}

	@Override
	public boolean hasToName() {
		return this.toName != null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean shouldMerge(MappingEntry<?> other) {
		return super.shouldMerge(other) && this.fromName.equals(((NamedMappingEntry<T>) other).getFromName());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractNamedParentMappingEntry<?> that = (AbstractNamedParentMappingEntry<?>) o;
		return Objects.equals(fromName, that.fromName) && Objects.equals(toName, that.toName) && this.children.containsAll(that.children) && that.children.containsAll(this.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), fromName, toName);
	}
}
