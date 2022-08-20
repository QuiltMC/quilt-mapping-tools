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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.objectweb.asm.Type;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.annotation.value.AnnotationAnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.AnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.EnumAnnotationValue;
import org.quiltmc.mapping.entry.annotation.value.LiteralAnnotationValue;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.parser.exception.IncorrectValueNameException;
import org.quiltmc.mapping.parser.exception.IncorrectValueOrderException;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public record AnnotationAddition(String descriptor, List<AnnotationValue> values)
		implements MappingEntry<AnnotationAddition>, AnnotationInformation {

	public static final MappingType<AnnotationAddition> ANNOTATION_ADDITION_MAPPING_TYPE = new MappingType<>("additions", MappingType.TokenType.OBJECT_ARRAY, AnnotationAddition.class, AnnotationAddition::parse, AnnotationAddition::write);

	public static AnnotationAddition parse(QuiltMappingParser parser) {
		String descriptor = null;
		List<AnnotationValue> values = null;
		while (parser.hasValues()) {
			String name = parser.valueName();
			switch (name) {
				case "descriptor" -> descriptor = parser.string();
				case "values" -> values = parseValues(parser);
				default -> parser.skip();
			}
		}

		parser.checkValuePresent("descriptor", descriptor);
		parser.checkValuePresent("values", values);

		return new AnnotationAddition(descriptor, List.copyOf(values));
	}

	@SuppressWarnings("unchecked")
	private static List<AnnotationValue> parseValues(QuiltMappingParser parser) {
		return parser.array(() -> parser.object(() -> {

			ValueType type = null;
			String valueName = null;
			String valueDescriptor = null;
			Object value = null;

			while (parser.hasValues()) {
				String objectName = parser.valueName();
				switch (objectName) {
					case "name" -> valueName = parser.string();
					case "type" -> type = parser.enumValue(ValueType.class);
					case "descriptor" -> valueDescriptor = parser.string();
					case "value" -> {
						if (type == null) {
							throw new IncorrectValueOrderException(parser, "Must have `type` before `value`");
						} else if (type == ValueType.ANNOTATION) {
							throw new IncorrectValueNameException(parser, "Nested annotations must use `values` and not `value`");
						}

						if (valueDescriptor == null) {
							throw new IncorrectValueOrderException(parser, "Must have `descriptor` before `value`");
						} else if (valueDescriptor.startsWith("[")) {
							throw new IncorrectValueNameException(parser, "Array types must use `values` not `value`");
						}

						value = getValue(parser, type, valueDescriptor);
					}
					case "values" -> {
						if (type == null) {
							throw new IncorrectValueOrderException(parser, "Must have `type` before `values`");
						} else if (valueDescriptor == null) {
							throw new IncorrectValueOrderException(parser, "Must have `descriptor` before `values`");
						} else if (!valueDescriptor.startsWith("[") && type != ValueType.ANNOTATION) {
							throw new IncorrectValueNameException(parser, "Non-array `descriptor`s or non-annotation `type`s must use `value` not `values`");
						}

						if (type == ValueType.ANNOTATION) {
							value = parseValues(parser);
						} else {
							ValueType finalType = type;
							String finalValueDescriptor = valueDescriptor;
							List<Object> valueArray = parser.array(() -> getValue(parser, finalType, finalValueDescriptor.substring(1)));
							value = valueArray.toArray(Object[]::new);
						}
					}
				}
			}

			parser.checkValuePresent("type", type);
			parser.checkValuePresent("name", valueName);
			parser.checkValuePresent("descriptor", valueDescriptor);
			parser.checkValuePresent("value", value);

			return switch (type) {
				case LITERAL, CLASS -> new LiteralAnnotationValue(valueName, value, valueDescriptor);
				case ENUM -> new EnumAnnotationValue(valueName, ((String) value), valueDescriptor);
				case ANNOTATION -> new AnnotationAnnotationValue(valueName, (List<AnnotationValue>) value, valueDescriptor);
			};
		}));
	}

	private static Object getValue(QuiltMappingParser parser, ValueType type, String valueDescriptor) {
		Object value = switch (valueDescriptor) {
			case "B", "S", "C", "I" -> parser.integerValue();
			case "J" -> parser.longValue();
			case "D" -> parser.doubleValue();
			case "F" -> parser.floatValue();
			case "Z" -> parser.booleanValue();
			default -> parser.string();
		};

		if (type == ValueType.CLASS) {
			assert value instanceof String;
			value = Type.getType((String) value);
		}

		return value;
	}

	public void write(QuiltMappingsWriter writer) {
		writer.writeString("descriptor", this.descriptor);
		writeValues(this.values, writer);
	}

	private void writeValues(List<AnnotationValue> values, QuiltMappingsWriter writer) {
		writer.name("values");
		writer.array(values, value -> writer.object(() -> {
			writer.writeString("name", value.name());
			writer.writeString("descriptor", value.descriptor());

			if (value instanceof LiteralAnnotationValue literal) {
				writer.writeString("type", (literal.value() instanceof Type) ? "class" : "literal");
				if (!(literal.value() instanceof Object[])) {
					if (literal.value() instanceof Number) {
						writer.writeNumber("value", (Number) literal.value());
					} else {
						writer.writeString("value", literal.value().toString());
					}
				} else {
					writer.name("values");
					writer.array(Arrays.asList((Object[]) literal.value()), arrayValue -> {
						if (arrayValue instanceof Number) {
							writer.number((Number) arrayValue);
						} else if (arrayValue instanceof Boolean) {
							writer.bool((Boolean) arrayValue);
						} else {
							writer.writeString(((String) arrayValue));
						}
					});
				}
			} else if (value instanceof EnumAnnotationValue enumValue) {
				writer.writeString("type", "enum");
				writer.writeString("value", enumValue.value());
			} else if (value instanceof AnnotationAnnotationValue annotation) {
				writer.writeString("type", "annotation");
				this.writeValues(annotation.values(), writer);
			}
		}));
	}

	@Override
	public AnnotationAddition remap() {
		return null;
	}

	@Override
	public MappingType<AnnotationAddition> getType() {
		return ANNOTATION_ADDITION_MAPPING_TYPE;
	}

	@Override
	public List<MappingType<?>> getTargetTypes() {
		return List.of(AnnotationModifications.ANNOTATION_MODIFICATIONS_MAPPING_TYPE);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		AnnotationAddition that = (AnnotationAddition) o;
		return Objects.equals(descriptor, that.descriptor) && this.values.containsAll(that.values) && that.values.containsAll(this.values);
	}

	private enum ValueType {
		LITERAL, CLASS, ENUM, ANNOTATION
	}
}
