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

package org.quiltmc.mapping.entry.annotation;

import java.util.ArrayList;
import java.util.List;

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public record AnnotationModifications(List<AnnotationRemovalMapping> removals, List<AnnotationAddition> additions) implements MappingEntry<AnnotationModifications> {
	public static final MappingType<AnnotationModifications> ANNOTATION_MODIFICATIONS_MAPPING_TYPE = new MappingType<>("annotation_modifications", MappingType.TokenType.OBJECT, AnnotationModifications.class, AnnotationModifications::parse, AnnotationModifications::write);

	public static AnnotationModifications parse(QuiltMappingParser parser) {
		List<MappingEntry<?>> genericAdditions = new ArrayList<>();
		List<MappingEntry<?>> genericRemovals = new ArrayList<>();

		while (parser.hasValues()) {
			String name = parser.valueName();
			switch (name) {
				case "additions" -> parser.parseChildToken(genericAdditions, name);
				case "removals" -> parser.parseChildToken(genericRemovals, name);
				default -> parser.skip();
			}
		}

		List<AnnotationRemovalMapping> removals = genericRemovals.stream().filter(AnnotationRemovalMapping.class::isInstance).map(AnnotationRemovalMapping.class::cast).toList();
		List<AnnotationAddition> additions = genericAdditions.stream().filter(AnnotationAddition.class::isInstance).map(AnnotationAddition.class::cast).toList();
		return new AnnotationModifications(removals, additions);
	}

	public void write(QuiltMappingsWriter writer) {
		List<MappingEntry<?>> children = new ArrayList<>();
		children.addAll(this.additions);
		children.addAll(this.removals);
		writer.writeChildren(children);
	}

	@Override
	public AnnotationModifications remap() {
		return null;
	}

	@Override
	public MappingType<AnnotationModifications> getType() {
		return ANNOTATION_MODIFICATIONS_MAPPING_TYPE;
	}

	@Override
	public List<MappingType<?>> getTargetTypes() {
		return null;
	}
}
