package org.quiltmc.mapping.parser.exception;

import org.quiltmc.mapping.parser.QuiltMappingParser;

public class InvalidValueException extends ParsingException {
    public InvalidValueException(QuiltMappingParser parser, String message) {
        super(parser, message);
    }

    public InvalidValueException(QuiltMappingParser parser, String message, Throwable cause) {
        super(parser, message, cause);
    }

    public InvalidValueException(QuiltMappingParser parser, Throwable cause) {
        super(parser, cause);
    }
}
