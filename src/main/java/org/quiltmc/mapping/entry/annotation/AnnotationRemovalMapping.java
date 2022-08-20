package org.quiltmc.mapping.entry.annotation;

import java.util.List;

import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.writer.QuiltMappingsWriter;

public record AnnotationRemovalMapping(
        String descriptor) implements MappingEntry<AnnotationRemovalMapping> {
    public static final MappingType<AnnotationRemovalMapping> ANNOTATION_REMOVAL_MAPPING_TYPE = new MappingType<>("removals", MappingType.TokenType.LITERAL_ARRAY, AnnotationRemovalMapping.class, AnnotationRemovalMapping::parse, AnnotationRemovalMapping::write);

    public static AnnotationRemovalMapping parse(QuiltMappingParser parser) {
        return new AnnotationRemovalMapping(parser.string());
    }

    public void write(QuiltMappingsWriter writer) {
        writer.writeString(this.descriptor);
    }

    @Override
    public AnnotationRemovalMapping remap() {
        return null;
    }

    @Override
    public MappingType<AnnotationRemovalMapping> getType() {
        return ANNOTATION_REMOVAL_MAPPING_TYPE;
    }

    @Override
    public List<MappingType<?>> getTargetTypes() {
        return List.of(AnnotationModifications.ANNOTATION_MODIFICATIONS_MAPPING_TYPE);
    }

}
