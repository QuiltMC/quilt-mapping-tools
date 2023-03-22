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

package org.quiltmc.annotation_replacement.impl.entry.value;

import java.util.Arrays;

import org.quiltmc.annotation_replacement.api.entry.value.LiteralAnnotationValue;
import org.quiltmc.annotation_replacement.api.entry.value.MutableLiteralAnnotationValue;

public class MutableLiteralAnnotationValueImpl implements MutableLiteralAnnotationValue {
	private String name;
	private Object value;
	private String descriptor;

	public MutableLiteralAnnotationValueImpl(String name, Object value, String descriptor) {
		this.name = name;
		this.value = value;
		this.descriptor = descriptor;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public Object value() {
		return this.value;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String descriptor() {
		return this.descriptor;
	}

	@Override
	public MutableLiteralAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableLiteralAnnotationValue makeMutable() {
		return this;
	}

	@Override
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public LiteralAnnotationValue makeFinal() {
		return new LiteralAnnotationValueImpl(this.name, this.value, this.descriptor);
	}

	@Override
	public String toString() {
		// Weird hack to make primitive arrays not crash
		String value = Arrays.deepToString(new Object[]{this.value});
		return "LiteralAnnotationValue[" +
			   "name='" + name + '\'' +
			   ", value=" + value.substring(1, value.length() - 1) +
			   ", descriptor='" + descriptor + '\'' +
			   ']';
	}
}
