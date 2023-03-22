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

package org.quiltmc.mapping.api.entry.naming;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.entry.info.MutableArgEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableDescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableNamedMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableParentMappingEntry;

public interface MutableMethodEntry extends MethodEntry, MutableNamedMappingEntry<MethodEntry>, MutableDescriptorMappingEntry<MethodEntry>, MutableParentMappingEntry<MethodEntry> {
	@Override
	Collection<? extends MutableArgEntry> getArgs();

	@Override
	Map<Integer, ? extends MutableArgEntry> getArgsByIndex();

	@Override
	Optional<? extends MutableArgEntry> getArgMapping(int index);

	MutableArgEntry createArgEntry(int index, @Nullable String name);

	default MutableArgEntry getOrCreateArgEntry(int index, @Nullable String name) {
		return this.hasArgMapping(index) ? this.getArgMapping(index).get() : this.createArgEntry(index, name);
	}
}
