package org.quiltmc.mapping.impl.tree;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.tree.MappingTree;
import org.quiltmc.mapping.api.tree.MutableMappingTree;

public class MappingTreeImpl implements MappingTree {
	private final Collection<MappingEntry<?>> entries;
	private final String fromNamespace;
	private final String toNamespace;

	private final Collection<ClassEntry> classes;
	private final Map<String, ClassEntry> classesByName;

	public MappingTreeImpl(Collection<MappingEntry<?>> entries, String fromNamespace, String toNamespace) {
		this.entries = entries;
		this.fromNamespace = fromNamespace;
		this.toNamespace = toNamespace;

		classes = this.getEntriesOfType(ClassEntry.CLASS_MAPPING_TYPE);
		classesByName = this.streamEntriesOfType(ClassEntry.CLASS_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ClassEntry::getFromName, Function.identity()));
	}

	@Override
	public String getFromNamespace() {
		return this.fromNamespace;
	}

	@Override
	public String getToNamespace() {
		return this.toNamespace;
	}

	@Override
	public Collection<? extends MappingEntry<?>> getEntries() {
		return this.entries;
	}

	@Override
	public Collection<? extends ClassEntry> getClassEntries() {
		return this.classes;
	}

	@Override
	public Optional<? extends ClassEntry> getClassEntry(String fromName) {
		return this.hasClassEntry(fromName) ? Optional.of(this.classesByName.get(fromName)) : Optional.empty();
	}

	@Override
	public boolean hasClassEntry(String fromName) {
		return this.classesByName.containsKey(fromName);
	}

	@Override
	public MutableMappingTree makeMutable() {
		return null;
	}

	@Override
	public MappingTree reverse() {
		return null;
	}

	@Override
	public MappingTree merge(MappingTree with) {
		return null;
	}

	@Override
	public MappingTree merge(MappingTree with, MappingTree dest) {
		return null;
	}

	@Override
	public MappingTree copy() {
		return null;
	}
}
