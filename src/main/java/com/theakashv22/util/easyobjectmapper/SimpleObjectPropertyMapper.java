package com.theakashv22.util.easyobjectmapper;

import java.util.Arrays;
import java.util.Collection;

public abstract class SimpleObjectPropertyMapper<S, T, P> extends SourceToTargetObjectPropertyMapper<S, P, T, P> {
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