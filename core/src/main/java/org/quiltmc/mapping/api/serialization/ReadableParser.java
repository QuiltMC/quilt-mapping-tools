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

import java.util.Iterator;

import com.google.gson.JsonElement;
import org.quiltmc.mapping.impl.serialization.JsonParser;

public interface ReadableParser<I> {
	ReadableParser<JsonElement> JSON = JsonParser.INSTANCE;

	boolean readBoolean(I input);

	byte readByte(I input);

	short readShort(I input);

	int readInteger(I input);

	long readLong(I input);

	float readFloat(I input);

	double readDouble(I input);

	String readString(I input);

	boolean hasField(String name, I parent);

	I field(String name, I parent);

	Iterator<I> list(I input);

	boolean isFieldList(String name, I parent);
}
