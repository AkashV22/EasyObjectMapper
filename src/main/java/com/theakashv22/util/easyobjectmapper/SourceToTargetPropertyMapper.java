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
 * This mapper will map a property of type {@link SP} from a {@code source} object of type {@link S} to a property of
 * type {@link TP} in a {@code target} object of type {@link T} when {@link #map(Object, Object)} is called.
 * @param <S> the type of the {@code source} object to map the property of type {@link SP} from
 * @param <SP> the type of the property in the {@code source} object to map from
 * @param <T> the type of the {@code target} object to map the property of type {@link TP} to
 * @param <TP> the type of the property in the {@code target} object to map to
 */
public abstract class SourceToTargetPropertyMapper<S, SP, T, TP> implements Mapper<S, T> {
    /**
     * Maps the property from {@code source} obtained by {@link #getPropertyFromSource(Object)} to that in
     * {@code target} set by {@link #setPropertyToTarget(Object, Object)}.<br><br>
     * Any type conversion is done by {@link #convert(Object)}.
     * @param source the object of type {@link S} to map the property from
     * @param target the object of type {@link T} to map property to
     * @throws Exception if mapping fails for any reason.
     */
    public void map(S source, T target) throws Exception {
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty = convert(sourceProperty);
        setPropertyToTarget(target, targetProperty);
    }

    /**
     * Returns the property in {@code source} to map from.
     * @param source the object of type {@link S} to obtain the property from
     * @return the property of type {@link SP} in {@code source} to map
     * @throws Exception if the property cannot be obtained from {@code source} for any reason.
     */
    protected abstract SP getPropertyFromSource(S source) throws Exception;

    /**
     * Sets the property to map to in {@code target}.
     * @param target the object of type {@link T} to set the property to
     * @param targetProperty the property of type {@link TP} to map in {@code target}
     * @throws Exception if the property cannot be mapped to {@code target} for any reason.
     */
    protected abstract void setPropertyToTarget(T target, TP targetProperty) throws Exception;

    /**
     * Converts the {@code sourceProperty} to the {@code targetProperty} and returns the latter.
     * @param sourceProperty the property of type {@link SP} to convert
     * @return the property of type {@link TP} to convert to
     * @throws Exception if property conversion fails for any reason.
     */
    protected abstract TP convert(SP sourceProperty) throws Exception;
}