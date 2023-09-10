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

package org.quiltmc.annotation_replacement.api.entry.value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.objectweb.asm.Type;
import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.impl.entry.value.LiteralAnnotationValueImpl;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.parse.Parser;
import org.quiltmc.mapping.api.parse.Parsers;
import org.quiltmc.mapping.impl.serialization.TabSeparatedContent;

public interface LiteralAnnotationValue extends AnnotationValue<Object, LiteralAnnotationValue> {
	Parser<LiteralAnnotationValue, TabSeparatedContent> PARSER = new Parser<>() {
		@Override
		public LiteralAnnotationValue parse(TabSeparatedContent input) {
			String name = input.getContent().get(1);
			String descriptor = input.getContent().get(2);

			Object value;
			if (descriptor.startsWith("[")) {
				List<String> content = input.getSubcontent().get(0).getContent();
				List<String> toParse = content.subList(1, content.size());
				if (descriptor.equals("[Ljava/lang/String;")) { // String List
					value = toParse.stream().map(Parsers.STRING::parse).toArray(String[]::new);
				} else if (descriptor.startsWith("[L") && descriptor.endsWith(";")) { // Class List
					value = toParse.stream().map(Parsers.STRING::parse).map(Type::getType).toArray(Type[]::new);
				} else {
					value = switch (descriptor) { // Primitives
						case "[Z" -> {
							boolean[] booleans = new boolean[toParse.size()];
							for (int i = 0; i < toParse.size(); i++) {
								booleans[i] = Parsers.BOOLEAN.parse(toParse.get(i));
							}
							yield booleans;
						}
						case "[B" -> {
							byte[] bytes = new byte[toParse.size()];
							for (int i = 0; i < toParse.size(); i++) {
								bytes[i] = Parsers.BYTE.parse(toParse.get(i));
							}
							yield bytes;
						}
						case "[S" -> {
							short[] shorts = new short[toParse.size()];
							for (int i = 0; i < toParse.size(); i++) {
								shorts[i] = Parsers.SHORT.parse(toParse.get(i));
							}
							yield shorts;
						}
						case "[I" -> {
							int[] ints = new int[toParse.size()];
							for (int i = 0; i < toParse.size(); i++) {
								ints[i] = Parsers.INTEGER.parse(toParse.get(i));
							}
							yield ints;
						}
						case "[J" -> {
							long[] longs = new long[toParse.size()];
							for (int i = 0; i < toParse.size(); i++) {
								longs[i] = Parsers.LONG.parse(toParse.get(i));
							}
							yield longs;
						}
						case "[F" -> {
							float[] floats = new float[toParse.size()];
							for (int i = 0; i < toParse.size(); i++) {
								floats[i] = Parsers.FLOAT.parse(toParse.get(i));
							}
							yield floats;
						}
						case "[D" -> {
							double[] doubles = new double[toParse.size()];
							for (int i = 0; i < toParse.size(); i++) {
								doubles[i] = Parsers.DOUBLE.parse(toParse.get(i));
							}
							yield doubles;
						}
						default -> throw new IllegalArgumentException("Unknown array type for annotation literal value: " + descriptor);
					};
				}
			} else {
				String toParse = input.getContent().get(3);
				if (descriptor.equals("Ljava/lang/String;")) { // String
					value = Parsers.STRING.parse(toParse);
				} else if (descriptor.startsWith("L")) { // Class
					value = Type.getType(toParse);
				} else {
					value = switch (descriptor) { // Primitives
						case "Z" -> Parsers.BOOLEAN.parse(toParse);
						case "B" -> Parsers.BYTE.parse(toParse);
						case "S" -> Parsers.SHORT.parse(toParse);
						case "I" -> Parsers.INTEGER.parse(toParse);
						case "J" -> Parsers.LONG.parse(toParse);
						case "F" -> Parsers.FLOAT.parse(toParse);
						case "D" -> Parsers.DOUBLE.parse(toParse);
						default -> throw new IllegalArgumentException("Unknown type for annotation literal value: " + descriptor);
					};
				}
			}

			return new LiteralAnnotationValueImpl(name, value, descriptor);
		}

		@Override
		public TabSeparatedContent serialize(LiteralAnnotationValue input) {
			TabSeparatedContent content = new TabSeparatedContent(List.of(LITERAL_MAPPING_TYPE.key().toUpperCase()));
			content.pushContent(input.name());
			content.pushContent(input.descriptor());

			if (input.descriptor().startsWith("[")) {
				List<String> serialized = new ArrayList<>();
				serialized.add("VALUES");
				if (input.descriptor().equals("[Ljava/lang/String;")) { // String List
					Arrays.stream(((String[]) input.value())).map(Parsers.STRING::serialize).forEach(serialized::add);
				} else if (input.descriptor().startsWith("[L") && input.descriptor().endsWith(";")) { // Class List
					Arrays.stream(((Type[]) input.value())).map(Type::getDescriptor).map(Parsers.STRING::serialize).forEach(serialized::add);
				} else {
					switch (input.descriptor()) { // Primitives
						case "[Z" -> {
							for (boolean bool : (boolean[]) input.value()) {
								String serialize = Parsers.BOOLEAN.serialize(bool);
								serialized.add(serialize);
							}
						}
						case "[B" -> {
							for (byte b : (byte[]) input.value()) {
								String serialize = Parsers.BYTE.serialize(b);
								serialized.add(serialize);
							}
						}
						case "[S" -> {
							for (short s : (short[]) input.value()) {
								String serialize = Parsers.SHORT.serialize(s);
								serialized.add(serialize);
							}
						}
						case "[I" -> {
							for (int i : (int[]) input.value()) {
								String serialize = Parsers.INTEGER.serialize(i);
								serialized.add(serialize);
							}
						}
						case "[J" -> {
							for (long l : (long[]) input.value()) {
								String serialize = Parsers.LONG.serialize(l);
								serialized.add(serialize);
							}
						}
						case "[F" -> {
							for (float f : (float[]) input.value()) {
								String serialize = Parsers.FLOAT.serialize(f);
								serialized.add(serialize);
							}
						}
						case "[D" -> {
							for (double d : (double[]) input.value()) {
								String serialize = Parsers.DOUBLE.serialize(d);
								serialized.add(serialize);
							}
						}
						default -> throw new IllegalArgumentException("Unknown array type for annotation literal value: " + input.descriptor());
					}
				}
				content.push(new TabSeparatedContent(serialized));
			} else {
				if (input.descriptor().equals("Ljava/lang/String;")) { // String
					content.pushContent(Parsers.STRING.serialize(((String) input.value())));
				} else if (input.descriptor().startsWith("L")) { // Class
					content.pushContent(Parsers.STRING.serialize((((Type) input.value()).getDescriptor())));
				} else {
					content.pushContent(switch (input.descriptor()) { // Primitives
						case "Z" -> Parsers.BOOLEAN.serialize((Boolean) input.value());
						case "B" -> Parsers.BYTE.serialize((Byte) input.value());
						case "S" -> Parsers.SHORT.serialize((Short) input.value());
						case "I" -> Parsers.INTEGER.serialize((Integer) input.value());
						case "J" -> Parsers.LONG.serialize((Long) input.value());
						case "F" -> Parsers.FLOAT.serialize((Float) input.value());
						case "D" -> Parsers.DOUBLE.serialize((Double) input.value());
						default -> throw new IllegalArgumentException("Unknown type for annotation literal value: " + input.descriptor());
					});
				}
			}

			return content;
		}
	};

	MappingType<LiteralAnnotationValue> LITERAL_MAPPING_TYPE =
		MappingTypes.register(
			new MappingType<>("literal",
				LiteralAnnotationValue.class,
				type -> type.equals(AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE) || type.equals(NestedAnnotationValue.NESTED_MAPPING_TYPE),
				PARSER
			));

	@Override
	default MappingType<LiteralAnnotationValue> getType() {
		return LITERAL_MAPPING_TYPE;
	}

	@Override
	default boolean shouldMerge(MappingEntry<?> other) {
		boolean sameType = AnnotationValue.super.shouldMerge(other);
		if (sameType) {
			LiteralAnnotationValue lav = ((LiteralAnnotationValue) other);
			return this.name().equals(lav.name());
		}
		return false;
	}

	@Override
	default LiteralAnnotationValue merge(MappingEntry<?> other) {
		// TODO: Always prefer this overriding the other entry? maybe adding a strategy system for this?
		return this;
	}
}
