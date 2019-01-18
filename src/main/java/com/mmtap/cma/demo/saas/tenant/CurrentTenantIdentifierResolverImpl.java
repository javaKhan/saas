/*
 * Copyright 2018 onwards - Sunit Katkar (sunitkatkar@gmail.com)
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
package com.mmtap.cma.demo.saas.tenant;

import com.mmtap.cma.demo.saas.util.TenantContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;

@Slf4j
public class CurrentTenantIdentifierResolverImpl
        implements CurrentTenantIdentifierResolver {

    private static final String DEFAULT_TENANT_ID = "Default";

    @Override
    public String resolveCurrentTenantIdentifier() {

        log.info("解析线程ID："+Thread.currentThread().getId());
        log.info("解析数据源Key");
        Thread t = Thread.currentThread();
        log.info("线程："+t);
        String tenant = TenantContextHolder.getTenant();
        log.info(">>>>> tenant key :"+tenant +"    "+StringUtils.isEmpty(tenant));
        String tenantKey = !StringUtils.isEmpty(tenant) ? tenant : DEFAULT_TENANT_ID;
        log.info("tenant解析结果"+tenantKey);
        return tenantKey;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
