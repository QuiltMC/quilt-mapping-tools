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

package org.quiltmc.annotation_replacement.impl.entry.value;

import java.util.Arrays;
import java.util.Objects;

import org.quiltmc.annotation_replacement.api.entry.value.LiteralAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public record LiteralAnnotationValueImpl(String name, Object value, String descriptor) implements LiteralAnnotationValue {
	@Override
	public LiteralAnnotationValue remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<LiteralAnnotationValue> makeMutable() {
		return new MutableLiteralAnnotationValueImpl(this.name, deepCopyValue(value), this.descriptor);
	}

	private Object deepCopyValue(Object value) {
		if (value.getClass().isArray()) {
			Object[] objects = ((Object[]) value);
			Object[] ret = new Object[objects.length];

			for (int i = 0; i < objects.length; i++) {
				ret[i] = deepCopyValue(objects[i]);
			}

			return ret;
		}
		return value;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		LiteralAnnotationValueImpl that = (LiteralAnnotationValueImpl) o;
		boolean valuesEqual = this.value.getClass().isArray() && that.value.getClass().isArray() ? deepEquals(this.value, that.value) : Objects.equals(this.value, that.value);
		return Objects.equals(name, that.name) && valuesEqual && Objects.equals(descriptor, that.descriptor);
	}

	private static boolean deepEquals(Object array1, Object array2) {
		Class<?> arrayType = array1.getClass().getComponentType();
		if (arrayType != array2.getClass().getComponentType()) {
			return false;
		}

		if (arrayType == Boolean.TYPE) {
			boolean[] a1 = (boolean[]) array1;
			boolean[] a2 = (boolean[]) array2;

			if (a1.length != a2.length) {
				return false;
			}

			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		} else if (arrayType == Byte.TYPE) {
			byte[] a1 = (byte[]) array1;
			byte[] a2 = (byte[]) array2;

			if (a1.length != a2.length) {
				return false;
			}

			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		} else if (arrayType == Short.TYPE) {
			short[] a1 = (short[]) array1;
			short[] a2 = (short[]) array2;

			if (a1.length != a2.length) {
				return false;
			}

			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		}  else if (arrayType == Integer.TYPE) {
			int[] a1 = (int[]) array1;
			int[] a2 = (int[]) array2;

			if (a1.length != a2.length) {
				return false;
			}

			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		} else if (arrayType == Long.TYPE) {
			long[] a1 = (long[]) array1;
			long[] a2 = (long[]) array2;

			if (a1.length != a2.length) {
				return false;
			}

			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		} else if (arrayType == Float.TYPE) {
			float[] a1 = (float[]) array1;
			float[] a2 = (float[]) array2;

			if (a1.length != a2.length) {
				return false;
			}

			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		} else if (arrayType == Double.TYPE) {
			double[] a1 = (double[]) array1;
			double[] a2 = (double[]) array2;

			if (a1.length != a2.length) {
				return false;
			}

			for (int i = 0; i < a1.length; i++) {
				if (a1[i] != a2[i]) {
					return false;
				}
			}
		} else {
			return Arrays.deepEquals((Object[]) array1, (Object[]) array2);
		}

		return true;
	}
}
