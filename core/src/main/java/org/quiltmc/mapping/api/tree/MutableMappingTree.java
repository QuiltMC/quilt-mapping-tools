/*
 * Copyright 2023 QuiltMC
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

package org.quiltmc.mapping.api.tree;

import java.util.Collection;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;

public interface MutableMappingTree extends MappingTree {
	@Override
	Collection<? extends MutableMappingEntry<?>> getEntries();

	@Override
	Collection<? extends MutableClassEntry> getClassEntries();

	@Override
	Optional<? extends MutableClassEntry> getClassEntry(String fromName);

	MutableClassEntry createClassEntry(String fromName, @Nullable String toName);

	default MutableClassEntry getOrCreateClassEntry(String fromName, @Nullable String toName) {
		return this.hasClassEntry(fromName) ? this.getClassEntry(fromName).get() : this.createClassEntry(fromName, toName);
	}

	void addEntry(MutableMappingEntry<?> entry);
}
