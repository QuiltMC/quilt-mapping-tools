package org.quiltmc.mapping.api.tree;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.api.entry.naming.MutableClassEntry;

public interface MutableMappingTree extends MappingTree {
	MutableClassEntry createClassEntry(String fromName, @Nullable String toName);

	MutableClassEntry getOrCreateClassEntry(String fromName, @Nullable String toName);
}
