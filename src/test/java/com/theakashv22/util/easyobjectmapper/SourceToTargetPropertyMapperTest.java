package com.theakashv22.util.easyobjectmapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SourceToTargetPropertyMapperTest {
    @Test
    public void testMapper() {
        SourceToTargetPropertyMapper<Source, Integer, Target, String> mapper =
                new SourceToTargetPropertyMapper<Source, Integer, Target, String>() {
            @Override
            protected Integer getPropertyFromSource(Source source) {
                return source.getSourceProperty();
            }

            @Override
            protected void setPropertyToTarget(Target target, String targetProperty) {
                target.setTargetProperty(targetProperty);
            }

            @Override
            protected String convert(Integer sourceProperty) {
                return sourceProperty != null ? sourceProperty.toString() : null;
            }
        };

        Source source = new Source(10);
        Target target = new Target();

        mapper.map(source, target);

        assertEquals("10", target.getTargetProperty());
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
        private String targetProperty;

        public String getTargetProperty() {
            return targetProperty;
        }

        public void setTargetProperty(String targetProperty) {
            this.targetProperty = targetProperty;
        }
    }
}