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

package org.quiltmc.mapping.impl.serialization;

import java.util.HashMap;
import java.util.Map;

import org.quiltmc.mapping.api.serialization.exception.MissingValueException;
import org.quiltmc.mapping.api.serialization.Builder;

public class BuilderArgsImpl implements Builder.BuilderArgs {
	Map<String, Object> values = new HashMap<>();

	@Override
	public <T> T get(String name) {
		Object o = values.get(name);
		if (o == null) {
			throw new MissingValueException();
		}
		return (T) o;
	}

	@Override
	public <T> T getOr(String name, T or) {
		Object o = values.get(name);
		if (o == null) {
			return or;
		}
		return (T) o;
	}

	@Override
	public <T> T getNullable(String name) {
		return (T) values.get(name);
	}
}
