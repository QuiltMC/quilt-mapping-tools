package org.quiltmc.mapping.writer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.quiltmc.mapping.MappingType;
import org.quiltmc.mapping.entry.annotation.AnnotationAddition;
import org.quiltmc.mapping.entry.annotation.AnnotationModifications;
import org.quiltmc.mapping.entry.annotation.AnnotationRemovalMapping;
import org.quiltmc.mapping.entry.info.ArgEntry;
import org.quiltmc.mapping.entry.info.CommentEntry;
import org.quiltmc.mapping.entry.info.ReturnEntry;
import org.quiltmc.mapping.entry.naming.ClassEntry;
import org.quiltmc.mapping.entry.naming.FieldEntry;
import org.quiltmc.mapping.entry.naming.MethodEntry;
import org.quiltmc.mapping.entry.transitive.TransitiveEntry;
import org.quiltmc.mapping.entry.unpick.UnpickEntry;
import org.quiltmc.mapping.file.QuiltMappingFile;
import org.quiltmc.mapping.parser.QuiltMappingParser;
import org.quiltmc.mapping.parser.QuiltMappingParserTest;

import static org.junit.jupiter.api.Assertions.*;

public class QuiltMappingsWriterTest {
    public static final List<MappingType<?>> TYPES = List.of(
            ClassEntry.CLASS_MAPPING_TYPE,
            MethodEntry.METHOD_MAPPING_TYPE,
            FieldEntry.FIELD_MAPPING_TYPE,
            CommentEntry.COMMENT_MAPPING_TYPE,
            ArgEntry.ARG_MAPPING_TYPE,
            ReturnEntry.RETURN_MAPPING_TYPE,
            UnpickEntry.UNPICK_MAPPING_TYPE,
            AnnotationModifications.ANNOTATION_MODIFICATIONS_MAPPING_TYPE,
            AnnotationRemovalMapping.ANNOTATION_REMOVAL_MAPPING_TYPE,
            AnnotationAddition.ANNOTATION_ADDITION_MAPPING_TYPE,
            TransitiveEntry.TRANSITIVE_MAPPING_TYPE);


    @Test
    void write() throws IOException {
        String input = new String(QuiltMappingParserTest.class.getClassLoader().getResourceAsStream("org/quiltmc/mapping/parser/TestMapping.quiltmapping").readAllBytes());
        QuiltMappingFile parsed = new QuiltMappingParser(input, TYPES).parse();
        QuiltMappingsWriter writer = new QuiltMappingsWriter(parsed, TYPES);
        StringWriter stringWriter = new StringWriter();
        writer.write(stringWriter);

        QuiltMappingFile actual = new QuiltMappingParser(stringWriter.toString(), TYPES).parse();
        assertEquals(parsed, actual, "Parses correctly");
    }
}