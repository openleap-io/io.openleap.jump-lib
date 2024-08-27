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
package io.openleap.i18n;

import org.springframework.context.i18n.LocaleContextHolder;

/**
 * In contrast to the {@code AbstractTranslator} an AbstractSpringTranslator is more flexible in the resolution of Locales. Instead of
 * relying on the platform default Locale, this class uses Spring's {@link LocaleContextHolder} to resolve the Locale from the current
 * thread.
 *
 * @author Heiko Scherrer
 */
public abstract class AbstractSpringTranslator extends AbstractTranslator {

    /**
     * {@inheritDoc}
     *
     * Use Spring's LocaleContextHolder to retrieve the current Locale.
     * @see LocaleContextHolder
     */
    @Override
    public String translate(String key, Object... objects) {
        return getMessageSource().getMessage(key,  objects == null ? new Object[0] : objects, LocaleContextHolder.getLocale());
    }
}
