package org.quiltmc.mapping.parser.exception;

import org.quiltmc.mapping.parser.QuiltMappingParser;

public class MissingValueException extends ParsingException {
    public MissingValueException(QuiltMappingParser parser, String message) {
        super(parser, message);
    }

    public MissingValueException(QuiltMappingParser parser, String message, Throwable cause) {
        super(parser, message, cause);
    }

    public MissingValueException(QuiltMappingParser parser, Throwable cause) {
        super(parser, cause);
    }
}
