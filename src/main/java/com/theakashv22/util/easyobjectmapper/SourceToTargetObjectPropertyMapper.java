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
 * This mapper will map an object property of type {@link SP} from a {@code source} object of type {@link S} to an
 * object property of type {@link TP} in a {@code target} object of type {@link T} when {@link #map(Object, Object)} is
 * called. Also, this will call any supplied {@code innerMappers} during mapping.
 * @param <S> the type of the {@code source} object to map the object property of type {@link SP} from
 * @param <SP> the type of the object property in the {@code source} object to map from
 * @param <T> the type of the {@code target} object to map the object property of type {@link TP} to
 * @param <TP> the type of the object property in the {@code target} object to map to
 */
public abstract class SourceToTargetObjectPropertyMapper<S, SP, T, TP>
        extends SourceToTargetPropertyMapper<S, SP, T, TP> implements Mapper<S, T> {
    @SuppressWarnings("WeakerAccess")
    protected static final boolean CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT = false;
    private final boolean convertSourceToTargetProperty;
    private final Collection<? extends Mapper<SP, TP>> innerMappers;

    /**
     * Main constructor of {@link SourceToTargetObjectPropertyMapper}.<br><br>
     * If {@code convertSourceToTargetProperty} is set to {@code true}, then {@link #convert(Object)} and
     * {@link #setPropertyToTarget(Object, Object)} must be overridden, otherwise,
     * {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param convertSourceToTargetProperty determines whether the source object property should be converted to the
     *                                      target object property when {@link #map(Object, Object)} is called
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    public SourceToTargetObjectPropertyMapper(
            boolean convertSourceToTargetProperty,
            Collection<? extends Mapper<SP, TP>> innerMappers
    ) {
        this.convertSourceToTargetProperty = convertSourceToTargetProperty;
        this.innerMappers = innerMappers;
    }

    /**
     * Constructor of {@link SourceToTargetObjectPropertyMapper} that calls
     * {@link #SourceToTargetObjectPropertyMapper(boolean, Collection)} and sets {@code convertSourceToTargetProperty}
     * to {@value #CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT}.<br><br>
     * When using this constructor, {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    public SourceToTargetObjectPropertyMapper(Collection<? extends Mapper<SP, TP>> innerMappers) {
        this(CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT, innerMappers);
    }

    /**
     * Constructor of {@link SourceToTargetObjectPropertyMapper} that calls
     * {@link #SourceToTargetObjectPropertyMapper(boolean, Collection)} and converts the {@code innerMappers} vararg
     * parameter into a {@link Collection}.<br><br>
     * If {@code convertSourceToTargetProperty} is set to {@code true}, then {@link #convert(Object)} and
     * {@link #setPropertyToTarget(Object, Object)} must be overridden, otherwise,
     * {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param convertSourceToTargetProperty determines whether the source object property should be converted to the
     *                                      target object property when {@link #map(Object, Object)} is called
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    @SafeVarargs
    public SourceToTargetObjectPropertyMapper(boolean convertSourceToTargetProperty, Mapper<SP, TP>... innerMappers) {
        this(convertSourceToTargetProperty, Arrays.asList(innerMappers));
    }

    /**
     * Constructor of {@link SourceToTargetObjectPropertyMapper} that calls
     * {@link #SourceToTargetObjectPropertyMapper(boolean, Mapper[])} and sets {@code convertSourceToTargetProperty}
     * to {@value #CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT}.<br><br>
     * When using this constructor, {@link #getPropertyFromTarget(Object)} must be overridden.
     * @param innerMappers the mappers for mapping properties from the source object property to the target object
     *                     property
     */
    @SafeVarargs
    public SourceToTargetObjectPropertyMapper(Mapper<SP, TP>... innerMappers) {
        this(CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT, innerMappers);
    }

    /**
     * Maps the object property from {@code source} obtained by {@link #getPropertyFromSource(Object)} to that in
     * {@code target} set by {@link #setPropertyToTarget(Object, Object)}.<br><br>
     * Any type conversion is done by {@link #convert(Object)}, and this will also call
     * {@link Mapper#map(Object, Object)} in all {@code innerMappers} supplied into one of the constructors of
     * {@link SourceToTargetObjectPropertyMapper}.
     * @param source the object of type {@link S} to map the object property from
     * @param target the object of type {@link T} to map object property to
     * @throws Exception if mapping fails for any reason.
     * @throws UnsupportedOperationException if the relevant methods in this class have not been overridden.
     */
    @Override
    public void map(S source, T target) throws Exception {
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty;
        if (convertSourceToTargetProperty) {
            targetProperty = convert(sourceProperty);
            setPropertyToTarget(target, targetProperty);
        } else {
            targetProperty = getPropertyFromTarget(target);
        }

        for (Mapper<SP, TP> mapper : innerMappers) {
            mapper.map(sourceProperty, targetProperty);
        }
    }

    /**
     * Sets the object property to map to in {@code target}.<br><br>
     * If {@code convertSourceToTargetProperty} is set to {@code true} in the constructor used to create this mapper
     * then this method must be overridden.
     * @param target the object of type {@link T} to set the object property to
     * @param targetProperty the object property of type {@link TP} to map in {@code target}
     * @throws Exception if the property cannot be mapped to {@code target} for any reason.
     * @throws UnsupportedOperationException if this method has not been overridden.
     */
    @Override
    protected void setPropertyToTarget(T target, TP targetProperty) throws Exception {
        throw new UnsupportedOperationException("This method must be overridden in order to be used.");
    }

    /**
     * Converts the {@code sourceProperty} to the {@code targetProperty} and returns the latter.<br><br>
     * If {@code convertSourceToTargetProperty} is set to {@code true} in the constructor used to create this mapper
     * then this method must be overridden.
     * @param sourceProperty the property of type {@link SP} to convert
     * @return the property of type {@link TP} to convert to
     * @throws Exception if property conversion fails for any reason.
     * @throws UnsupportedOperationException if this method has not been overridden.
     */
    @Override
    protected TP convert(SP sourceProperty) throws Exception {
        throw new UnsupportedOperationException("This method must be overridden in order to be used.");
    }

    /**
     * Returns the object property in {@code target} to map to.<br><br>
     * If {@code convertSourceToTargetProperty} is set to {@code false} in the constructor used to create this mapper
     * then this method must be overridden.
     * @param target the object of type {@link T} to obtain the object property from
     * @return the object property of type {@link TP} in {@code source} to map to
     * @throws Exception if the object property cannot be obtained from {@code target} for any reason.
     * @throws UnsupportedOperationException if this method has not been overridden.
     */
    protected TP getPropertyFromTarget(T target) throws Exception {
        throw new UnsupportedOperationException("This method must be overridden in order to be used.");
    }
}