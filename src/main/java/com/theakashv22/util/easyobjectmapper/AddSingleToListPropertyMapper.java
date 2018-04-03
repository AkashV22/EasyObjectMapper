package com.theakashv22.util.easyobjectmapper;

import java.util.List;

public abstract class AddSingleToListPropertyMapper<S, SP, T, TP> implements Mapper<S, T> {
    private final boolean clearListUponAdding;

    public AddSingleToListPropertyMapper() {
        this(false);
    }

    public AddSingleToListPropertyMapper(boolean clearListUponAdding) {
        this.clearListUponAdding = clearListUponAdding;
    }

    public void map(S source, T target) {
        SP sourceProperty = getPropertyFromSource(source);
        TP targetProperty = convert(sourceProperty);

        List<TP> targetPropertyList = getPropertyFromTarget(target);

        if (clearListUponAdding) {
            targetPropertyList.clear();
        }

        targetPropertyList.add(targetProperty);
    }

    protected abstract SP getPropertyFromSource(S source);
    protected abstract List<TP> getPropertyFromTarget(T target);
    protected abstract TP convert(SP sourceProperty);
}