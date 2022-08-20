package org.quiltmc.mapping.parser.exception;

import org.quiltmc.mapping.parser.QuiltMappingParser;

public class InvalidSyntaxException extends ParsingException {
    public InvalidSyntaxException(QuiltMappingParser parser, String message) {
        super(parser, message);
    }

    public InvalidSyntaxException(QuiltMappingParser parser, String message, Throwable cause) {
        super(parser, message, cause);
    }

    public InvalidSyntaxException(QuiltMappingParser parser, Throwable cause) {
        super(parser, cause);
    }
}
