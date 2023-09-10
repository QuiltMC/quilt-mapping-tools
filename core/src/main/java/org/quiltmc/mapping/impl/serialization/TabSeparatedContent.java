/*
 * Copyright 2023 QuiltMC
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

package org.quiltmc.mapping.impl.serialization;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class TabSeparatedContent {
	final List<TabSeparatedContent> internal = new ArrayList<>();
	final List<String> content;

	public TabSeparatedContent(List<String> content) {
		this.content = new ArrayList<>(content);
	}

	public void pushContent(String content) {
		this.content.add(content);
	}

	public void push(TabSeparatedContent internalContent) {
		internal.add(internalContent);
	}

	@Override
	public String toString() {
		return toString(0);
	}

	public String toString(int indent) {
		String internalString = internal.stream().map(in -> in.toString(indent + 1)).collect(Collectors.joining());
		if (indent < 0) {
			return internalString;
		}
		return "\t".repeat(indent) + String.join("\t", content) + "\n" + internalString;
	}

	public static TabSeparatedContent parse(String input) {
		List<String> lines = input.lines().toList();
		TabSeparatedContent root = new TabSeparatedContent(List.of());
		TabSeparatedContent current = root;
		Deque<TabSeparatedContent> content = new ArrayDeque<>();

		for (String line : lines) {
			int tabCount = 0;
			while (line.charAt(tabCount) == '\t') {
				tabCount++;
			}

			String trimmedLine = line.trim();
			List<String> splitLine = Arrays.stream(trimmedLine.split("\t")).toList();
			TabSeparatedContent lineContent = new TabSeparatedContent(splitLine);

			if (tabCount == content.size()) { // New indent
				content.push(current);
				current.push(lineContent);
				current = lineContent;
			} else if (tabCount == content.size() - 1) { // Same Level
				current = lineContent;
				content.peek().push(current);
			} else if (tabCount <= content.size() - 2) { // Going down a level
				while (content.size() - 1 > tabCount) {
					content.pop();
				}
				current = lineContent;
				assert content.peek() != null;
				content.peek().push(current);
			}
		}

		return root;
	}

	public List<String> getContent() {
		return List.copyOf(this.content);
	}

	public List<TabSeparatedContent> getSubcontent() {
		return List.copyOf(this.internal);
	}
}
