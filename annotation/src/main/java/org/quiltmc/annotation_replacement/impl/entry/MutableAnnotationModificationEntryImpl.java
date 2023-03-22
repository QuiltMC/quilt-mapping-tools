/*
 * Copyright 2022-2023 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	@Override
	public String toString() {
		return "AnnotationModificationEntry[" +
			   "removals=" + removals +
			   ", additions=" + additions +
			   ']';
	}
}
