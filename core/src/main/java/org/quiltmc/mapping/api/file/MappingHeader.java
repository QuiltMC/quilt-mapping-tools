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

package org.quiltmc.mapping.api.file;


import java.util.List;
import java.util.Set;

import org.quiltmc.mapping.api.parse.Parser;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

/**
 * The header for a mapping file. Holds information about the from namespace, what to namespaces exist, and what extensions are present
 *
 * @param fromNamespace the source namespace for the file
 * @param toNamespaces  the destination namespaces for the file
 * @param extensions    the extensions in the file
 */
public record MappingHeader(String fromNamespace, List<String> toNamespaces, Set<String> extensions) {
	public static final Parser<MappingHeader, TabSeparatedContent> PARSER = Parsers.create(
		Parsers.STRING.field("from", MappingHeader::fromNamespace),
		Parsers.STRING.greedyList().field("to", MappingHeader::toNamespaces),
		Parsers.STRING.set().field("extensions", MappingHeader::extensions),
		"QUILT_MAPPING",
		MappingHeader::new
	);
}
