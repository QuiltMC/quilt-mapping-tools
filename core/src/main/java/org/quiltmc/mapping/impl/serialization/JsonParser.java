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

import java.util.Iterator;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.quiltmc.mapping.api.serialization.ReadableParser;
import org.quiltmc.mapping.api.serialization.WritableParser;

public final class JsonParser implements ReadableParser<JsonElement>, WritableParser<JsonElement> {
	public static final JsonParser INSTANCE = new JsonParser();

	@Override
	public boolean readBoolean(JsonElement input) {
		return input.getAsBoolean();
	}

	@Override
	public byte readByte(JsonElement input) {
		return input.getAsByte();
	}

	@Override
	public short readShort(JsonElement input) {
		return input.getAsShort();
	}

	@Override
	public int readInteger(JsonElement input) {
		return input.getAsInt();
	}

	@Override
	public long readLong(JsonElement input) {
		return input.getAsLong();
	}

	@Override
	public float readFloat(JsonElement input) {
		return input.getAsFloat();
	}

	@Override
	public double readDouble(JsonElement input) {
		return input.getAsDouble();
	}

	@Override
	public String readString(JsonElement input) {
		return input.getAsString();
	}

	@Override
	public boolean hasField(String name, JsonElement parent) {
		return parent.isJsonObject() && parent.getAsJsonObject().has(name);
	}

	@Override
	public JsonElement field(String name, JsonElement parent) {
		return parent.getAsJsonObject().get(name);
	}

	@Override
	public Iterator<JsonElement> list(JsonElement input) {
		return input.getAsJsonArray().iterator();
	}

	@Override
	public boolean isFieldList(String name, JsonElement parent) {
		return parent.isJsonObject() && parent.getAsJsonObject().has(name) && parent.getAsJsonObject().get(name).isJsonArray();
	}

	@Override
	public JsonElement writeBoolean(boolean value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement writeByte(byte value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement writeShort(short value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement writeInteger(int value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement writeLong(long value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement writeFloat(float value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement writeDouble(double value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement writeString(String value) {
		return new JsonPrimitive(value);
	}

	@Override
	public JsonElement object() {
		return new JsonObject();
	}

	@Override
	public JsonElement field(JsonElement parent, String name, JsonElement field) {
		if (!parent.isJsonObject()) {
			throw new IllegalArgumentException();
		}
		JsonObject object = parent.deepCopy().getAsJsonObject();
		object.add(name, field);
		return object;
	}

	@Override
	public JsonElement list(List<JsonElement> list) {
		JsonArray array = new JsonArray();
		list.forEach(array::add);
		return array;
	}
}
