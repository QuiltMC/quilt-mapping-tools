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

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.parse.Field;
import org.quiltmc.mapping.api.parse.Parser;

public record PrimativeParser<I>(Function<String, I> parser) implements Parser<I, String> {
	@Override
	public I deserialize(String input) {
		return parser.apply(input);
	}

	@Override
	public String serialize(I input) {
		return input.toString();
	}

	public Parser<List<I>, List<String>> list() {
		return new CollectionParser<>(this, Collectors.toList(), false);
	}

	public Parser<List<I>, List<String>> greedyList() {
		return new CollectionParser<>(this, Collectors.toList(), true);
	}

	public Parser<Set<I>, List<String>> set() {
		return new CollectionParser<>(this, Collectors.toSet(), false);
	}

	public Parser<Set<I>, List<String>> greedySet() {
		return new CollectionParser<>(this, Collectors.toSet(), true);
	}

	private record CollectionParser<T, C extends Collection<T>>(PrimativeParser<T> parser, Collector<T, ?, C> collector, boolean greedy) implements Parser<C, List<String>> {
		@Override
		public C deserialize(List<String> input) {
			return input.stream().map(this.parser::deserialize).collect(this.collector);
		}

		@Override
		public List<String> serialize(C input) {
			return input.stream().map(this.parser::serialize).toList();
		}

		@Override
		public <P> Field<P, C> field(String name, Function<P, C> getter) {
			return new Field<>(this, name, getter, false, this.greedy, this.greedy);
		}

		@Override
		public <P> Field<P, C> nullableField(String name, Function<P, C> getter) {
			return new Field<>(this, name, getter, true, this.greedy, this.greedy);
		}
	}
}
