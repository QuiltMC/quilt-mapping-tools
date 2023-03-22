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

package org.quiltmc.mapping.impl.entry.transitive;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.transitive.MutableTransitiveEntry;
import org.quiltmc.mapping.api.entry.transitive.TransitiveEntry;

public final class MutableTransitiveEntryImpl implements MutableTransitiveEntry {
	private String target;
	private final Set<MappingType<?>> transitiveTypes;

	public MutableTransitiveEntryImpl(String target, Set<MappingType<?>> transitiveTypes) {
		this.target = target;
		this.transitiveTypes = new HashSet<>(transitiveTypes);
	}

	@Override
	public TransitiveEntry remap() {
		return null;
	}

	@Override
	public MutableTransitiveEntryImpl merge(MappingEntry<?> other) {
		return null;
	}

	@Override
	public MutableMappingEntry<TransitiveEntry> makeMutable() {
		return this;
	}

	public String target() {
		return target;
	}

	public Set<MappingType<?>> transitiveTypes() {
		return transitiveTypes;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (MutableTransitiveEntryImpl) obj;
		return Objects.equals(this.target, that.target) &&
			   Objects.equals(this.transitiveTypes, that.transitiveTypes);
	}

	@Override
	public int hashCode() {
		return Objects.hash(target, transitiveTypes);
	}

	@Override
	public String toString() {
		return "MutableTransitiveEntryImpl[" +
			   "target=" + target + ", " +
			   "transitiveTypes=" + transitiveTypes + ']';
	}

	@Override
	public TransitiveEntry makeFinal() {
		return new TransitiveEntryImpl(this.target, Set.copyOf(this.transitiveTypes));
	}

	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public void addTransitiveType(MappingType<?> type) {
		this.transitiveTypes.add(type);
	}
}
