/*
 * Copyright 2018 AkashV22
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

package com.theakashv22.util.easyobjectmapper;

import java.util.Arrays;
import java.util.Collection;

public class EasyObjectMapper<S, T> implements Mapper<S, T> {
    private final Collection<? extends Mapper<S, T>> mappers;

    public EasyObjectMapper(Collection<? extends Mapper<S, T>> mappers) {
        this.mappers = mappers;
    }

    @SafeVarargs
    public EasyObjectMapper(Mapper<S, T>... mappers) {
        this(Arrays.asList(mappers));
    }

    public void map(S source, T target) {
        for (Mapper<S, T> mapper : mappers) {
            mapper.map(source, target);
        }
    }
}