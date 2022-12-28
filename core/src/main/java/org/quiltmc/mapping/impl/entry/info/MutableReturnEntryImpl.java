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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.MutableReturnEntry;
import org.quiltmc.mapping.api.entry.info.ReturnEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;
import org.quiltmc.mapping.impl.entry.MutableAbstractParentMappingEntry;

public final class MutableReturnEntryImpl extends MutableAbstractParentMappingEntry<ReturnEntry> implements MutableReturnEntry {
	public MutableReturnEntryImpl(Collection<MappingEntry<?>> children) {
		super(new ArrayList<>(children));
	}

	@Override
	public ReturnEntry merge(MappingEntry<?> other) {
		ReturnEntry ret = (ReturnEntry) other;
		return new MutableReturnEntryImpl(AbstractNamedParentMappingEntry.mergeChildren(this.children, ret.children()));
	}

	@Override
	public MutableMappingEntry<ReturnEntry> makeMutable() {
		return this;
	}

	@Override
	public ReturnEntry remap() {
		return this;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (MutableReturnEntryImpl) obj;
		return Objects.equals(this.children, that.children);
	}

	@Override
	public int hashCode() {
		return Objects.hash(children);
	}

	@Override
	public String toString() {
		return "MutableReturnEntryImpl[" +
			   "children=" + children + ']';
	}

	@Override
	public ReturnEntry makeFinal() {
		return new ReturnEntryImpl(List.copyOf(this.children));
	}
}
