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

public class SimplePropertyMapperTest {

    @Test
    public void testMapper() {
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