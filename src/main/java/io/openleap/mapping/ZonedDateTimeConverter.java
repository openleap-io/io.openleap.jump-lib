/*
 * Copyright 2015-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.openleap.mapping;

import com.github.dozermapper.core.DozerConverter;

import java.time.ZonedDateTime;

/**
 * A ZonedDateTimeConverter is a Dozer converter to recognize java.time.ZonedDateTime types. No conversion is applied,
 * but a type converter must be configured within Dozer, to accept Java 1.8 types.
 * As soon as these types are included into Dozer this implementation can be deleted.
 *
 * @author <a href="mailto:admin@ice3ider.de">Ice3ider</a>
 * @author Heiko Scherrer
 */
public class ZonedDateTimeConverter extends DozerConverter<ZonedDateTime, ZonedDateTime> {

    /**
     * {@inheritDoc}
     */
    public ZonedDateTimeConverter() {
        super(ZonedDateTime.class, ZonedDateTime.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTime convertTo(ZonedDateTime source, ZonedDateTime destination) {
        return source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTime convertFrom(ZonedDateTime source, ZonedDateTime destination) {
        return convertTo(source, destination);
    }
}
