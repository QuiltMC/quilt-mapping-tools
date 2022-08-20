package org.quiltmc.mapping.entry.unpick;

import java.util.List;

import org.jetbrains.annotations.Nullable;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.info.ArgEntry;
import org.quiltmc.mapping.entry.info.ReturnEntry;
import org.quiltmc.mapping.entry.naming.FieldEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public record UnpickEntry(String group, @Nullable UnpickType type) implements MappingEntry<UnpickEntry> {
    public static final MappingType<UnpickEntry> UNPICK_MAPPING_TYPE = new MappingType<>("unpick", MappingType.TokenType.OBJECT, UnpickEntry.class, UnpickEntry::parse, UnpickEntry::write);

    public static UnpickEntry parse(QuiltMappingParser parser) {
        String group = null;
        UnpickType type = null;

        while (parser.hasValues()) {
            String name = parser.valueName();
            switch (name) {
                case "group" -> group = parser.string();
                case "type" -> type = parser.enumValue(UnpickType.class);
                default -> parser.skip();
            }
        }

        parser.checkValuePresent("group", group);

        return new UnpickEntry(group, type);
    }

    public void write(QuiltMappingsWriter writer) {
        writer.writeString("group", this.group);
        if (this.type != null) {
            writer.writeEnum("type", this.type);
        }
    }


    @Override
    public UnpickEntry remap() {
        return null;
    }

    @Override
    public MappingType<UnpickEntry> getType() {
        return UNPICK_MAPPING_TYPE;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(FieldEntry.FIELD_MAPPING_TYPE, ArgEntry.ARG_MAPPING_TYPE, ReturnEntry.RETURN_MAPPING_TYPE);
    }

    @Override
    public String toString() {
        return "UnpickEntry[" +
               "group='" + group + '\'' +
               (type != null ? ", type=" + type : "") +
               ']';
    }

    public enum UnpickType {
        CONSTANT, FLAG
    }
}
