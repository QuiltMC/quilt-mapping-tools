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
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;

public interface ClassEntry extends ParentMappingEntry<ClassEntry>, NamedMappingEntry<ClassEntry> {
	@Override
	default MappingType<ClassEntry> getType() {
		return CLASS_MAPPING_TYPE;
	}	MappingType<ClassEntry> CLASS_MAPPING_TYPE = new MappingType<>("classes", ClassEntry.class, mappingType -> mappingType.equals(ClassEntry.CLASS_MAPPING_TYPE));

	default Collection<? extends FieldEntry> getFields() {
		return this.getChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE);
	}

	default Map<String, ? extends FieldEntry> getFieldsByName() {
		return this.streamChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(FieldEntry::getFromName, Function.identity()));
	}

	Optional<? extends FieldEntry> getFieldMapping(String fromName);

	boolean hasFieldMapping(String fromName);

	default Collection<? extends MethodEntry> getMethods() {
		return this.getChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE);
	}

	default Map<String, ? extends MethodEntry> getMethodsByName() {
		return this.streamChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(MethodEntry::getFromName, Function.identity()));
	}

	Optional<? extends MethodEntry> getMethodMapping(String fromName);

	boolean hasMethodMapping(String fromName);

	default Collection<? extends ClassEntry> getClasses() {
		return this.getChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE);
	}

	default Map<String, ? extends ClassEntry> getClassesByName() {
		return this.streamChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ClassEntry::getFromName, Function.identity()));
	}

	Optional<? extends ClassEntry> getClassMapping(String fromName);

	boolean hasClassMapping(String fromName);
}
