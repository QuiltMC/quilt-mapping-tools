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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.parse.Parser;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

/**
 * Represents a mapping file.
 *
 * @param header  the header for the file
 * @param entries the entries present in the file
 */
public record QuiltMappingFile(MappingHeader header, Collection<MappingEntry<?>> entries) {
	/**
	 * The parser for the file.
	 */
	public static final Parser<QuiltMappingFile, TabSeparatedContent> PARSER = new Parser<>() {
		@Override
		public QuiltMappingFile deserialize(TabSeparatedContent input) {
			List<TabSeparatedContent> subcontents = new ArrayList<>(input.getSubcontent());
			TabSeparatedContent headerContent = subcontents.remove(0);
			MappingHeader header = MappingHeader.PARSER.deserialize(headerContent);

			Collection<MappingEntry<?>> children = Parsers.parseChildren(MappingTypes.types().values(), subcontents);

			return new QuiltMappingFile(header, children);
		}

		@SuppressWarnings("unchecked")
		@Override
		public TabSeparatedContent serialize(QuiltMappingFile input) {
			TabSeparatedContent content = new TabSeparatedContent(List.of());
			content.push(MappingHeader.PARSER.serialize(input.header()));

			input.entries.stream().map(entry -> {
				Parser<MappingEntry<?>, TabSeparatedContent> parser = (Parser<MappingEntry<?>, TabSeparatedContent>) entry.getType().parser();
				return parser.serialize(entry);
			}).forEach(content::push);

			return content;
		}
	};

	// TODO: Figure this out
	public QuiltMappingFile merge(QuiltMappingFile other) {
		throw new IllegalStateException("Not implemented yet");

//		Set<String> extensions = new HashSet<>(this.header.extensions());
//		extensions.addAll(other.header.extensions());
//		MappingHeader header = new MappingHeader(this.header.fromNamespace(), this.header.toNamespaces(), extensions);
//
//		return new QuiltMappingFile(header, AbstractNamedParentMappingEntry.mergeChildren(this.entries, other.entries));
	}
}
