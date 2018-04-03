package com.theakashv22.util.easyobjectmapper;

import java.util.Collection;

public abstract class SimpleObjectPropertyMapper<S, T, P> extends SourceToTargetObjectPropertyMapper<S, P, T, P> {
    public SimpleObjectPropertyMapper(Collection<? extends Mapper<P, P>> mappers) {
        super(mappers);
    }

    @Override
    protected P convert(P sourceProperty) {
        return sourceProperty;
    }
}