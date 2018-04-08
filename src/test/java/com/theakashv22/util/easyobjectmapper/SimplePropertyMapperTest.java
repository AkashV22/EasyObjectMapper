package com.theakashv22.util.easyobjectmapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimplePropertyMapperTest {

    @Test
    public void testSimplePropertyMapper() {
        SimplePropertyMapper<Source, Target, String> mapper =
                new SimplePropertyMapper<Source, Target, String>() {
            @Override
            protected String getPropertyFromSource(Source source) {
                return source.getSourceProperty();
            }

            @Override
            protected void setPropertyToTarget(Target target, String targetProperty) {
                target.setTargetProperty(targetProperty);
            }
        };

        Source source = new Source("Team, unite up!");
        Target target = new Target();

        mapper.map(source, target);

        assertEquals("Team, unite up!", target.getTargetProperty());
    }

    private static class Source {
        private final String sourceProperty;

        private Source(String sourceProperty) {
            this.sourceProperty = sourceProperty;
        }

        public String getSourceProperty() {
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