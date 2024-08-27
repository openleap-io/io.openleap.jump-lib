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
package io.openleap.exception;

/**
 * A PresentationLayerException is used to signal exceptions around the presentation layer. This type of exception might be caught by an AOP
 * aspect.
 *
 * @author Heiko Scherrer
 * @version 1.1
 * @since 0.2
 */
public class PresentationLayerException extends TechnicalRuntimeException {

    /**
     * {@inheritDoc}
     */
    public PresentationLayerException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public PresentationLayerException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public PresentationLayerException(String message, String messageKey) {
        super(message, messageKey);
    }

    /**
     * {@inheritDoc}
     */
    public PresentationLayerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public PresentationLayerException(String message, String messageKey, Throwable cause) {
        super(message, messageKey, cause);
    }
}
