package org.quiltmc.mapping.entry.transitive;

import java.util.List;

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.naming.ClassEntry;
import org.quiltmc.mapping.entry.naming.FieldEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

// TODO: Make this be able to select the transitive entries
public record TransitiveEntry(String name) implements MappingEntry<TransitiveEntry> {
    public static final MappingType<TransitiveEntry> TRANSITIVE_MAPPING_TYPE = new MappingType<>("transitive", MappingType.TokenType.LITERAL, TransitiveEntry.class, TransitiveEntry::write, TransitiveEntry::write);

    public static TransitiveEntry write(QuiltMappingParser parser) {
        return new TransitiveEntry(parser.string());
    }

    public void write(QuiltMappingsWriter writer) {
        writer.writeString(this.name);
    }

    @Override
    public TransitiveEntry remap() {
        return this;
    }

    @Override
    public MappingType<TransitiveEntry> getType() {
        return TRANSITIVE_MAPPING_TYPE;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(MethodEntry.METHOD_MAPPING_TYPE, ClassEntry.CLASS_MAPPING_TYPE, FieldEntry.FIELD_MAPPING_TYPE);
    }
}
