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

import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.impl.entry.naming.MethodEntryImpl;
import org.quiltmc.mapping.api.serialization.Builder;

public interface MethodEntry extends NamedMappingEntry<MethodEntry>, DescriptorMappingEntry<MethodEntry>, ParentMappingEntry<MethodEntry> {
	MappingType<MethodEntry> METHOD_MAPPING_TYPE = MappingTypes.register(
			new MappingType<>("methods",
					MethodEntry.class,
					type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE),
					Builder.EntryBuilder.<MethodEntry>entry()
							.string("from", NamedMappingEntry::getFromName)
							.nullableString("to", NamedMappingEntry::getToName)
							.string("descriptor", DescriptorMappingEntry::descriptor)
							.withChildren(() -> MethodEntry.METHOD_MAPPING_TYPE)
							.build(args -> new MethodEntryImpl(args.get("from"), args.getNullable("to"), args.get("descriptor"), args.get("children")))));

	@Override
	default MappingType<MethodEntry> getType() {
		return METHOD_MAPPING_TYPE;
	}

	Collection<? extends ArgEntry> getArgs();

	Map<Integer, ? extends ArgEntry> getArgsByIndex();

	Optional<? extends ArgEntry> getArgMapping(int index);

	boolean hasArgMapping(int index);
}
