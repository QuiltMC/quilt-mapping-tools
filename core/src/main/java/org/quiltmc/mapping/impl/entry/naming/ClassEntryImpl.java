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

import java.util.Collection;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;

public class ClassEntryImpl extends AbstractNamedParentMappingEntry<ClassEntry> implements ClassEntry {
	protected ClassEntryImpl(String fromName, @Nullable String toName, Collection<MappingEntry<?>> children) {
		super(fromName, toName, children);
	}

	@Override
	public ClassEntry remap() {
		return null;
	}

	@Override
	public ClassEntry merge(MappingEntry<?> other) {
		ClassEntry clazz = ((ClassEntry) other);
		Collection<MappingEntry<?>> children = mergeChildren(this.children, clazz.children());
		return new ClassEntryImpl(this.fromName, this.toName != null ? this.toName : clazz.getToName(), children);
	}

	@Override
	public MutableMappingEntry<ClassEntry> makeMutable() {
		return new MutableClassEntryImpl(this.fromName, this.toName, children.stream().map(MappingEntry::makeMutable).toList());
	}

	@Override
	public String toString() {
		return "ClassEntry[" +
			   "fromName='" + fromName + '\'' +
			   ", toName='" + toName + '\'' +
			   ", children=" + children +
			   ']';
	}
}
