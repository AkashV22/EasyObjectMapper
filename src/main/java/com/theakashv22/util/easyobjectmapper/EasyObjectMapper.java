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

/**
 * This is the root object mapper that will map properties from an object of type {@link S} to an object of type
 * {@link T} upon calling {@link #map(Object, Object)} using the supplied {@code innerMappers}.
 * @param <S> the type of the {@code source} object to map properties from
 * @param <T> the type of the {@code target} object to map properties to
 */
public class EasyObjectMapper<S, T> implements Mapper<S, T> {
    private final Collection<? extends Mapper<S, T>> innerMappers;

    /**
     * Constructor of {@link EasyObjectMapper} that takes a {@link Collection} of {@code innerMappers}.
     * @param innerMappers the {@link Mapper} {@link Collection} containing mappers to map the properties of
     * {@code source} to {@code target}
     */
    public EasyObjectMapper(Collection<? extends Mapper<S, T>> innerMappers) {
        this.innerMappers = innerMappers;
    }

    /**
     * Constructor of {@link EasyObjectMapper} that takes {@code innerMappers} via a vararg parameter.
     * @param innerMappers the {@link Mapper} objects to map the properties of {@code source} to {@code target}
     */
    @SafeVarargs
    public EasyObjectMapper(Mapper<S, T>... innerMappers) {
        this(Arrays.asList(innerMappers));
    }

    /**
     * Maps the properties from {@code source} to {@code target} using the supplied {@code innerMappers}.
     * @param source the object of type {@link S} to map properties from
     * @param target the object of type {@link T} to map properties to
     * @throws Exception if mapping fails for any reason.
     */
    public void map(S source, T target) throws Exception {
        for (Mapper<S, T> mapper : innerMappers) {
            mapper.map(source, target);
        }
    }
}