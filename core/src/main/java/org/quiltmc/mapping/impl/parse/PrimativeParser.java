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

package org.quiltmc.mapping.impl.parse;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.parse.Field;
import org.quiltmc.mapping.api.parse.Parser;

public record PrimativeParser<I>(Function<String, I> parser) implements Parser<I, String> {
	@Override
	public I parse(String input) {
		return parser.apply(input);
	}

	@Override
	public String serialize(I input) {
		return input.toString();
	}

	public Parser<List<I>, List<String>> list() {
		return new Parser<>() {
			@Override
			public List<I> parse(List<String> input) {
				return input.stream().map(PrimativeParser.this::parse).toList();
			}

			@Override
			public List<String> serialize(List<I> input) {
				return input.stream().map(PrimativeParser.this::serialize).toList();
			}

			@Override
			public <P> Field<P, List<I>> field(String name, Function<P, List<I>> getter) {
				return new Field<>(this, name, getter, false, false);
			}

			@Override
			public <P> Field<P, List<I>> nullableField(String name, Function<P, List<I>> getter) {
				return new Field<>(this, name, getter, true, false);
			}
		};
	}

	public Parser<Set<I>, List<String>> set() {
		return new Parser<>() {
			@Override
			public Set<I> parse(List<String> input) {
				return input.stream().map(PrimativeParser.this::parse).collect(Collectors.toSet());
			}

			@Override
			public List<String> serialize(Set<I> input) {
				return input.stream().map(PrimativeParser.this::serialize).toList();
			}

			@Override
			public <P> Field<P, Set<I>> field(String name, Function<P, Set<I>> getter) {
				return new Field<>(this, name, getter, false, false);
			}

			@Override
			public <P> Field<P, Set<I>> nullableField(String name, Function<P, Set<I>> getter) {
				return new Field<>(this, name, getter, true, false);
			}
		};
	}
}
