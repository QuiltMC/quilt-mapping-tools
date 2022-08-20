package org.quiltmc.mapping.file;

import java.util.List;

public record MappingHeader(String fromNamespace, String toNamespace, List<String> extensions) {
}
