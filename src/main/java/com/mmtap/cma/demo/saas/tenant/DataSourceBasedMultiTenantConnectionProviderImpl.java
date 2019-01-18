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

import com.mmtap.cma.demo.saas.dao.SynctargeturiResopory;
import com.mmtap.cma.demo.saas.entiy.Synctargeturi;
import com.mmtap.cma.demo.saas.util.DataSourceUtil;
import com.mmtap.cma.demo.saas.util.TenantContextHolder;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


@Configuration
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final Logger LOG = LoggerFactory.getLogger(DataSourceBasedMultiTenantConnectionProviderImpl.class);

    private static final long serialVersionUID = 1L;

    @Autowired
    private DataSource masterDataSource;

    /**
     * Injected MasterTenantRepository to access the tenant information from the master_tenant table
     */
    @Autowired
    private SynctargeturiResopory synctargeturiResopory;

    public static Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();

    @Override
    @Primary
    protected DataSource selectAnyDataSource() {
        LOG.info(">>>>> 获取任意数据源 ：selectAnyDataSource====1");
        if (dataSourcesMtApp.isEmpty()) {
            dataSourcesMtApp.put("Default",masterDataSource);
            List<Synctargeturi> masterTenants = synctargeturiResopory.findAll();
            LOG.info(">>>> selectAnyDataSource() -- Total tenants:" + masterTenants.size());
            for (Synctargeturi masterTenant : masterTenants) {
                Map dbInfo = DataSourceUtil.analysisDataBaseInfo(masterTenant);
                String dataSourceKey = dbInfo.get("insCode").toString();
                dataSourcesMtApp.put(dataSourceKey, DataSourceUtil.createAndConfigureDataSource(dbInfo));
            }
            LOG.info(">>>> selectAnyDataSource() -- Total DataSource:" + dataSourcesMtApp.size());
        }
        return dataSourcesMtApp.values().iterator().next();
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        LOG.info(">>>>> 获取制定ID 数据源 selectDataSource:222===");
        tenantIdentifier = initializeTenantIfLost(tenantIdentifier);

        if (!dataSourcesMtApp.containsKey(tenantIdentifier)) {
            List<Synctargeturi> masterTenants = synctargeturiResopory.findAll();
            LOG.info(">>>> selectDataSource() -- tenant:" + tenantIdentifier + " Total tenants:" + masterTenants.size());
            for (Synctargeturi masterTenant : masterTenants) {
                Map dbInfo = DataSourceUtil.analysisDataBaseInfo(masterTenant);
                String dataSourceKey = dbInfo.get("insCode").toString();
                dataSourcesMtApp.put(dataSourceKey, DataSourceUtil.createAndConfigureDataSource(dbInfo));
            }
        }
        return dataSourcesMtApp.get(tenantIdentifier);
    }

    /**
     * Initialize tenantId based on the logged in user if the tenant Id got lost in after form submission in a user
     * session.
     *
     * @param tenantIdentifier
     * @return tenantIdentifier
     */
    private String initializeTenantIfLost(String tenantIdentifier) {
//        if (TenantContextHolder.getTenant() == null) {
//
//            SecurityContext securityContext = SecurityContextHolder.getContext();
//            Authentication authentication = securityContext.getAuthentication();
//            CustomUserDetails customUserDetails = null;
//            if (authentication != null) {
//                Object principal = authentication.getPrincipal();
//                customUserDetails = principal instanceof CustomUserDetails ? (CustomUserDetails) principal : null;
//            }
//            TenantContextHolder.setTenantId(customUserDetails.getTenant());
//        }

        if (tenantIdentifier != TenantContextHolder.getTenant()) {
            tenantIdentifier = TenantContextHolder.getTenant();
        }
        return tenantIdentifier;
    }
}