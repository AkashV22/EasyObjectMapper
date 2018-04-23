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

import java.util.Collection;

/**
 * This mapper will add the value of a property of type {@link SP} from a {@code source} object of type {@link S} into a
 * {@link Collection} property of type {@link TP} in a {@code target} object of type {@link T} when
 * {@link #map(Object, Object)} is called.
 * @param <S> the type of the {@code source} object to obtain the property of type {@link SP} from
 * @param <SP> the type of the property in the {@code source} object to map from
 * @param <T> the type of the {@code target} object that contains the {@link Collection} property of type {@link TP}
 * @param <TP> the type of the {@link Collection} property in the {@code target} object to add to
 */
public abstract class AddSingleToCollectionPropertyMapper<S, SP, T, TP> implements Mapper<S, T> {
    private final boolean clearCollectionUponAdding;

    /**
     * No-arg constructor of {@link AddSingleToCollectionPropertyMapper}. This calls
     * {@link #AddSingleToCollectionPropertyMapper(boolean)} and sets {@code clearCollectionUponAdding} to
     * {@code false}.
     */
    public AddSingleToCollectionPropertyMapper() {
        this(false);
    }

    /**
     * Main constructor of {@link AddSingleToCollectionPropertyMapper}.
     * @param clearCollectionUponAdding if {@code true}, the {@link Collection} property is cleared when adding to it.
     */
    public AddSingleToCollectionPropertyMapper(boolean clearCollectionUponAdding) {
        this.clearCollectionUponAdding = clearCollectionUponAdding;
    }

    /**
     * Adds the property obtained by {@link #getPropertyFromSource(Object)} to the {@link Collection} property obtained
     * by {@link #getPropertyFromTarget(Object)}.<br><br>
     * Any type conversion is done by {@link #convert(Object)}, and the {@link Collection} property is cleared before
     * the converted property is added to it if {@code clearCollectionUponAdding} is set to {@code true} in the
     * constructor used to create this mapper.
     * @param source the object of type {@link S} to obtain the {@code sourceProperty} from
     * @param target the object of type {@link T} that contains the {@link Collection} property to add the
     * {@code targetProperty} to
     * @throws Exception if mapping fails for any reason.
     */
    public void map(S source, T target) throws Exception {
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty = convert(sourceProperty);

        Collection<TP> targetPropertyCollection = getPropertyFromTarget(target);

        if (clearCollectionUponAdding) {
            targetPropertyCollection.clear();
        }

        targetPropertyCollection.add(targetProperty);
    }

    /**
     * Returns the {@code sourceProperty} in {@code source}.
     * @param source the object of type {@link S} to obtain the property from
     * @return the property of type {@link SP} in {@code source}
     * @throws Exception if the property cannot be obtained from {@code source} for any reason.
     */
    protected abstract SP getPropertyFromSource(S source) throws Exception;

    /**
     * Returns the {@link Collection} property in the {@code target} object to add to.
     * @param target the object of type {@link T} to obtain the {@link Collection} property from
     * @return the {@link Collection} property of type {@link TP} to add to
     * @throws Exception if the {@link Collection} property cannot be obtained from {@code target} for any reason.
     */
    protected abstract Collection<TP> getPropertyFromTarget(T target) throws Exception;

    /**
     * Converts the {@code sourceProperty} to the {@code targetProperty} and returns the latter.
     * @param sourceProperty the property of type {@link SP} to convert
     * @return the property of type {@link TP} to convert to
     * @throws Exception if property conversion fails for any reason.
     */
    protected abstract TP convert(SP sourceProperty) throws Exception;
}