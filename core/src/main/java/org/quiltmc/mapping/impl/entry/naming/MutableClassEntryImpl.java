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

package org.quiltmc.mapping.impl.entry.naming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;
import org.quiltmc.mapping.api.entry.naming.MutableFieldEntry;
import org.quiltmc.mapping.api.entry.naming.MutableMethodEntry;
import org.quiltmc.mapping.impl.entry.AbstractParentMappingEntry;
import org.quiltmc.mapping.impl.entry.MutableAbstractNamedParentMappingEntry;

public class MutableClassEntryImpl extends MutableAbstractNamedParentMappingEntry<ClassEntry> implements MutableClassEntry {
	private final Collection<MutableFieldEntry> fields;
	private final Map<String, MutableFieldEntry> fieldsByName;

	private final Collection<MutableMethodEntry> methods;
	private final Map<String, MutableMethodEntry> methodsByName;

	private final Collection<MutableClassEntry> classes;
	private final Map<String, MutableClassEntry> classesByName;

	public MutableClassEntryImpl(String fromName, @Nullable String toName, Collection<? extends MutableMappingEntry<?>> children) {
		super(fromName, toName, new ArrayList<>(children));

		fields = new ArrayList<>(streamChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE).map(field -> (MutableFieldEntry) field.makeMutable()).toList());
		fieldsByName = new HashMap<>(this.streamChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(FieldEntry::getFromName, field -> (MutableFieldEntry) field.makeMutable())));

		methods = new ArrayList<>(streamChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE).map(field -> (MutableMethodEntry) field.makeMutable()).toList());
		methodsByName = new HashMap<>(this.streamChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(MethodEntry::getFromName, field -> (MutableMethodEntry) field.makeMutable())));

		classes = new ArrayList<>(streamChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE).map(field -> (MutableClassEntry) field.makeMutable()).toList());
		classesByName = new HashMap<>(this.streamChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ClassEntry::getFromName, field -> (MutableClassEntry) field.makeMutable())));
	}

	@Override
	public MutableClassEntryImpl remap() {
		return null;
	}

	@Override
	public ClassEntry merge(MappingEntry<?> other) {
		ClassEntry clazz = ((ClassEntry) other);
		Collection<MutableMappingEntry<?>> children = AbstractParentMappingEntry.mergeChildren(this.children, clazz.children());
		return new MutableClassEntryImpl(this.fromName, this.toName != null ? this.toName : clazz.getToName(), children);
	}

	@Override
	public MutableMappingEntry<ClassEntry> makeMutable() {
		return this;
	}

	@Override
	public void addChild(MutableMappingEntry<?> entry) {
		super.addChild(entry);

		if (entry instanceof MutableFieldEntry field) {
			this.fields.add(field);
			this.fieldsByName.put(field.getFromName(), field);
		}

		if (entry instanceof MutableMethodEntry method) {
			this.methods.add(method);
			this.methodsByName.put(method.getFromName(), method);
		}

		if (entry instanceof MutableClassEntry clazz) {
			this.classes.add(clazz);
			this.classesByName.put(clazz.getFromName(), clazz);
		}
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

	public Collection<? extends MutableFieldEntry> getFields() {
		return fields;
	}

	public Map<String, ? extends MutableFieldEntry> getFieldsByName() {
		return fieldsByName;
	}

	@Override
	public Optional<? extends MutableFieldEntry> getFieldMapping(String fromName) {
		return hasFieldMapping(fromName) ? Optional.of(fieldsByName.get(fromName)) : Optional.empty();
	}

	@Override
	public MutableFieldEntry createFieldEntry(String fromName, @Nullable String toName, String descriptor) {
		MutableFieldEntryImpl field = new MutableFieldEntryImpl(fromName, toName, descriptor, List.of());
		this.addChild(field);
		return field;
	}

	@Override
	public boolean hasFieldMapping(String fromName) {
		return fieldsByName.containsKey(fromName);
	}

	public Collection<? extends MutableMethodEntry> getMethods() {
		return methods;
	}

	public Map<String, ? extends MutableMethodEntry> getMethodsByName() {
		return methodsByName;
	}

	@Override
	public Optional<? extends MutableMethodEntry> getMethodMapping(String fromName) {
		return hasMethodMapping(fromName) ? Optional.of(methodsByName.get(fromName)) : Optional.empty();
	}

	@Override
	public MutableMethodEntry createMethodEntry(String fromName, @Nullable String toName, String descriptor) {
		MutableMethodEntryImpl method = new MutableMethodEntryImpl(fromName, toName, descriptor, List.of());
		this.addChild(method);
		return method;
	}

	@Override
	public boolean hasMethodMapping(String fromName) {
		return methodsByName.containsKey(fromName);
	}

	public Collection<? extends MutableClassEntry> getClasses() {
		return classes;
	}

	public Map<String, ? extends MutableClassEntry> getClassesByName() {
		return classesByName;
	}

	@Override
	public Optional<? extends MutableClassEntry> getClassMapping(String fromName) {
		return hasClassMapping(fromName) ? Optional.of(classesByName.get(fromName)) : Optional.empty();
	}

	@Override
	public MutableClassEntry createClassEntry(String fromName, @Nullable String toName) {
		MutableClassEntryImpl clazz = new MutableClassEntryImpl(fromName, toName, List.of());
		this.addChild(clazz);
		return clazz;
	}

	@Override
	public boolean hasClassMapping(String fromName) {
		return classesByName.containsKey(fromName);
	}
}
