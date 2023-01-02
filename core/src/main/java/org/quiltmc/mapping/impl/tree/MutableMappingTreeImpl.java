package org.quiltmc.mapping.impl.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.ClassEntry;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;
import org.quiltmc.mapping.api.tree.MappingTree;
import org.quiltmc.mapping.api.tree.MutableMappingTree;
import org.quiltmc.mapping.impl.entry.naming.MutableClassEntryImpl;

public class MutableMappingTreeImpl implements MutableMappingTree {
	private final Collection<MutableMappingEntry<?>> entries;
	private final String fromNamespace;
	private final String toNamespace;

	private final Collection<MutableClassEntry> classes;
	private final Map<String, MutableClassEntry> classesByName;

	public MutableMappingTreeImpl(Collection<MutableMappingEntry<?>> entries, String fromNamespace, String toNamespace) {
		this.entries = new ArrayList<>(entries);
		this.fromNamespace = fromNamespace;
		this.toNamespace = toNamespace;

		classes = new ArrayList<>(this.streamEntriesOfType(ClassEntry.CLASS_MAPPING_TYPE).map(field -> (MutableClassEntry) field.makeMutable()).toList());
		classesByName = new HashMap<>(this.streamEntriesOfType(ClassEntry.CLASS_MAPPING_TYPE).collect(Collectors.toUnmodifiableMap(ClassEntry::getFromName, field -> (MutableClassEntry) field.makeMutable())));
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
	public Collection<? extends MutableMappingEntry<?>> getEntries() {
		return this.entries;
	}

	@Override
	public Collection<? extends MutableClassEntry> getClassEntries() {
		return this.classes;
	}

	@Override
	public Optional<? extends MutableClassEntry> getClassEntry(String fromName) {
		return this.hasClassEntry(fromName) ? Optional.of(this.classesByName.get(fromName)) : Optional.empty();
	}

	@Override
	public MutableClassEntry createClassEntry(String fromName, @Nullable String toName) {
		MutableClassEntryImpl clazz = new MutableClassEntryImpl(fromName, toName, List.of());
		this.addEntry(clazz);
		return clazz;
	}

	@Override
	public void addEntry(MutableMappingEntry<?> entry) {
		this.entries.add(entry);

		if (entry instanceof MutableClassEntry clazz) {
			this.classes.add(clazz);
			this.classesByName.put(clazz.getFromName(), clazz);
		}
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
