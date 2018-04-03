package com.theakashv22.util.easyobjectmapper;

import java.util.Arrays;
import java.util.Collection;

public abstract class SourceToTargetObjectPropertyMapper<S, SP, T, TP> extends SourceToTargetPropertyMapper<S, SP, T, TP> {
    private final Collection<? extends Mapper<SP, TP>> mappers;

    public SourceToTargetObjectPropertyMapper(Collection<? extends Mapper<SP, TP>> mappers) {
        this.mappers = mappers;
    }

    public SourceToTargetObjectPropertyMapper(Mapper<SP, TP>... mappers) {
        this(Arrays.asList(mappers));
    }

    @Override
    public void map(S source, T target) {
        super.map(source, target);
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty = getPropertyFromTarget(target);
        mappers.forEach(mapper -> mapper.map(sourceProperty, targetProperty));
    }

    protected abstract TP getPropertyFromTarget(T target);
}