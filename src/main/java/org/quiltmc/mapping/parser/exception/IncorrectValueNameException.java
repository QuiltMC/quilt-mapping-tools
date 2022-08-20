package org.quiltmc.mapping.parser.exception;

import org.quiltmc.mapping.parser.QuiltMappingParser;

public class IncorrectValueNameException extends ParsingException {
    public IncorrectValueNameException(QuiltMappingParser parser, String message) {
        super(parser, message);
    }

    public IncorrectValueNameException(QuiltMappingParser parser, String message, Throwable cause) {
        super(parser, message, cause);
    }

    public IncorrectValueNameException(QuiltMappingParser parser, Throwable cause) {
        super(parser, cause);
    }
}
