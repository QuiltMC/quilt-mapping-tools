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

package org.quiltmc.mapping.impl.entry.naming;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;

public class ClassEntryImpl extends AbstractNamedParentMappingEntry<ClassEntry> implements ClassEntry {
	private final Collection<? extends FieldEntry> fields;
	private final Map<String, ? extends FieldEntry> fieldsByName;

	private final Collection<? extends MethodEntry> methods;
	private final Map<String, ? extends MethodEntry> methodsByName;

	private final Collection<? extends ClassEntry> classes;
	private final Map<String, ? extends ClassEntry> classesByName;

	public ClassEntryImpl(String fromName, @Nullable String toName, Collection<MappingEntry<?>> children) {
		super(fromName, toName, children);
		fields = this.getChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE);
		fieldsByName = this.streamChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(FieldEntry::getFromName, Function.identity()));
		;

		methods = this.getChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE);
		methodsByName = this.streamChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(MethodEntry::getFromName, Function.identity()));

		classes = this.getChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE);
		classesByName = this.streamChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ClassEntry::getFromName, Function.identity()));
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

	public Collection<? extends FieldEntry> getFields() {
		return fields;
	}

	public Map<String, ? extends FieldEntry> getFieldsByName() {
		return fieldsByName;
	}

	@Override
	public Optional<? extends FieldEntry> getFieldMapping(String fromName) {
		return hasFieldMapping(fromName) ? Optional.of(fieldsByName.get(fromName)) : Optional.empty();
	}

	@Override
	public boolean hasFieldMapping(String fromName) {
		return fieldsByName.containsKey(fromName);
	}

	public Collection<? extends MethodEntry> getMethods() {
		return methods;
	}

	public Map<String, ? extends MethodEntry> getMethodsByName() {
		return methodsByName;
	}

	@Override
	public Optional<? extends MethodEntry> getMethodMapping(String fromName) {
		return hasMethodMapping(fromName) ? Optional.of(methodsByName.get(fromName)) : Optional.empty();
	}

	@Override
	public boolean hasMethodMapping(String fromName) {
		return methodsByName.containsKey(fromName);
	}

	public Collection<? extends ClassEntry> getClasses() {
		return classes;
	}

	public Map<String, ? extends ClassEntry> getClassesByName() {
		return classesByName;
	}

	@Override
	public Optional<? extends ClassEntry> getClassMapping(String fromName) {
		return hasClassMapping(fromName) ? Optional.of(classesByName.get(fromName)) : Optional.empty();
	}

	@Override
	public boolean hasClassMapping(String fromName) {
		return classesByName.containsKey(fromName);
	}
}
