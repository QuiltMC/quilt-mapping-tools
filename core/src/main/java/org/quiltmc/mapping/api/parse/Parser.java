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

package org.quiltmc.mapping.api.parse;

import java.util.function.Function;

/**
 * A way to serialize and deserialize a value
 *
 * @param <I> The deserialized type
 * @param <O> The serialized type
 */
public interface Parser<I, O> {
	/**
	 * @param input the serialized type
	 * @return the deserialized type
	 */
	I deserialize(O input);

	/**
	 * @param input the deserialized type
	 * @return the serialized type
	 */
	O serialize(I input);

	/**
	 * Creates a field for the given parser.
	 *
	 * @param name   the name of the field
	 * @param getter the getter for the field
	 * @param <P>    the type that has the field
	 * @return the new field
	 */
	default <P> Field<P, I> field(String name, Function<P, I> getter) {
		return new Field<>(this, name, getter, false, true, false);
	}

	/**
	 * Creates a nullable field for the given parser. This field can be skipped.
	 *
	 * @param name   the name of the field
	 * @param getter the getter for the field
	 * @param <P>    the type that has the field
	 * @return the new field
	 */
	default <P> Field<P, I> nullableField(String name, Function<P, I> getter) {
		return new Field<>(this, name, getter, true, true, false);
	}
}
