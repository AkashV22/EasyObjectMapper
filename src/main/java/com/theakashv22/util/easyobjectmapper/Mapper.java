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

/**
 * This is an interface for mapping the properties from an object of type {@link S} to an object of type {@link T}.
 * @param <S> the type of the {@code source} object to map properties from
 * @param <T> the type of the {@code target} object to map properties to
 */
public interface Mapper<S, T> {
    /**
     * Map the properties from {@code source} to {@code target}.
     * @param source the object of type {@link S} to map properties from
     * @param target the object of type {@link T} to map properties to
     */
    void map(S source, T target);
}