package org.quiltmc.mapping.parser;

import org.quiltmc.mapping.entry.MappingEntry;

public interface MappingEntryParser<T extends MappingEntry<T>> {
    T parse(QuiltMappingParser parser);
}
