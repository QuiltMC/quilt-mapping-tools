package org.quiltmc.mapping.entry.info;

import java.util.ArrayList;
import java.util.List;

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public record ReturnEntry(List<MappingEntry<?>> children) implements MappingEntry<ReturnEntry> {
    public static final MappingType<ReturnEntry> RETURN_MAPPING_TYPE = new MappingType<>("return", MappingType.TokenType.OBJECT, ReturnEntry.class, ReturnEntry::parse, ReturnEntry::write);

    public static ReturnEntry parse(QuiltMappingParser parser) {
        List<MappingEntry<?>> children = new ArrayList<>();

        while (parser.hasValues()) {
            String name = parser.valueName();
            parser.parseChildToken(children, name);
        }

        return new ReturnEntry(List.copyOf(children));
    }

    public void write(QuiltMappingsWriter writer) {
        writer.writeChildren(this.children);
    }

    @Override
    public ReturnEntry remap() {
        return this;
    }

    @Override
    public MappingType<ReturnEntry> getType() {
        return RETURN_MAPPING_TYPE;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(MethodEntry.METHOD_MAPPING_TYPE);
    }
}
