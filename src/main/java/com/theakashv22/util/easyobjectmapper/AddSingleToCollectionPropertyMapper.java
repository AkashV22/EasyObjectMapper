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

public abstract class AddSingleToCollectionPropertyMapper<S, SP, T, TP> implements Mapper<S, T> {
    private final boolean clearCollectionUponAdding;

    public AddSingleToCollectionPropertyMapper() {
        this(false);
    }

    public AddSingleToCollectionPropertyMapper(boolean clearCollectionUponAdding) {
        this.clearCollectionUponAdding = clearCollectionUponAdding;
    }

    public void map(S source, T target) {
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty = convert(sourceProperty);

        Collection<TP> targetPropertyCollection = getPropertyFromTarget(target);

        if (clearCollectionUponAdding) {
            targetPropertyCollection.clear();
        }

        targetPropertyCollection.add(targetProperty);
    }

    protected abstract SP getPropertyFromSource(S source);
    protected abstract Collection<TP> getPropertyFromTarget(T target);
    protected abstract TP convert(SP sourceProperty);
}