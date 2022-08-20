package org.quiltmc.mapping;

import org.quiltmc.mapping.entry.MappingEntry;
import org.quiltmc.mapping.parser.MappingEntryParser;
import org.quiltmc.mapping.writer.MappingEntryWriter;

public record MappingType<T extends MappingEntry<T>>(String key, TokenType tokenType, Class<T> targetEntry, MappingEntryParser<T> parser, MappingEntryWriter<T> writer) {
    public enum TokenType {
        OBJECT(true, false), LITERAL(false, false), OBJECT_ARRAY(true, true), LITERAL_ARRAY(false, true);
        private final boolean isObject, isArray;

        TokenType(boolean isObject, boolean isArray) {
            this.isObject = isObject;
            this.isArray = isArray;
        }

        public boolean isObject() {
            return isObject;
        }

        public boolean isArray() {
            return isArray;
        }
    }
}
