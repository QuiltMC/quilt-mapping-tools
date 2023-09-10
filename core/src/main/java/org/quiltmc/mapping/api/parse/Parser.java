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

public interface Parser<I, O> {
	I parse(O input);

	O serialize(I input);

	default <P> Field<P, I> field(String name, Function<P, I> getter) {
		return new Field<>(this, name, getter, false, true);
	}

	default <P> Field<P, I> nullableField(String name, Function<P, I> getter) {
		return new Field<>(this, name, getter, true, true);
	}
}
