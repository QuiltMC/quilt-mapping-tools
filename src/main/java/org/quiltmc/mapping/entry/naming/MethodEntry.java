package org.quiltmc.mapping.entry.naming;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.AbstractParentDescriptorMappingEntry;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public class MethodEntry extends AbstractParentDescriptorMappingEntry<MethodEntry> {
    public static final MappingType<MethodEntry> METHOD_MAPPING_TYPE = new MappingType<>("methods", MappingType.TokenType.OBJECT_ARRAY, MethodEntry.class, MethodEntry::parse, MethodEntry::write);

    protected MethodEntry(String fromName, @Nullable String toName, String descriptor, List<MappingEntry<?>> children) {
        super(fromName, toName, descriptor, children, METHOD_MAPPING_TYPE);
    }

    public static MethodEntry parse(QuiltMappingParser parser) {
        String fromName = null;
        String toName = null;
        String descriptor = null;
        List<MappingEntry<?>> children = new ArrayList<>();

        while (parser.hasValues()) {
            String name = parser.valueName();
            switch (name) {
                case "from_name" -> fromName = parser.string();
                case "descriptor" -> descriptor = parser.string();
                case "to_name" -> toName = parser.string();
                default -> parser.parseChildToken(children, name);
            }
        }

        parser.checkValuePresent("from_name", fromName);
        parser.checkValuePresent("descriptor", descriptor);
        parser.checkValue("to_name", toName, s -> s != null && s.isEmpty());

        return new MethodEntry(fromName, toName, descriptor, children);
    }

    public void write(QuiltMappingsWriter writer) {
        writer.writeString("from_name", fromName);
        if (this.toName != null && !this.toName.isEmpty()) {
            writer.writeString("to_name", this.toName);
        }
        writer.writeString("descriptor", this.descriptor);

        writer.writeChildren(this.children);
    }

    @Override
    public MethodEntry remap() {
        return null;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(ClassEntry.CLASS_MAPPING_TYPE);
    }

    @Override
    public String toString() {
        return "MethodEntry[" +
               "fromName='" + fromName + '\'' +
               ", toName='" + toName + '\'' +
               ", children=" + children +
               ", descriptor='" + descriptor + '\'' +
               ']';
    }
}
