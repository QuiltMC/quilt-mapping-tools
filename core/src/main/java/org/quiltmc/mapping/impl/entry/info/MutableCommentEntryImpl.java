/*
 * Copyright 2022 QuiltMC
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

package org.quiltmc.mapping.impl.entry.info;

import org.quiltmc.mapping.api.entry.MappingEntry;
import org.quiltmc.mapping.api.entry.info.CommentEntry;
import org.quiltmc.mapping.api.entry.info.MutableCommentEntry;
import org.quiltmc.mapping.api.entry.mutable.MutableMappingEntry;

class MutableCommentEntryImpl implements MutableCommentEntry {
	private String comment;

	public MutableCommentEntryImpl(String comment) {
		this.comment = comment;
	}

	@Override
	public CommentEntry remap() {
		return this;
	}

	@Override
	public CommentEntry merge(MappingEntry<?> other) {
		return new MutableCommentEntryImpl(this.comment + "\n" + ((CommentEntry) other).comment());
	}

	@Override
	public MutableMappingEntry<CommentEntry> makeMutable() {
		return this;
	}

	@Override
	public CommentEntry makeFinal() {
		return new CommentEntryImpl(this.comment);
	}

	@Override
	public String comment() {
		return this.comment;
	}

	@Override
	public void setComment(String comment) {
		this.comment = comment;
	}
}
