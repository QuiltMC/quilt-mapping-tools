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

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.FieldEntry;
import org.quiltmc.mapping.api.entry.naming.MethodEntry;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;
import org.quiltmc.mapping.api.entry.naming.MutableFieldEntry;
import org.quiltmc.mapping.api.entry.naming.MutableMethodEntry;
import org.quiltmc.mapping.impl.entry.AbstractNamedParentMappingEntry;
import org.quiltmc.mapping.impl.entry.AbstractParentMappingEntry;
import org.quiltmc.mapping.impl.entry.MutableAbstractNamedParentMappingEntry;

public class MutableClassEntryImpl extends MutableAbstractNamedParentMappingEntry<ClassEntry> implements MutableClassEntry {
	private final Collection<MutableFieldEntry> fields;
	private final Map<String, MutableFieldEntry> fieldsByName;

	private final Collection<MutableMethodEntry> methods;
	private final Map<String, MutableMethodEntry> methodsByName;

	private final Collection<MutableClassEntry> classes;
	private final Map<String, MutableClassEntry> classesByName;

	public MutableClassEntryImpl(String fromName, List<String> toNames, Collection<? extends MutableMappingEntry<?>> children) {
		super(fromName, toNames, new ArrayList<>(children));

		fields = new ArrayList<>(streamChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE).map(field -> (MutableFieldEntry) field.makeMutable()).toList());
		fieldsByName = new HashMap<>(this.streamChildrenOfType(FieldEntry.FIELD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(FieldEntry::fromName, field -> (MutableFieldEntry) field.makeMutable())));

		methods = new ArrayList<>(streamChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE).map(field -> (MutableMethodEntry) field.makeMutable()).toList());
		methodsByName = new HashMap<>(this.streamChildrenOfType(MethodEntry.METHOD_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(MethodEntry::fromName, field -> (MutableMethodEntry) field.makeMutable())));

		classes = new ArrayList<>(streamChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE).map(field -> (MutableClassEntry) field.makeMutable()).toList());
		classesByName = new HashMap<>(this.streamChildrenOfType(ClassEntry.CLASS_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ClassEntry::fromName, field -> (MutableClassEntry) field.makeMutable())));
	}

	@Override
	public MutableClassEntryImpl remap() {
		return null;
	}

	@Override
	public ClassEntry merge(MappingEntry<?> other) {
		ClassEntry clazz = ((ClassEntry) other);
		Collection<MutableMappingEntry<?>> children = AbstractParentMappingEntry.mergeChildren(this.children, clazz.children());
		return new MutableClassEntryImpl(this.fromName, AbstractNamedParentMappingEntry.joinNames(this.toNames, clazz.toNames()), children);
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
			this.fieldsByName.put(field.fromName(), field);
		}

		if (entry instanceof MutableMethodEntry method) {
			this.methods.add(method);
			this.methodsByName.put(method.fromName(), method);
		}

		if (entry instanceof MutableClassEntry clazz) {
			this.classes.add(clazz);
			this.classesByName.put(clazz.fromName(), clazz);
		}
	}

	@Override
	public String toString() {
		return "ClassEntry[" +
			   "fromName='" + fromName + '\'' +
			   ", toNames='" + toNames + '\'' +
			   ", children=" + children +
			   ']';
	}

	@Override
	public ClassEntry makeFinal() {
		return new ClassEntryImpl(this.fromName, this.toNames, List.copyOf(this.children));
	}

	public Collection<? extends MutableFieldEntry> fields() {
		return fields;
	}

	public Map<String, ? extends MutableFieldEntry> fieldsByName() {
		return fieldsByName;
	}

	@Override
	public Optional<? extends MutableFieldEntry> field(String fromName) {
		return hasField(fromName) ? Optional.of(fieldsByName.get(fromName)) : Optional.empty();
	}

	@Override
	public MutableFieldEntry createFieldEntry(String fromName, List<String> toNames, String descriptor) {
		MutableFieldEntryImpl field = new MutableFieldEntryImpl(fromName, toNames, descriptor, List.of());
		this.addChild(field);
		return field;
	}

	@Override
	public boolean hasField(String fromName) {
		return fieldsByName.containsKey(fromName);
	}

	public Collection<? extends MutableMethodEntry> methods() {
		return methods;
	}

	public Map<String, ? extends MutableMethodEntry> methodsByName() {
		return methodsByName;
	}

	@Override
	public Optional<? extends MutableMethodEntry> method(String fromName) {
		return hasMethod(fromName) ? Optional.of(methodsByName.get(fromName)) : Optional.empty();
	}

	@Override
	public MutableMethodEntry createMethodEntry(String fromName, List<String> toNames, String descriptor) {
		MutableMethodEntryImpl method = new MutableMethodEntryImpl(fromName, toNames, descriptor, List.of());
		this.addChild(method);
		return method;
	}

	@Override
	public boolean hasMethod(String fromName) {
		return methodsByName.containsKey(fromName);
	}

	public Collection<? extends MutableClassEntry> classes() {
		return classes;
	}

	public Map<String, ? extends MutableClassEntry> classesByName() {
		return classesByName;
	}

	@Override
	public Optional<? extends MutableClassEntry> classEntry(String fromName) {
		return hasClass(fromName) ? Optional.of(classesByName.get(fromName)) : Optional.empty();
	}

	@Override
	public MutableClassEntry createClassEntry(String fromName, List<String> toName) {
		MutableClassEntryImpl clazz = new MutableClassEntryImpl(fromName, toNames, List.of());
		this.addChild(clazz);
		return clazz;
	}

	@Override
	public boolean hasClass(String fromName) {
		return classesByName.containsKey(fromName);
	}
}
