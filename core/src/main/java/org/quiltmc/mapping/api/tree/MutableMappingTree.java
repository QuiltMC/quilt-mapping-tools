package org.quiltmc.mapping.api.tree;

import java.util.Collection;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;

public interface MutableMappingTree extends MappingTree {
	@Override
	Collection<? extends MutableMappingEntry<?>> getEntries();

	@Override
	Collection<? extends MutableClassEntry> getClassEntries();

	@Override
	Optional<? extends MutableClassEntry> getClassEntry(String fromName);

	MutableClassEntry createClassEntry(String fromName, @Nullable String toName);

	default MutableClassEntry getOrCreateClassEntry(String fromName, @Nullable String toName) {
		return this.hasClassEntry(fromName) ? this.getClassEntry(fromName).get() : this.createClassEntry(fromName, toName);
	}

	void addEntry(MutableMappingEntry<?> entry);
}
