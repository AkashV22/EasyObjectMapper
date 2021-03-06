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
 * This mapper will map an object property of type {@link P} from a {@code source} object of type {@link S} to an object
 * property of the same type in a {@code target} object of type {@link T} when {@link #map(Object, Object)} is called.
 * Also, this will call any supplied {@code innerMappers} during mapping.
 * @param <S> the type of the {@code source} object to map the object property of type {@link P} from
 * @param <T> the type of the {@code target} object to map the object property of type {@link P} to
 * @param <P> the type of the object property in the {@code source} and {@code target} objects to map between
 */
public abstract class SimpleObjectPropertyMapper<S, T, P> extends SourceToTargetObjectPropertyMapper<S, P, T, P>
        implements Mapper<S, T> {
    /**
     * The constructor of {@link SimpleObjectPropertyMapper} that calls
     * {@link SourceToTargetObjectPropertyMapper#SourceToTargetObjectPropertyMapper(boolean, Collection)}.<br><br>
     * If {@code convertSourceToTargetProperty} is set to {@code true}, then
     * {@link #setPropertyToTarget(Object, Object)} must be overridden, otherwise,
     * {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param convertSourceToTargetProperty determines whether the source object property should be converted to the
     *                                      target object property when {@link #map(Object, Object)} is called
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    public SimpleObjectPropertyMapper(
            boolean convertSourceToTargetProperty,
            Collection<? extends Mapper<P, P>> innerMappers
    ) {
        super(convertSourceToTargetProperty, innerMappers);
    }

    /**
     * The constructor of {@link SimpleObjectPropertyMapper} that calls
     * {@link SourceToTargetObjectPropertyMapper#SourceToTargetObjectPropertyMapper(Collection)}.<br><br>
     * When using this constructor, {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    public SimpleObjectPropertyMapper(Collection<? extends Mapper<P, P>> innerMappers) {
        super(innerMappers);
    }

    /**
     * The constructor of {@link SimpleObjectPropertyMapper} that calls
     * {@link #SimpleObjectPropertyMapper(boolean, Collection)}.<br><br>
     * If {@code convertSourceToTargetProperty} is set to {@code true}, then
     * {@link #setPropertyToTarget(Object, Object)} must be overridden, otherwise,
     * {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param convertSourceToTargetProperty determines whether the source object property should be converted to the
     *                                      target object property when {@link #map(Object, Object)} is called
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    @SafeVarargs
    public SimpleObjectPropertyMapper(boolean convertSourceToTargetProperty, Mapper<P, P>... innerMappers) {
        this(convertSourceToTargetProperty, Arrays.asList(innerMappers));
    }

    /**
     * The constructor of {@link SimpleObjectPropertyMapper} that calls
     * {@link #SimpleObjectPropertyMapper(Collection)}.<br><br>
     * When using this constructor, {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    @SafeVarargs
    public SimpleObjectPropertyMapper(Mapper<P, P>... innerMappers) {
        this(Arrays.asList(innerMappers));
    }

    /**
     * This implementation of {@link SourceToTargetObjectPropertyMapper#convert(Object)} returns the
     * {@code sourceProperty} which will also be the {@code targetProperty} as a result.
     * @param sourceProperty the object property of type {@link P} to return
     * @return the object property of type {@link P} to map in {@code target}
     */
    @Override
    protected P convert(P sourceProperty) {
        return sourceProperty;
    }
}