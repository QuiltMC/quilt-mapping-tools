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

package org.quiltmc.mapping.api.serialization;

import java.util.List;

import com.google.gson.JsonElement;
import org.quiltmc.mapping.impl.serialization.JsonParser;

public interface WritableParser<O> {
	WritableParser<JsonElement> JSON = JsonParser.INSTANCE;

	O writeBoolean(boolean value);

	O writeByte(byte value);

	O writeShort(short value);

	O writeInteger(int value);

	O writeLong(long value);

	O writeFloat(float value);

	O writeDouble(double value);

	O writeString(String value);

	O object();

	O field(O parent, String name, O field);

	O list(List<O> list);
}
