package org.quiltmc.annotation_replacement.api.entry;

import org.quiltmc.annotation_replacement.entry.AnnotationInformation;
import org.quiltmc.mapping.api.entry.mutable.MutableDescriptorMappingEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableParentMappingEntry;

public interface MutableAnnotationAdditionEntry extends AnnotationAdditionEntry, MutableParentMappingEntry<AnnotationAdditionEntry>, MutableDescriptorMappingEntry<AnnotationAdditionEntry>, AnnotationInformation {

}
