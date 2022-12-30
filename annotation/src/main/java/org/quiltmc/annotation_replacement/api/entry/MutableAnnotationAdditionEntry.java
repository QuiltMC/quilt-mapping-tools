package org.quiltmc.annotation_replacement.api.entry;

import java.util.Collection;

import org.quiltmc.annotation_replacement.api.entry.value.MutableAnnotationValue;
import org.quiltmc.mapping.api.entry.mutable.MutableDescriptorMappingEntry;

public interface MutableAnnotationAdditionEntry extends AnnotationAdditionEntry, MutableDescriptorMappingEntry<AnnotationAdditionEntry>, WriteableAnnotationInformation {
	Collection<? extends MutableAnnotationValue<?, ?>> values();

	void addValue(MutableAnnotationValue<?, ?> value);
}
