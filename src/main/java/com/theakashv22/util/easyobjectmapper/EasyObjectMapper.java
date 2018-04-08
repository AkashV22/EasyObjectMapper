package com.theakashv22.util.easyobjectmapper;

import java.util.Arrays;
import java.util.Collection;

public class EasyObjectMapper<S, T> implements Mapper<S, T> {
    private final Collection<? extends Mapper<S, T>> mappers;

    public EasyObjectMapper(Collection<? extends Mapper<S, T>> mappers) {
        this.mappers = mappers;
    }

    @SafeVarargs
    public EasyObjectMapper(Mapper<S, T>... mappers) {
        this(Arrays.asList(mappers));
    }

    public void map(S source, T target) {
        for (Mapper<S, T> mapper : mappers) {
            mapper.map(source, target);
        }
    }
}