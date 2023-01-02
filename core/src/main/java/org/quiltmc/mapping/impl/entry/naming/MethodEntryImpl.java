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

package org.quiltmc.mapping.impl.entry.naming;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentDescriptorMappingEntry;

public class MethodEntryImpl extends AbstractNamedParentDescriptorMappingEntry<MethodEntry> implements MethodEntry {
	private final Collection<ArgEntry> args;
	private final Map<Integer, ArgEntry> indexToArg;

	protected MethodEntryImpl(String fromName, @Nullable String toName, String descriptor, Collection<MappingEntry<?>> children) {
		super(fromName, toName, descriptor, children);
		args = this.getChildrenOfType(ArgEntry.ARG_MAPPING_TYPE);
		indexToArg = this.streamChildrenOfType(ArgEntry.ARG_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ArgEntry::index, Function.identity()));
	}

	@Override
	public MethodEntry remap() {
		return null;
	}

	@Override
	public MethodEntry merge(MappingEntry<?> other) {
		MethodEntry method = ((MethodEntry) other);
		Collection<MappingEntry<?>> children = mergeChildren(this.children, method.children());
		return new MethodEntryImpl(this.fromName, this.toName != null ? this.toName : method.getToName(), this.descriptor, children);
	}

	@Override
	public MutableMappingEntry<MethodEntry> makeMutable() {
		return new MutableMethodEntryImpl(this.fromName, this.toName, this.descriptor, this.children.stream().map(MappingEntry::makeMutable).toList());
	}

	@Override
	public Collection<? extends ArgEntry> getArgs() {
		return args;
	}

	@Override
	public Map<Integer, ? extends ArgEntry> getArgsByIndex() {
		return indexToArg;
	}

	@Override
	public Optional<? extends ArgEntry> getArgMapping(int index) {
		return hasArgMapping(index) ? Optional.of(indexToArg.get(index)) : Optional.empty();
	}

	@Override
	public boolean hasArgMapping(int index) {
		return indexToArg.containsKey(index);
	}
}
