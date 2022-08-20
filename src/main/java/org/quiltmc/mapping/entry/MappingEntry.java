package org.quiltmc.mapping.entry;

import java.util.List;

import org.quiltmc.mapping.MappingType;

public interface MappingEntry<T extends MappingEntry<T>> {
    T remap();

    MappingType<T> getType();

    List<MappingType<?>> getTargetTypes();
}
