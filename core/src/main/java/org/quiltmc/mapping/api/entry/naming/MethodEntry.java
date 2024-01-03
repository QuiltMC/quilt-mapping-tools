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

package org.quiltmc.mapping.api.entry.naming;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.info.ArgEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.naming.MethodEntryImpl;

/**
 * Represents a mapping entry for a Method.
 */
public interface MethodEntry extends NamedMappingEntry<MethodEntry>, DescriptorMappingEntry<MethodEntry>, ParentMappingEntry<MethodEntry> {
	/**
	 * The Mapping Type for Methods.
	 */
	MappingType<MethodEntry> METHOD_MAPPING_TYPE = MappingTypes.register(
		new MappingType<>("method",
			MethodEntry.class,
			type -> type.equals(ClassEntry.CLASS_MAPPING_TYPE),
			Parsers.createParent(
				Parsers.STRING.field("from", MethodEntry::fromName),
				Parsers.STRING.field("descriptor", MethodEntry::descriptor),
				Parsers.STRING.greedyList().field("to", MethodEntry::toNames),
				() -> MethodEntry.METHOD_MAPPING_TYPE,
				MethodEntryImpl::new
			)));

	@Override
	default MappingType<MethodEntry> getType() {
		return METHOD_MAPPING_TYPE;
	}

	/**
	 * @return the arguments for this method
	 */
	Collection<? extends ArgEntry> args();

	/**
	 * @return a map of the index to argument
	 */
	Map<Integer, ? extends ArgEntry> argsByIndex();

	/**
	 * @param index the argument index
	 * @return the argument wrapped in an optional if it exists, empty otherwirse
	 */
	Optional<? extends ArgEntry> arg(int index);

	/**
	 * @param index the argument index
	 * @return true if the argument exists
	 */
	boolean hasArg(int index);
}
