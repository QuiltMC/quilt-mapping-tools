package org.quiltmc.mapping.entry;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;

public abstract class AbstractParentDescriptorMappingEntry<T extends AbstractParentDescriptorMappingEntry<T>> extends AbstractParentMappingEntry<T> {
    protected final String descriptor;

    protected AbstractParentDescriptorMappingEntry(String fromName, @Nullable String toName, String descriptor, List<MappingEntry<?>> children, MappingType<T> type) {
        super(fromName, toName, children, type);
        this.descriptor = descriptor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AbstractParentDescriptorMappingEntry<?> that = (AbstractParentDescriptorMappingEntry<?>) o;
        return Objects.equals(descriptor, that.descriptor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), descriptor);
    }
}
