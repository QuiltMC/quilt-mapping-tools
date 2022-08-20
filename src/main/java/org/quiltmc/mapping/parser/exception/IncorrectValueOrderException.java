package org.quiltmc.mapping.parser.exception;

import org.quiltmc.mapping.parser.QuiltMappingParser;

public class IncorrectValueOrderException extends ParsingException {
    public IncorrectValueOrderException(QuiltMappingParser parser, String message) {
        super(parser, message);
    }

    public IncorrectValueOrderException(QuiltMappingParser parser, String message, Throwable cause) {
        super(parser, message, cause);
    }

    public IncorrectValueOrderException(QuiltMappingParser parser, Throwable cause) {
        super(parser, cause);
    }
}
