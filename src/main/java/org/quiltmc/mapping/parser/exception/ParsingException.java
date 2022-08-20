package org.quiltmc.mapping.parser.exception;

import org.quiltmc.mapping.parser.QuiltMappingParser;

public class ParsingException extends RuntimeException {
    public ParsingException(QuiltMappingParser parser, String message) {
        super(message + parser.location());
    }

    public ParsingException(QuiltMappingParser parser, String message, Throwable cause) {
        super(message + parser.location(), cause);
    }

    public ParsingException(QuiltMappingParser parser, Throwable cause) {
        super("Error " + parser.location(), cause);
    }
}
