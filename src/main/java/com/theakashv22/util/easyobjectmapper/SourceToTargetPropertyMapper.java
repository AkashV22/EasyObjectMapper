package com.theakashv22.util.easyobjectmapper;

public abstract class SourceToTargetPropertyMapper<S, SP, T, TP> implements Mapper<S, T> {
    public void map(S source, T target) {
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty = convert(sourceProperty);
        setPropertyToTarget(target, targetProperty);
    }

    protected abstract SP getPropertyFromSource(S source);
    protected abstract void setPropertyToTarget(T target, TP targetProperty);
    protected abstract TP convert(SP sourceProperty);
}