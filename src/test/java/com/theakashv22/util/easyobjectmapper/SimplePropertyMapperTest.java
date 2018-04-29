/*
 * Copyright 2018 AkashV22
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.theakashv22.util.easyobjectmapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimplePropertyMapperTest {

    @Test
    public void testMapper() throws Exception {
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

        Target target = createSourceAndTargetThenMap(mapper);

        assertEquals("Team, unite up!", target.getTargetProperty());
    }

    @Test
    public void testMapperThrowsExceptionIfGetPropertyFromSourceThrowsException() {
        SimplePropertyMapper<Source, Target, String> mapper =
                new SimplePropertyMapper<Source, Target, String>() {
                    @Override
                    protected String getPropertyFromSource(Source source) throws Exception {
                        throw new Exception();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, String targetProperty) {
                        target.setTargetProperty(targetProperty);
                    }
                };

        testMapperThrowsException(mapper);
    }

    @Test
    public void testMapperThrowsExceptionIfSetPropertyToTargetThrowsException() {
        SimplePropertyMapper<Source, Target, String> mapper =
                new SimplePropertyMapper<Source, Target, String>() {
                    @Override
                    protected String getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, String targetProperty) throws Exception {
                        throw new Exception();
                    }
                };

        testMapperThrowsException(mapper);
    }

    private void testMapperThrowsException(SimplePropertyMapper<Source, Target, String> mapper) {
        assertThrows(Exception.class, () -> createSourceAndTargetThenMap(mapper));
    }

    private Target createSourceAndTargetThenMap(SimplePropertyMapper<Source, Target, String> mapper)
            throws Exception {
        Source source = new Source("Team, unite up!");
        Target target = new Target();

        mapper.map(source, target);

        return target;
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