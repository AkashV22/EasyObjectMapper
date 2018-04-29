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

public class SourceToTargetObjectPropertyMapperTest {
    @Test
    public void testMapperUsingVarargConstructor() throws Exception {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(getInnerMapper()) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerTarget getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingCollectionConstructor() throws Exception {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        Collections.singletonList(getInnerMapper())
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerTarget getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingVarargConstructorAndConvertSourceToTargetPropertyFalse() throws Exception {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        false,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerTarget getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingCollectionConstructorAndConvertSourceToTargetPropertyFalse() throws Exception {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        false,
                        Collections.singletonList(getInnerMapper())
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerTarget getPropertyFromTarget(Target target) {
                        return target.getTargetProperty();
                    }
                };

        testMapperWorksCorrectly(mapper, true);
    }

    @Test
    public void testMapperUsingVarargConstructorAndConvertSourceToTargetPropertyTrue() throws Exception {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerTarget targetProperty) {
                        target.setTargetProperty(targetProperty);
                    }

                    @Override
                    protected InnerTarget convert(InnerSource sourceProperty) {
                        return sourceProperty.createInnerTarget();
                    }
                };

        testMapperWorksCorrectly(mapper, false);
    }

    @Test
    public void testMapperUsingCollectionConstructorAndConvertSourceToTargetPropertyTrue() throws Exception {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        true,
                        Collections.singletonList(getInnerMapper())
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerTarget targetProperty) {
                        target.setTargetProperty(targetProperty);
                    }

                    @Override
                    protected InnerTarget convert(InnerSource sourceProperty) {
                        return sourceProperty.createInnerTarget();
                    }
                };

        testMapperWorksCorrectly(mapper, false);
    }

    @Test
    public void testMapperWhereConvertIsNotImplemented() {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerTarget targetProperty) {
                        target.setTargetProperty(targetProperty);
                    }

                    @Override
                    protected InnerTarget convert(InnerSource sourceProperty) throws Exception {
                        return super.convert(sourceProperty);
                    }
                };

        testMapperThrowsUnsupportedOperationException(mapper, false);
    }

    @Test
    public void testMapperWhereSetPropertyToTargetIsNotImplemented() {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerTarget targetProperty) throws Exception {
                        super.setPropertyToTarget(target, targetProperty);
                    }

                    @Override
                    protected InnerTarget convert(InnerSource sourceProperty) {
                        return sourceProperty.createInnerTarget();
                    }
                };

        testMapperThrowsUnsupportedOperationException(mapper, false);
    }

    @Test
    public void testMapperWhereGetPropertyToTargetIsNotImplemented() {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        false,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerTarget getPropertyFromTarget(Target target) throws Exception {
                        return super.getPropertyFromTarget(target);
                    }
                };

        testMapperThrowsUnsupportedOperationException(mapper, true);
    }
    @Test
    public void testMapperWhereConvertThrowsException() {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerTarget targetProperty) {
                        target.setTargetProperty(targetProperty);
                    }

                    @Override
                    protected InnerTarget convert(InnerSource sourceProperty) throws Exception {
                        throw new Exception();
                    }
                };

        testMapperThrowsException(mapper, false);
    }

    @Test
    public void testMapperWhereSetPropertyToTargetThrowsException() {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        true,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected void setPropertyToTarget(Target target, InnerTarget targetProperty) throws Exception {
                        throw new Exception();
                    }

                    @Override
                    protected InnerTarget convert(InnerSource sourceProperty) {
                        return sourceProperty.createInnerTarget();
                    }
                };

        testMapperThrowsException(mapper, false);
    }

    @Test
    public void testMapperWhereGetPropertyToTargetThrowsException() {
        SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper =
                new SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget>(
                        false,
                        getInnerMapper()
                ) {
                    @Override
                    protected InnerSource getPropertyFromSource(Source source) {
                        return source.getSourceProperty();
                    }

                    @Override
                    protected InnerTarget getPropertyFromTarget(Target target) throws Exception {
                        throw new Exception();
                    }
                };

        testMapperThrowsException(mapper, true);
    }

    private Mapper<InnerSource, InnerTarget> getInnerMapper() {
        return (source, target) -> target.setTargetProperty(String.valueOf(source.getSourceProperty()));
    }

    private void testMapperWorksCorrectly(
            SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper,
            boolean setTargetPropertyToNewInnerTarget
    ) throws Exception {
        Target target = createSourceAndTargetThenMap(mapper, setTargetPropertyToNewInnerTarget);
        assertEquals("10", target.getTargetProperty().getTargetProperty());
    }

    private void testMapperThrowsUnsupportedOperationException(
            SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper,
            boolean setTargetPropertyToNewInnerTarget
    ) {
        testMapperThrowsException(mapper, setTargetPropertyToNewInnerTarget, UnsupportedOperationException.class);
    }

    private void testMapperThrowsException(
            SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper,
            boolean setTargetPropertyToNewInnerTarget
    ) {
        testMapperThrowsException(mapper, setTargetPropertyToNewInnerTarget, Exception.class);
    }

    private <T extends Exception> void testMapperThrowsException(
            SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper,
            boolean setTargetPropertyToNewInnerTarget,
            Class<T> expectedType
    ) {
        assertThrows(expectedType, () -> createSourceAndTargetThenMap(mapper, setTargetPropertyToNewInnerTarget));
    }

    private Target createSourceAndTargetThenMap(
            SourceToTargetObjectPropertyMapper<Source, InnerSource, Target, InnerTarget> mapper,
            boolean setTargetPropertyToNewInnerTarget
    ) throws Exception {
        Source source = new Source(new InnerSource(10));
        Target target = new Target();

        if (setTargetPropertyToNewInnerTarget) {
            target.setTargetProperty(new InnerTarget());
        }

        mapper.map(source, target);

        return target;
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

        public InnerTarget createInnerTarget() {
            return new InnerTarget();
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