/*
 * Copyright 2022 QuiltMC
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

package org.quiltmc.mapping.impl.entry.naming;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;
import org.quiltmc.mapping.impl.entry.MutableAbstractNamedParentMappingEntry;

public class MutableClassEntryImpl extends MutableAbstractNamedParentMappingEntry<ClassEntry> implements MutableClassEntry {
	protected MutableClassEntryImpl(String fromName, @Nullable String toName, List<MappingEntry<?>> children) {
		super(fromName, toName, new ArrayList<>(children));
	}

	@Override
	public MutableClassEntryImpl remap() {
		return null;
	}

	@Override
	public MutableClassEntryImpl merge(MappingEntry<?> other) {
		ClassEntry clazz = ((ClassEntry) other);
		List<MappingEntry<?>> children = mergeChildren(this.children, clazz.children());
		return new MutableClassEntryImpl(this.fromName, this.toName != null ? this.toName : clazz.getToName(), children);
	}

	@Override
	public MutableMappingEntry<ClassEntry> makeMutable() {
		return this;
	}

	@Override
	public String toString() {
		return "ClassEntry[" +
			   "fromName='" + fromName + '\'' +
			   ", toName='" + toName + '\'' +
			   ", children=" + children +
			   ']';
	}

	@Override
	public ClassEntry makeFinal() {
		return new ClassEntryImpl(this.fromName, this.toName, List.copyOf(this.children));
	}
}
