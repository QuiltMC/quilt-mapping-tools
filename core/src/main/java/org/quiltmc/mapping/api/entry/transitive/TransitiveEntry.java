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

package org.quiltmc.mapping.api.entry.transitive;

import java.util.List;
import java.util.Set;

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;

public interface TransitiveEntry extends MappingEntry<TransitiveEntry> {
	MappingType<TransitiveEntry> TRANSITIVE_MAPPING_TYPE = new MappingType<>("transitive", TransitiveEntry.class, mappingType -> mappingType.equals(ClassEntry.CLASS_MAPPING_TYPE) || mappingType.equals(MethodEntry.METHOD_MAPPING_TYPE) || mappingType.equals(FieldEntry.FIELD_MAPPING_TYPE));

	String target();
	Set<MappingType<?>> transitiveTypes();

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		return MappingEntry.super.shouldMerge(other) && this.target().equals(((TransitiveEntry) other).target());
	}

	@Override
	default MappingType<TransitiveEntry> getType() {
		return TRANSITIVE_MAPPING_TYPE;
	}
}
