package com.theakashv22.util.easyobjectmapper;

public abstract class SimplePropertyMapper<S, T, P> extends SourceToTargetPropertyMapper<S, P, T, P> {
    @Override
    protected P convert(P sourceProperty){
        return sourceProperty;
    }
}