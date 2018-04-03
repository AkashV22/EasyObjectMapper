package com.theakashv22.util.easyobjectmapper;

public interface Mapper<S, T> {
    void map(S source, T target);
}