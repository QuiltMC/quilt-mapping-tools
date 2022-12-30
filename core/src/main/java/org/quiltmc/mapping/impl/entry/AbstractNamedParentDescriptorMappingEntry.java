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
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;

public abstract class AbstractNamedParentDescriptorMappingEntry<T extends DescriptorMappingEntry<T> & NamedMappingEntry<T> & ParentMappingEntry<T>> extends AbstractNamedParentMappingEntry<T> implements DescriptorMappingEntry<T> {
	protected final String descriptor;

	protected AbstractNamedParentDescriptorMappingEntry(String fromName, @Nullable String toName, String descriptor, Collection<MappingEntry<?>> children) {
		super(fromName, toName, children);
		this.descriptor = descriptor;
	}

	@Override
	public String descriptor() {
		return descriptor;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		AbstractNamedParentDescriptorMappingEntry<?> that = (AbstractNamedParentDescriptorMappingEntry<?>) o;
		return Objects.equals(descriptor, that.descriptor);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), descriptor);
	}
}
