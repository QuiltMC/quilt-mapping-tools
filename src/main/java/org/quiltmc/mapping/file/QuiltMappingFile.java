package org.quiltmc.mapping.file;

import java.util.List;

import org.quiltmc.mapping.entry.MappingEntry;

public record QuiltMappingFile(MappingHeader header, List<MappingEntry<?>> entries) {
}
