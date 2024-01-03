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

package org.quiltmc.mapping.api.parse;

import java.util.function.Function;

/**
 * Represents a field for an entry that can be parsed.
 * <p>
 * Nullable fields can be read in between non-nullable fields. The order of nullable fields in the parser is the order in which they are filled.
 * <p>
 * Greedy fields will consume to the end of a line. Greedy fields must be inline. There can only be one greedy field per parser.
 *
 * @param parser   the parser for the field
 * @param name     the name of the field
 * @param getter   the getter for the field from the type
 * @param nullable true if the field is optional
 * @param inline   true if the field is inline to the serialized entry
 * @param greedy   true if the field is greedy
 * @param <P>      The entry type
 * @param <F>      The field type
 */
public record Field<P, F>(Parser<F, ?> parser, String name, Function<P, F> getter, boolean nullable, boolean inline, boolean greedy) {
}
