package com.theakashv22.util.easyobjectmapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddSingleToCollectionPropertyMapperTest {
    @Test
    public void testAddSingleToCollectionPropertyMapperUsingDefaultConstructor() {
        testAddSingleToCollectionPropertyMapperWithoutClearingTargetProperty(
                new AddSingleToCollectionPropertyMapper<Source, Integer, Target, String>() {
                    @Override
                    protected Integer getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected Collection<String> getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }

                    @Override
                    protected String convert(Integer sourceProperty) {
                        return sourceProperty.toString();
                    }
                }
        );
    }

    @Test
    public void testAddSingleToCollectionPropertyMapperWithoutClearingTargetProperty() {
        testAddSingleToCollectionPropertyMapperWithoutClearingTargetProperty(createMapper(false));
    }

    @Test
    public void testAddSingleToCollectionPropertyMapperWhileClearingTargetProperty() {
        testAddSingleToListPropertyMapper(
                createMapper(true),
                Collections.singletonList("10")
        );
    }

    private void testAddSingleToCollectionPropertyMapperWithoutClearingTargetProperty(
            AddSingleToCollectionPropertyMapper<Source, Integer, Target, String> mapper
    ) {
        testAddSingleToListPropertyMapper(mapper, Arrays.asList("5", "10"));
    }

    private AddSingleToCollectionPropertyMapper<Source, Integer, Target, String> createMapper(
            boolean clearCollectionUponAdding
    ) {
        return new AddSingleToCollectionPropertyMapper<Source, Integer, Target, String>(clearCollectionUponAdding) {
            @Override
            protected Integer getPropertyFromSource(Source source) {
                return source.getSourceProperty();
            }

            @Override
            protected Collection<String> getPropertyFromTarget(Target target) {
                return target.getTargetProperty();
            }

            @Override
            protected String convert(Integer sourceProperty) {
                return sourceProperty.toString();
            }
        };
    }

    private void testAddSingleToListPropertyMapper(
            AddSingleToCollectionPropertyMapper<Source, Integer, Target, String> mapper,
            Collection<String> expectedTargetPropertyValue
    ) {
        Source source = new Source(10);
        Target target = new Target();
        target.getTargetProperty().add("5");

        mapper.map(source, target);

        assertEquals(expectedTargetPropertyValue, target.getTargetProperty());
    }

    private static class Source {
        private final int sourceProperty;

        private Source(int sourceProperty) {
            this.sourceProperty = sourceProperty;
        }

        public int getSourceProperty() {
            return sourceProperty;
        }
    }

    private static class Target {
        private Collection<String> targetProperty = new ArrayList<>();

        public Collection<String> getTargetProperty() {
            return targetProperty;
        }
    }
}