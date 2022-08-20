package org.quiltmc.mapping.entry.naming;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.AbstractParentDescriptorMappingEntry;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public class FieldEntry extends AbstractParentDescriptorMappingEntry<FieldEntry> {
    public static final MappingType<FieldEntry> FIELD_MAPPING_TYPE = new MappingType<>("fields", MappingType.TokenType.OBJECT_ARRAY, FieldEntry.class, FieldEntry::parse, FieldEntry::write);

    protected FieldEntry(String fromName, @Nullable String toName, String descriptor, List<MappingEntry<?>> children) {
        super(fromName, toName, descriptor, children, FIELD_MAPPING_TYPE);
    }

    public static FieldEntry parse(QuiltMappingParser parser) {
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
        parser.checkValue("to_name", toName, String::isEmpty);

        return new FieldEntry(fromName, toName, descriptor, children);
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
    public FieldEntry remap() {
        return null;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(ClassEntry.CLASS_MAPPING_TYPE);
    }

    @Override
    public String toString() {
        return "FieldEntry[" +
               "fromName='" + fromName + '\'' +
               ", toName='" + toName + '\'' +
               ", children=" + children +
               ", descriptor='" + descriptor + '\'' +
               ']';
    }
}
