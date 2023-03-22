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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.MutableArgEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.entry.naming.MutableFieldEntry;
import org.quiltmc.mapping.api.entry.naming.MutableMethodEntry;
import org.quiltmc.mapping.impl.entry.AbstractParentMappingEntry;
import org.quiltmc.mapping.impl.entry.MutableAbstractNamedParentDescriptorMappingEntry;
import org.quiltmc.mapping.impl.entry.info.MutableArgEntryImpl;

public class MutableMethodEntryImpl extends MutableAbstractNamedParentDescriptorMappingEntry<MethodEntry> implements MutableMethodEntry {
	private final Collection<MutableArgEntry> args;
	private final Map<Integer, MutableArgEntry> indexToArg;

	protected MutableMethodEntryImpl(String fromName, @Nullable String toName, String descriptor, Collection<? extends MutableMappingEntry<?>> children) {
		super(fromName, toName, descriptor, new ArrayList<>(children));

		args = new ArrayList<>(this.streamChildrenOfType(ArgEntry.ARG_MAPPING_TYPE).map(arg -> (MutableArgEntry) arg.makeMutable()).toList());
		indexToArg = new HashMap<>(streamChildrenOfType(ArgEntry.ARG_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ArgEntry::index, arg -> (MutableArgEntry) arg.makeMutable())));
	}

	@Override
	public MethodEntry remap() {
		return null;
	}

	@Override
	public MethodEntry merge(MappingEntry<?> other) {
		MethodEntry method = ((MethodEntry) other);
		Collection<MutableMappingEntry<?>> children = AbstractParentMappingEntry.mergeChildren(this.children, method.children());
		return new MutableMethodEntryImpl(this.fromName, this.toName != null ? this.toName : method.getToName(), this.descriptor, children);
	}

	@Override
	public MutableMappingEntry<MethodEntry> makeMutable() {
		return this;
	}

	@Override
	public MethodEntry makeFinal() {
		return new MethodEntryImpl(this.fromName, this.toName, this.descriptor, List.copyOf(children));
	}

	@Override
	public Collection<? extends MutableArgEntry> getArgs() {
		return args;
	}

	@Override
	public Map<Integer, ? extends MutableArgEntry> getArgsByIndex() {
		return indexToArg;
	}

	@Override
	public Optional<? extends MutableArgEntry> getArgMapping(int index) {
		return hasArgMapping(index) ? Optional.of(indexToArg.get(index)) : Optional.empty();
	}

	@Override
	public void addChild(MutableMappingEntry<?> entry) {
		super.addChild(entry);

		if (entry instanceof MutableArgEntry arg) {
			this.args.add(arg);
			this.indexToArg.put(arg.index(), arg);
		}
	}

	@Override
	public MutableArgEntry createArgEntry(int index, @Nullable String name) {
		MutableArgEntryImpl arg = new MutableArgEntryImpl(index, name, List.of());
		this.addChild(arg);
		return arg;
	}

	@Override
	public boolean hasArgMapping(int index) {
		return indexToArg.containsKey(index);
	}

	@Override
	public String toString() {
		return "MethodEntry[" +
			   "descriptor='" + descriptor + '\'' +
			   ", fromName='" + fromName + '\'' +
			   ", toName='" + toName + '\'' +
			   ", children=" + children +
			   ']';
	}
}
