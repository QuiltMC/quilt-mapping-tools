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

package org.quiltmc.mapping.file;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;

public record QuiltMappingFile(MappingHeader header, List<MappingEntry<?>> entries) {
	public QuiltMappingFile merge(QuiltMappingFile other) {
		Set<String> extensions = new HashSet<>(this.header.extensions());
		extensions.addAll(other.header.extensions());
		MappingHeader header = new MappingHeader(this.header.fromNamespace(), this.header.toNamespace(), extensions);

		return new QuiltMappingFile(header, AbstractNamedParentMappingEntry.mergeChildren(this.entries, other.entries));
	}
}
