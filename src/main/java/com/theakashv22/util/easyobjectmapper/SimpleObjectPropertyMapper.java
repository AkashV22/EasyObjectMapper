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

public abstract class SimpleObjectPropertyMapper<S, T, P> extends SourceToTargetObjectPropertyMapper<S, P, T, P>
        implements Mapper<S, T> {
    public SimpleObjectPropertyMapper(
            boolean convertSourceToTargetProperty,
            Collection<? extends Mapper<P, P>> mappers
    ) {
        super(convertSourceToTargetProperty, mappers);
    }

    public SimpleObjectPropertyMapper(Collection<? extends Mapper<P, P>> mappers) {
        super(mappers);
    }

    @SafeVarargs
    public SimpleObjectPropertyMapper(boolean convertSourceToTargetProperty, Mapper<P, P>... mappers) {
        this(convertSourceToTargetProperty, Arrays.asList(mappers));
    }

    @SafeVarargs
    public SimpleObjectPropertyMapper(Mapper<P, P>... mappers) {
        this(Arrays.asList(mappers));
    }

    @Override
    protected P convert(P sourceProperty) {
        return sourceProperty;
    }
}