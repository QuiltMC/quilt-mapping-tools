package org.quiltmc.mapping.entry;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;

public abstract class AbstractParentMappingEntry<T extends AbstractParentMappingEntry<T>> implements MappingEntry<T> {
    protected final String fromName;
    protected final @Nullable String toName;
    protected final List<MappingEntry<?>> children;
    protected final MappingType<T> type;

    protected AbstractParentMappingEntry(String fromName, @Nullable String toName, List<MappingEntry<?>> children, MappingType<T> type) {
        this.fromName = fromName;
        this.toName = toName;
        this.children = children;
        this.type = type;
    }

    @Override
    public MappingType<T> getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractParentMappingEntry<?> that = (AbstractParentMappingEntry<?>) o;
        return Objects.equals(fromName, that.fromName) && Objects.equals(toName, that.toName) && this.children.containsAll(that.children) && that.children.containsAll(this.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromName, toName, children, type);
    }
}
