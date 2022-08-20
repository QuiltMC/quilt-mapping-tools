package org.quiltmc.mapping.entry.naming;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.AbstractParentMappingEntry;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public class ClassEntry extends AbstractParentMappingEntry<ClassEntry> {
    public static final MappingType<ClassEntry> CLASS_MAPPING_TYPE = new MappingType<>("classes", MappingType.TokenType.OBJECT_ARRAY, ClassEntry.class, ClassEntry::parse, ClassEntry::write);

    protected ClassEntry(String fromName, @Nullable String toName, List<MappingEntry<?>> children) {
        super(fromName, toName, children, CLASS_MAPPING_TYPE);
    }

    public static ClassEntry parse(QuiltMappingParser parser) {
        String fromName = null;
        String toName = null;
        List<MappingEntry<?>> children = new ArrayList<>();

        while (parser.hasValues()) {
            String name = parser.valueName();
            switch (name) {
                case "from_name" -> fromName = parser.string();
                case "to_name" -> toName = parser.string();
                default -> parser.parseChildToken(children, name);
            }
        }

        parser.checkValuePresent("from_name", fromName);
        parser.checkValue("to_name", toName, String::isEmpty);

        return new ClassEntry(fromName, toName, children);
    }

    public void write(QuiltMappingsWriter writer) {
        writer.writeString("from_name", fromName);
        if (this.toName != null && !this.toName.isEmpty()) {
            writer.writeString("to_name", this.toName);
        }

        writer.writeChildren(this.children);
    }

    @Override
    public ClassEntry remap() {
        return null;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(CLASS_MAPPING_TYPE);
    }

    @Override
    public String toString() {
        return "ClassEntry[" +
               "fromName='" + fromName + '\'' +
               ", toName='" + toName + '\'' +
               ", children=" + children +
               ']';
    }


}
