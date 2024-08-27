/*
 * Copyright 2014-2023 the original author or authors.
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
package io.openleap.logging;

import static io.openleap.LoggingCategories.BOOT;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.sift.Discriminator;
import io.openleap.tenancy.TenantHolder;

/**
 * A TenantDiscriminator is a Logback extension to resolve the current Tenant and integrate it into Logback ecosystem.
 *
 * @author Heiko Scherrer
 */
public class TenantDiscriminator implements Discriminator<ILoggingEvent> {

    private boolean started;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDiscriminatingValue(ILoggingEvent iLoggingEvent) {
        return TenantHolder.getCurrentTenant() == null ? BOOT : TenantHolder.getCurrentTenant();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getKey() {
        return "Tenant";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        started = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {
        started = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStarted() {
        return started;
    }
}
