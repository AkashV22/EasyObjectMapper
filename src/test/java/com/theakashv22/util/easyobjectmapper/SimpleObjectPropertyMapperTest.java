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

import java.util.Collections;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SimpleObjectPropertyMapperTest {
    @Test
    public void testMapperUsingVarargConstructor() throws Exception {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(getInnerMapper()) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerObjectProperty getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingCollectionConstructor() throws Exception {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        Collections.singletonList(getInnerMapper())
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerObjectProperty getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingVarargConstructorAndConvertSourceToTargetPropertyFalse() throws Exception {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        false,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerObjectProperty getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingCollectionConstructorAndConvertSourceToTargetPropertyFalse() throws Exception {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        false,
                        Collections.singletonList(getInnerMapper())
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerObjectProperty getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingVarargConstructorAndConvertSourceToTargetPropertyTrue() throws Exception {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerObjectProperty targetProperty) {
                        target.setTargetProperty(targetProperty);
                    }
                };

        testMapperWorksCorrectly(mapper, false);
    }

    @Test
    public void testMapperUsingCollectionConstructorAndConvertSourceToTargetPropertyTrue() throws Exception {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        true,
                        Collections.singletonList(getInnerMapper())
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerObjectProperty targetProperty) {
                        target.setTargetProperty(targetProperty);
                    }
                };

        testMapperWorksCorrectly(mapper, false);
    }

    @Test
    public void testMapperWhereSetPropertyToTargetIsNotImplemented() {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerObjectProperty targetProperty)
                            throws Exception {
                        super.setPropertyToTarget(target, targetProperty);
                    }
                };

        testMapperThrowsUnsupportedOperationException(mapper, false);
    }

    @Test
    public void testMapperWhereGetPropertyToTargetIsNotImplemented() {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        false,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerObjectProperty getPropertyFromTarget(Target target) throws Exception {
                        return super.getPropertyFromTarget(target);
                    }
                };

        testMapperThrowsUnsupportedOperationException(mapper, true);
    }

    @Test
    public void testMapperWhereSetPropertyToTargetThrowsException() {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerObjectProperty targetProperty)
                            throws Exception {
                        throw new Exception();
                    }
                };

        testMapperThrowsException(mapper, false);
    }

    @Test
    public void testMapperWhereGetPropertyToTargetThrowsException() {
        SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper =
                new SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty>(
                        false,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerObjectProperty getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerObjectProperty getPropertyFromTarget(Target target) throws Exception {
                        throw new Exception();
                    }
                };

        testMapperThrowsException(mapper, true);
    }

    private Mapper<InnerObjectProperty, InnerObjectProperty> getInnerMapper() {
        return (source, target) -> target.setInnerProperty(source.getInnerProperty());
    }

    private void testMapperWorksCorrectly(
            SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper,
            boolean setTargetPropertyToNewInnerObjectProperty
    ) throws Exception {
        Target target = createSourceAndTargetThenMap(mapper, setTargetPropertyToNewInnerObjectProperty);

        assertEquals(10, target.getTargetProperty().getInnerProperty());
    }

    private void testMapperThrowsUnsupportedOperationException(
            SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper,
            boolean setTargetPropertyToNewInnerObjectProperty
    ) {
        Class<UnsupportedOperationException> expectedType = UnsupportedOperationException.class;

        testMapperThrowsException(mapper, setTargetPropertyToNewInnerObjectProperty, expectedType);
    }

    private void testMapperThrowsException(
            SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper,
            boolean setTargetPropertyToNewInnerObjectProperty
    ) {
        testMapperThrowsException(mapper, setTargetPropertyToNewInnerObjectProperty, Exception.class);
    }

    private <T extends Exception> void testMapperThrowsException(
            SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper,
            boolean setTargetPropertyToNewInnerObjectProperty,
            Class<T> expectedType
    ) {
        assertThrows(
                expectedType,
                () -> createSourceAndTargetThenMap(mapper, setTargetPropertyToNewInnerObjectProperty)
        );
    }

    private Target createSourceAndTargetThenMap(
            SimpleObjectPropertyMapper<Source, Target, InnerObjectProperty> mapper,
            boolean setTargetPropertyToNewInnerObjectProperty
    ) throws Exception {
        Source source = new Source(new InnerObjectProperty(10));
        Target target = new Target();

        if (setTargetPropertyToNewInnerObjectProperty) {
            target.setTargetProperty(new InnerObjectProperty());
        }

        mapper.map(source, target);

        return target;
    }

    private static class Source {
        private final InnerObjectProperty sourceProperty;

        private Source(InnerObjectProperty sourceProperty) {
            this.sourceProperty = sourceProperty;
        }

        public InnerObjectProperty getSourceProperty() {
            return sourceProperty;
        }
    }

    private static class Target {
        private InnerObjectProperty targetProperty;

        public InnerObjectProperty getTargetProperty() {
            return targetProperty;
        }

        public void setTargetProperty(InnerObjectProperty targetProperty) {
            this.targetProperty = targetProperty;
        }
    }

    private static class InnerObjectProperty {
        private int innerProperty;

        private InnerObjectProperty() {}

        private InnerObjectProperty(int innerProperty) {
            this.innerProperty = innerProperty;
        }

        public int getInnerProperty() {
            return innerProperty;
        }

        public void setInnerProperty(int innerProperty) {
            this.innerProperty = innerProperty;
        }
    }
}