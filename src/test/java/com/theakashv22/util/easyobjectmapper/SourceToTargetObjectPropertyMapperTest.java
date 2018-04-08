package com.theakashv22.util.easyobjectmapper;

import org.junit.jupiter.api.Test;

public class SourceToTargetObjectPropertyMapperTest {
    @Test
    public void testSourceToTargetObjectPropertyMapper() {
        // TODO Implement this.
    }

    private static class Source {
        private final InnerSource sourceProperty;

        private Source(InnerSource sourceProperty) {
            this.sourceProperty = sourceProperty;
        }

        public InnerSource getSourceProperty() {
            return sourceProperty;
        }
    }

    private static class Target {
        private InnerTarget targetProperty;

        public InnerTarget getTargetProperty() {
            return targetProperty;
        }

        public void setTargetProperty(InnerTarget targetProperty) {
            this.targetProperty = targetProperty;
        }
    }

    private static class InnerSource {
        private final int sourceProperty;

        private InnerSource(int sourceProperty) {
            this.sourceProperty = sourceProperty;
        }

        public int getSourceProperty() {
            return sourceProperty;
        }
    }

    private static class InnerTarget {
        private String targetProperty;

        public String getTargetProperty() {
            return targetProperty;
        }

        public void setTargetProperty(String targetProperty) {
            this.targetProperty = targetProperty;
        }
    }
}