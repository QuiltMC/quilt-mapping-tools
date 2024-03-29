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

package org.quiltmc.mapping.impl.entry.naming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MutableFieldEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractParentMappingEntry;
import org.quiltmc.mapping.impl.entry.MutableAbstractNamedParentDescriptorMappingEntry;

public class MutableFieldEntryImpl extends MutableAbstractNamedParentDescriptorMappingEntry<FieldEntry> implements MutableFieldEntry {
	protected MutableFieldEntryImpl(String fromName, List<String> toNames, String descriptor, Collection<? extends MutableMappingEntry<?>> children) {
		super(fromName, toNames, descriptor, new ArrayList<>(children));
	}

	@Override
	public FieldEntry remap() {
		return null;
	}

	@Override
	public FieldEntry merge(MappingEntry<?> other) {
		FieldEntry field = ((FieldEntry) other);
		Collection<MutableMappingEntry<?>> children = AbstractParentMappingEntry.mergeChildren(this.children, field.children());
		return new MutableFieldEntryImpl(this.fromName, AbstractNamedParentMappingEntry.joinNames(this.toNames, field.toNames()), this.descriptor, children);
	}

	@Override
	public MutableMappingEntry<FieldEntry> makeMutable() {
		return this;
	}

	@Override
	public FieldEntry makeFinal() {
		return new FieldEntryImpl(this.fromName, this.descriptor, this.toNames, List.copyOf(children));
	}

	@Override
	public String toString() {
		return "FieldEntry[" +
			   "descriptor='" + descriptor + '\'' +
			   ", fromName='" + fromName + '\'' +
			   ", toNames='" + toNames + '\'' +
			   ", children=" + children +
			   ']';
	}
}
