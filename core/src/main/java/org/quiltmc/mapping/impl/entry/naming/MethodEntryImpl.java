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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentDescriptorMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;

public class MethodEntryImpl extends AbstractNamedParentDescriptorMappingEntry<MethodEntry> implements MethodEntry {
	private final Collection<ArgEntry> args;
	private final Map<Integer, ArgEntry> indexToArg;

	public MethodEntryImpl(String fromName, String descriptor, List<String> toNames, Collection<MappingEntry<?>> children) {
		super(fromName, toNames, descriptor, children);
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
		return new MethodEntryImpl(this.fromName, this.descriptor, AbstractNamedParentMappingEntry.joinNames(this.toNames, method.toNames()), children);
	}

	@Override
	public MutableMappingEntry<MethodEntry> makeMutable() {
		return new MutableMethodEntryImpl(this.fromName, this.toNames, this.descriptor, this.children.stream().map(MappingEntry::makeMutable).toList());
	}

	@Override
	public Collection<? extends ArgEntry> args() {
		return args;
	}

	@Override
	public Map<Integer, ? extends ArgEntry> argsByIndex() {
		return indexToArg;
	}

	@Override
	public Optional<? extends ArgEntry> arg(int index) {
		return hasArg(index) ? Optional.of(indexToArg.get(index)) : Optional.empty();
	}

	@Override
	public boolean hasArg(int index) {
		return indexToArg.containsKey(index);
	}

	@Override
	public String toString() {
		return "MethodEntry[" +
			   "descriptor='" + descriptor + '\'' +
			   ", fromName='" + fromName + '\'' +
			   ", toNames='" + toNames + '\'' +
			   ", children=" + children +
			   ']';
	}
}
