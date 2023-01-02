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

package org.quiltmc.mapping.api.entry.naming;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.mutable.MutableNamedMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableParentMappingEntry;

public interface MutableClassEntry extends ClassEntry, MutableParentMappingEntry<ClassEntry>, MutableNamedMappingEntry<ClassEntry> {
	@Override
	Collection<? extends MutableFieldEntry> getFields();
	@Override
	Map<String, ? extends MutableFieldEntry> getFieldsByName();
	@Override
	Optional<? extends MutableFieldEntry> getFieldMapping(String fromName);
	MutableFieldEntry createFieldEntry(String fromName, @Nullable String toName, String descriptor);
	default MutableFieldEntry getOrCreateFieldEntry(String fromName, @Nullable String toName, String descriptor) {
		return this.hasFieldMapping(fromName) ? this.getFieldMapping(fromName).get() : this.createFieldEntry(fromName, toName, descriptor);
	}

	@Override
	Collection<? extends MutableMethodEntry> getMethods();
	@Override
	Map<String, ? extends MutableMethodEntry> getMethodsByName();
	@Override
	Optional<? extends MutableMethodEntry> getMethodMapping(String fromName);
	MutableMethodEntry createMethodEntry(String fromName, @Nullable String toName, String descriptor);
	default MutableMethodEntry getOrCreateMethodEntry(String fromName, @Nullable String toName, String descriptor) {
		return this.hasMethodMapping(fromName) ? this.getMethodMapping(fromName).get() : this.createMethodEntry(fromName, toName, descriptor);
	}

	@Override
	Collection<? extends MutableClassEntry> getClasses();
	@Override
	Map<String, ? extends MutableClassEntry> getClassesByName();
	@Override
	Optional<? extends MutableClassEntry> getClassMapping(String fromName);
	MutableClassEntry createClassEntry(String fromName, @Nullable String toName);
	default MutableClassEntry getOrCreateClassEntry(String fromName, @Nullable String toName) {
		return this.hasClassMapping(fromName) ? this.getClassMapping(fromName).get() : this.createClassEntry(fromName, toName);
	}
}
