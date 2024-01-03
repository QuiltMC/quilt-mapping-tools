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

package org.quiltmc.mapping.api.entry.info;

import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.entry.info.ReturnEntryImpl;

/**
 * A Mapping Entry for the Return type of a Method.
 */
public interface ReturnEntry extends ParentMappingEntry<ReturnEntry> {
	/**
	 * The Mapping Type for Returns.
	 */
	MappingType<ReturnEntry> RETURN_MAPPING_TYPE = MappingTypes.register(new MappingType<>("return",
		ReturnEntry.class,
		type -> type.equals(MethodEntry.METHOD_MAPPING_TYPE),
		Parsers
			.createParent(
				() -> ReturnEntry.RETURN_MAPPING_TYPE,
				ReturnEntryImpl::new
			)));

	@Override
	default MappingType<ReturnEntry> getType() {
		return RETURN_MAPPING_TYPE;
	}
}
