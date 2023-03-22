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

package org.quiltmc.mapping.impl.entry.unpick;

import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.unpick.MutableUnpickEntry;
import org.quiltmc.mapping.api.entry.unpick.UnpickEntry;

public class MutableUnpickEntryImpl implements MutableUnpickEntry {
	protected String group;
	protected @Nullable UnpickType type;

	public MutableUnpickEntryImpl(String group, @Nullable UnpickType type) {
		this.group = group;
		this.type = type;
	}

	@Override
	public MutableMappingEntry<UnpickEntry> makeMutable() {
		return this;
	}

	@Override
	public String toString() {
		return "UnpickEntry[" +
			   "group='" + group + '\'' +
			   (type != null ? ", type=" + type : "") +
			   ']';
	}

	public String group() {
		return this.group;
	}

	@Override
	public @Nullable UnpickType type() {
		return this.type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (MutableUnpickEntryImpl) obj;
		return Objects.equals(this.group, that.group) &&
			   Objects.equals(this.type, that.type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(group, type);
	}

	@Override
	public UnpickEntry makeFinal() {
		return new UnpickEntryImpl(this.group, this.type);
	}

	@Override
	public void setGroup(String group) {
		this.group = group;
	}

	@Override
	public void setType(@Nullable UnpickType type) {
		this.type = type;
	}
}
