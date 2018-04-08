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