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

import java.util.Collection;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.ReturnEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;

public record ReturnEntryImpl(Collection<MappingEntry<?>> children) implements ReturnEntry {
	@Override
	public ReturnEntry merge(MappingEntry<?> other) {
		ReturnEntry ret = (ReturnEntry) other;
		return new ReturnEntryImpl(AbstractNamedParentMappingEntry.mergeChildren(this.children, ret.children()));
	}

	@Override
	public MutableMappingEntry<ReturnEntry> makeMutable() {
		return new MutableReturnEntryImpl(this.children.stream().map(MappingEntry::makeMutable).toList());
	}

	@Override
	public ReturnEntry remap() {
		return this;
	}

	@Override
	public String toString() {
		return "ReturnEntry[" +
			   "children=" + children +
			   ']';
	}
}
