package com.theakashv22.util.easyobjectmapper;

import java.util.Arrays;
import java.util.Collection;

public abstract class SourceToTargetObjectPropertyMapper<S, SP, T, TP> extends SourceToTargetPropertyMapper<S, SP, T, TP> {
    private static final boolean CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT = false;
    private final boolean convertSourceToTargetProperty;
    private final Collection<? extends Mapper<SP, TP>> mappers;

    public SourceToTargetObjectPropertyMapper(
            boolean convertSourceToTargetProperty,
            Collection<? extends Mapper<SP, TP>> mappers
    ) {
        this.convertSourceToTargetProperty = convertSourceToTargetProperty;
        this.mappers = mappers;
    }

    public SourceToTargetObjectPropertyMapper(Collection<? extends Mapper<SP, TP>> mappers) {
        this(CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT, mappers);
    }

    @SafeVarargs
    public SourceToTargetObjectPropertyMapper(boolean convertSourceToTargetProperty, Mapper<SP, TP>... mappers) {
        this(convertSourceToTargetProperty, Arrays.asList(mappers));
    }

    @SafeVarargs
    public SourceToTargetObjectPropertyMapper(Mapper<SP, TP>... mappers) {
        this(CONVERT_SOURCE_TO_TARGET_PROPERTY_DEFAULT, mappers);
    }

    @Override
    public void map(S source, T target) {
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty;
        if (convertSourceToTargetProperty) {
            targetProperty = convert(sourceProperty);
            setPropertyToTarget(target, targetProperty);
        } else {
            targetProperty = getPropertyFromTarget(target);
        }
        mappers.forEach(mapper -> mapper.map(sourceProperty, targetProperty));
    }

    @Override
    protected void setPropertyToTarget(T target, TP targetProperty) {
        throw new UnsupportedOperationException("This method must be overridden in order to be used.");
    }

    @Override
    protected TP convert(SP sourceProperty) {
        throw new UnsupportedOperationException("This method must be overridden in order to be used.");
    }

    protected TP getPropertyFromTarget(T target) {
        throw new UnsupportedOperationException("This method must be overridden in order to be used.");
    }
}