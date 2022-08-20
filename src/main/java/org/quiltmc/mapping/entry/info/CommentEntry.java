package org.quiltmc.mapping.entry.info;

import java.util.List;

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.entry.naming.ClassEntry;
import org.quiltmc.mapping.entry.naming.FieldEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public record CommentEntry(String comment) implements MappingEntry<CommentEntry> {
    public static final MappingType<CommentEntry> COMMENT_MAPPING_TYPE = new MappingType<>("comment", MappingType.TokenType.LITERAL, CommentEntry.class, CommentEntry::parse, CommentEntry::write);

    public static CommentEntry parse(QuiltMappingParser parser) {
        return new CommentEntry(parser.string());
    }

    public void write(QuiltMappingsWriter writer) {
        writer.writeString(this.comment);
    }

    @Override
    public CommentEntry remap() {
        return this;
    }

    @Override
    public MappingType<CommentEntry> getType() {
        return COMMENT_MAPPING_TYPE;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(ClassEntry.CLASS_MAPPING_TYPE, MethodEntry.METHOD_MAPPING_TYPE, FieldEntry.FIELD_MAPPING_TYPE, ArgEntry.ARG_MAPPING_TYPE, ReturnEntry.RETURN_MAPPING_TYPE);
    }
}
