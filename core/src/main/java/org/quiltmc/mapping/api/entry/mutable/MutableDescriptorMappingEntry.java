package org.quiltmc.mapping.api.entry.mutable;

import org.quiltmc.mapping.api.entry.DescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.NamedMappingEntry;

public interface MutableDescriptorMappingEntry<T extends DescriptorMappingEntry<T>> extends DescriptorMappingEntry<T>, MutableMappingEntry<T> {
	void setDescriptor(String descriptor);
}
