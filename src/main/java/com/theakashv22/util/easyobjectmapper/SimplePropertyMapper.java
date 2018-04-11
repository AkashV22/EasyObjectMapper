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
 * This mapper will map a property of type {@link P} from a {@code source} object of type {@link S} to a property of
 * the same type in a {@code target} object of type {@link T} when {@link SimplePropertyMapper#map(Object, Object)}
 * is called.
 * @param <S> the type of the {@code source} object to map the property of type {@link P} from
 * @param <T> the type of the {@code target} object to map property of type {@link P} to
 * @param <P> the type of the property in the {@code source} and {@code target} objects to map between
 */
public abstract class SimplePropertyMapper<S, T, P> extends SourceToTargetPropertyMapper<S, P, T, P>
        implements Mapper<S, T> {
    /**
     * This implementation of {@link SourceToTargetPropertyMapper#convert(Object)} returns the {@code sourceProperty}
     * which will also be the {@code targetProperty} as a result.
     * @param sourceProperty the property of type {@link P} to return
     * @return the property of type {@link P} to map in {@code target}
     */
    @Override
    protected P convert(P sourceProperty){
        return sourceProperty;
    }
}