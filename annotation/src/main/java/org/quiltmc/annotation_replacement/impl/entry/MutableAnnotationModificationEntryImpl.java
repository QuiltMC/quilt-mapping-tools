package org.quiltmc.annotation_replacement.impl.entry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.quiltmc.annotation_replacement.api.entry.AnnotationModificationEntry;
import org.quiltmc.annotation_replacement.api.entry.MutableAnnotationAdditionEntry;
import org.quiltmc.annotation_replacement.api.entry.MutableAnnotationModificationEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

public class MutableAnnotationModificationEntryImpl implements MutableAnnotationModificationEntry {
	private final Set<String> removals;
	private final Collection<MutableAnnotationAdditionEntry> additions;

	public MutableAnnotationModificationEntryImpl(Set<String> removals, Collection<? extends MutableAnnotationAdditionEntry> additions) {
		this.removals = new HashSet<>(removals);
		this.additions = new ArrayList<>(additions);
	}

	@Override
	public Set<String> removals() {
		return this.removals;
	}

	@Override
	public Collection<? extends MutableAnnotationAdditionEntry> additions() {
		return this.additions;
	}

	@Override
	public void addRemoval(String removal) {
		this.removals.add(removal);
	}

	@Override
	public <T extends MutableAnnotationAdditionEntry> void addAddition(T addition) {
		this.additions.add(addition);
	}

	@Override
	public AnnotationModificationEntry remap() {
		return null;
	}

	@Override
	public MutableMappingEntry<AnnotationModificationEntry> makeMutable() {
		return this;
	}

	@Override
	public AnnotationModificationEntry makeFinal() {
		return new AnnotationModificationEntryImpl(Set.copyOf(this.removals), this.additions.stream().map(MutableMappingEntry::makeFinal).toList());
	}
}
