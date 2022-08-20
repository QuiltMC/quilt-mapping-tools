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

package org.quiltmc.mapping.entry;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;

public abstract class AbstractParentMappingEntry<T extends AbstractParentMappingEntry<T>> implements MappingEntry<T> {
	protected final String fromName;
	protected final @Nullable String toName;
	protected final List<MappingEntry<?>> children;
	protected final MappingType<T> type;

	protected AbstractParentMappingEntry(String fromName, @Nullable String toName, List<MappingEntry<?>> children, MappingType<T> type) {
		this.fromName = fromName;
		this.toName = toName;
		this.children = children;
		this.type = type;
	}

	@Override
	public MappingType<T> getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AbstractParentMappingEntry<?> that = (AbstractParentMappingEntry<?>) o;
		return Objects.equals(fromName, that.fromName) && Objects.equals(toName, that.toName) && this.children.containsAll(that.children) && that.children.containsAll(this.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(fromName, toName, children, type);
	}
}
