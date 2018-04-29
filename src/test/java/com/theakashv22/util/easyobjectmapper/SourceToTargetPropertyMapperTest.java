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

public class SourceToTargetPropertyMapperTest {
    @Test
    public void testMapper() throws Exception {
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

        Target target = createSourceAndTargetThenMap(mapper);

        assertEquals("10", target.getTargetProperty());
    }

    @Test
    public void testMapperThrowsExceptionIfGetPropertyFromSourceThrowsException() {
        SourceToTargetPropertyMapper<Source, Integer, Target, String> mapper =
                new SourceToTargetPropertyMapper<Source, Integer, Target, String>() {
                    @Override
                    protected Integer getPropertyFromSource(Source source) throws Exception {
                        throw new Exception();
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

        testMapperThrowsException(mapper);
    }

    @Test
    public void testMapperThrowsExceptionIfSetPropertyToTargetThrowsException() {
        SourceToTargetPropertyMapper<Source, Integer, Target, String> mapper =
                new SourceToTargetPropertyMapper<Source, Integer, Target, String>() {
                    @Override
                    protected Integer getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, String targetProperty) throws Exception {
                        throw new Exception();
                    }

                    @Override
                    protected String convert(Integer sourceProperty) {
                        return sourceProperty != null ? sourceProperty.toString() : null;
                    }
                };

        testMapperThrowsException(mapper);
    }

    @Test
    public void testMapperThrowsExceptionIfConvertThrowsException() {
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
                    protected String convert(Integer sourceProperty) throws Exception {
                        throw new Exception();
                    }
                };

        testMapperThrowsException(mapper);
    }

    private void testMapperThrowsException(SourceToTargetPropertyMapper<Source, Integer, Target, String> mapper) {
        assertThrows(Exception.class, () -> createSourceAndTargetThenMap(mapper));
    }

    private Target createSourceAndTargetThenMap(SourceToTargetPropertyMapper<Source, Integer, Target, String> mapper)
            throws Exception {
        Source source = new Source(10);
        Target target = new Target();

        mapper.map(source, target);

        return target;
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