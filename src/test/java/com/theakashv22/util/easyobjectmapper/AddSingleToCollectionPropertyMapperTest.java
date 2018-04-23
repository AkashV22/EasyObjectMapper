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
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddSingleToCollectionPropertyMapperTest {
    @Test
    public void testMapperUsingDefaultConstructor() throws Exception {
        testMapperWithoutClearingTargetProperty(
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
    public void testMapperWithoutClearingTargetProperty() throws Exception {
        testMapperWithoutClearingTargetProperty(createMapper(false));
    }

    @Test
    public void testMapperWhileClearingTargetProperty() throws Exception {
        testMapper(
                createMapper(true),
                Collections.singletonList("10")
        );
    }

    private void testMapperWithoutClearingTargetProperty(
            AddSingleToCollectionPropertyMapper<Source, Integer, Target, String> mapper
    ) throws Exception {
        testMapper(mapper, Arrays.asList("5", "10"));
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

    private void testMapper(
            AddSingleToCollectionPropertyMapper<Source, Integer, Target, String> mapper,
            Collection<String> expectedTargetPropertyValue
    ) throws Exception {
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