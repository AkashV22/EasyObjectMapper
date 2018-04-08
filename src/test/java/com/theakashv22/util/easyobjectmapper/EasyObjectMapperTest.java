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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EasyObjectMapperTest {
    @Test
    public void testMapperUsingVarargConstructor() {
        Collection<Mapper<Source, Target>> innerMappers = createInnerMappers();
        @SuppressWarnings({"unchecked", "SuspiciousToArrayCall"})
        Mapper<Source, Target>[] innerMapperArr = (Mapper<Source, Target>[]) innerMappers.toArray(new Mapper[0]);

        testMapper(new EasyObjectMapper<>(innerMapperArr));
    }

    @Test
    public void testMapperUsingCollectionConstructor() {
        testMapper(new EasyObjectMapper<>(createInnerMappers()));
    }

    private Collection<Mapper<Source, Target>> createInnerMappers() {
        return Arrays.asList(
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        new SourceToTargetPropertyMapper<InnerSource, Integer, InnerTarget, String>() {
                            @Override
                            protected Integer getPropertyFromSource(InnerSource source) {
                                return source.getIntProp();
                            }

                            @Override
                            protected void setPropertyToTarget(InnerTarget target, String targetProperty) {
                                target.setStringProp(targetProperty);
                            }

                            @Override
                            protected String convert(Integer sourceProperty) {
                                return String.valueOf(sourceProperty);
                            }
                        },
                        new AddSingleToCollectionPropertyMapper<InnerSource, Double, InnerTarget, String>() {
                            @Override
                            protected Double getPropertyFromSource(InnerSource source) {
                                return source.getDoubleProp();
                            }

                            @Override
                            protected Collection<String> getPropertyFromTarget(InnerTarget target) {
                                return target.getListProp();
                            }

                            @Override
                            protected String convert(Double sourceProperty) {
                                return String.valueOf(sourceProperty);
                            }
                        }
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getInnerSource();
                    }

                    @Override
                    protected InnerTarget getPropertyFromTarget(Target target) {
                        return target.getInnerTarget();
                    }
                },
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        new SimplePropertyMapper<InnerObjectProperty, InnerObjectProperty, Integer>() {
                            @Override
                            protected Integer getPropertyFromSource(InnerObjectProperty source) {
                                return source.getIntProperty();
                            }

                            @Override
                            protected void setPropertyToTarget(InnerObjectProperty target, Integer targetProperty) {
                                target.setIntProperty(targetProperty);
                            }
                        }
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getInnerObjectProperty();
                    }

                    @Override
                    protected InnerObjectProperty getPropertyFromTarget(Target target) {
                        return target.getInnerObjectProperty();
                    }
                }
        );
    }

    private void testMapper(EasyObjectMapper<Source, Target> mapper) {
        Source source = new Source(new InnerSource(10, 1.1), new InnerObjectProperty(15));
        Target target = new Target();

        mapper.map(source, target);

        assertEquals("10", target.getInnerTarget().getStringProp());
        assertEquals(Collections.singletonList("1.1"), target.getInnerTarget().getListProp());
        assertEquals(15, target.getInnerObjectProperty().getIntProperty());
    }

    private class Source {
        private final InnerSource innerSource;
        private final InnerObjectProperty innerObjectProperty;

        private Source(InnerSource innerSource, InnerObjectProperty innerObjectProperty) {
            this.innerSource = innerSource;
            this.innerObjectProperty = innerObjectProperty;
        }

        public InnerSource getInnerSource() {
            return innerSource;
        }

        public InnerObjectProperty getInnerObjectProperty() {
            return innerObjectProperty;
        }
    }

    private class Target {
        private final InnerTarget innerTarget = new InnerTarget();
        private final InnerObjectProperty innerObjectProperty = new InnerObjectProperty();

        public InnerTarget getInnerTarget() {
            return innerTarget;
        }

        public InnerObjectProperty getInnerObjectProperty() {
            return innerObjectProperty;
        }
    }

    private class InnerSource {
        private final int intProp;
        private final double doubleProp;

        private InnerSource(int intProp, double doubleProp) {
            this.intProp = intProp;
            this.doubleProp = doubleProp;
        }

        public int getIntProp() {
            return intProp;
        }

        public double getDoubleProp() {
            return doubleProp;
        }
    }

    private class InnerTarget {
        private String stringProp;
        private final List<String> listProp = new ArrayList<>();

        public String getStringProp() {
            return stringProp;
        }

        public void setStringProp(String stringProp) {
            this.stringProp = stringProp;
        }

        public List<String> getListProp() {
            return listProp;
        }
    }

    private class InnerObjectProperty {
        private int intProperty;

        public InnerObjectProperty() {}

        public InnerObjectProperty(int intProperty) {
            this.intProperty = intProperty;
        }

        public int getIntProperty() {
            return intProperty;
        }

        public void setIntProperty(int intProperty) {
            this.intProperty = intProperty;
        }
    }
}