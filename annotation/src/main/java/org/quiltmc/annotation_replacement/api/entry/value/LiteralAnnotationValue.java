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

package org.quiltmc.annotation_replacement.api.entry.value;

import java.util.Arrays;

import org.objectweb.asm.Type;
import org.quiltmc.annotation_replacement.api.entry.AnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.impl.entry.value.LiteralAnnotationValueImpl;
import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.MappingType;
import org.quiltmc.mapping.api.entry.MappingTypes;
import org.quiltmc.mapping.api.serialization.Builder;
import org.quiltmc.mapping.api.serialization.Serializer;

public interface LiteralAnnotationValue extends AnnotationValue<Object, LiteralAnnotationValue> {
	Serializer<LiteralAnnotationValue> SERIALIZER =
			Serializer.dispatch("descriptor", Serializer.STRING, DescriptorMappingEntry::descriptor, s -> {
				Serializer<Object> computedSerializer;

				if (s.endsWith("Ljava/lang/String;")) { // String
					computedSerializer = s.startsWith("[") ? Serializer.STRING.list().map(_s -> _s.toArray(String[]::new), o -> Arrays.asList((String[]) o))
							: Serializer.STRING.map(_s -> _s, o -> ((String) o));
				} else if ((s.startsWith("L") || s.startsWith("[L")) && s.endsWith(";")) { // Class
					computedSerializer = s.startsWith("[") ? Serializer.STRING.map(Type::getType, Type::getDescriptor).list().map(_s -> _s.toArray(Type[]::new), o -> Arrays.asList((Type[]) o))
							: Serializer.STRING.map(Type::getType, Type::getDescriptor).map(_s -> _s, o -> ((Type) o));
				} else {
					computedSerializer = switch (s) { // Primitives
						case "Z" -> Serializer.BOOLEAN.map(_s -> _s, o -> ((Boolean) o));
						case "B" -> Serializer.BYTE.map(_s -> _s, o -> ((Byte) o));
						case "S" -> Serializer.SHORT.map(_s -> _s, o -> ((Short) o));
						case "I" -> Serializer.INTEGER.map(_s -> _s, o -> ((Integer) o));
						case "J" -> Serializer.LONG.map(_s -> _s, o -> ((Long) o));
						case "F" -> Serializer.FLOAT.map(_s -> _s, o -> ((Float) o));
						case "D" -> Serializer.DOUBLE.map(_s -> _s, o -> ((Double) o));
						case "[Z" -> Serializer.BOOLEAN.list().map(_s -> _s.toArray(Boolean[]::new), o -> Arrays.asList(((Boolean[]) o)));
						case "[B" -> Serializer.BYTE.list().map(_s -> _s.toArray(Byte[]::new), o -> Arrays.asList(((Byte[]) o)));
						case "[S" -> Serializer.SHORT.list().map(_s -> _s.toArray(Short[]::new), o -> Arrays.asList(((Short[]) o)));
						case "[I" -> Serializer.INTEGER.list().map(_s -> _s.toArray(Integer[]::new), o -> Arrays.asList(((Integer[]) o)));
						case "[J" -> Serializer.LONG.list().map(_s -> _s.toArray(Long[]::new), o -> Arrays.asList(((Long[]) o)));
						case "[F" -> Serializer.FLOAT.list().map(_s -> _s.toArray(Float[]::new), o -> Arrays.asList(((Float[]) o)));
						case "[D" -> Serializer.DOUBLE.list().map(_s -> _s.toArray(Double[]::new), o -> Arrays.asList(((Double[]) o)));
						default -> throw new IllegalArgumentException("Unknown type for annotation literal value: " + s);
					};
				}

				return Builder.EntryBuilder.<LiteralAnnotationValue>entry()
						.string("name", AnnotationValue::name)
						.string("descriptor", DescriptorMappingEntry::descriptor)
						.field("value", computedSerializer, AnnotationValue::value)
						.build(args -> new LiteralAnnotationValueImpl(args.get("name"), args.get("value"), args.get("descriptor")));
			});


	MappingType<LiteralAnnotationValue> LITERAL_MAPPING_TYPE =
			MappingTypes.register(
					new MappingType<>("literal_annotation_value",
							LiteralAnnotationValue.class,
							mappingType -> mappingType.equals(AnnotationAdditionEntry.ANNOTATION_ADDITION_MAPPING_TYPE),
							SERIALIZER
					));

	@Override
	default ValueType type() {
		return ValueType.LITERAL;
	}

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
