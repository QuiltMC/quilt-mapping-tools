package org.quiltmc.mapping.writer;

import org.quiltmc.mapping.entry.MappingEntry;

public interface MappingEntryWriter<T extends MappingEntry<T>> {
    void write(T entry, QuiltMappingsWriter writer);
}
