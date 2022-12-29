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

import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.ParentMappingEntry;

public interface FieldEntry extends DescriptorMappingEntry<FieldEntry>, ParentMappingEntry<FieldEntry> {
	MappingType<FieldEntry> FIELD_MAPPING_TYPE = new MappingType<>("fields", FieldEntry.class, mappingType -> mappingType.equals(ClassEntry.CLASS_MAPPING_TYPE));

	@Override
	default MappingType<FieldEntry> getType() {
		return FIELD_MAPPING_TYPE;
	}
}
