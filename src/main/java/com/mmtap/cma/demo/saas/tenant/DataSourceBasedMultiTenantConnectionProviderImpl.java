
package com.mmtap.cma.demo.saas.tenant;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import java.util.TreeMap;


@Component
@Slf4j
public class DataSourceBasedMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

    private static final long serialVersionUID = 1L;

    @Autowired
    private DataSource dataSource;

    @Autowired ApplicationContext applicationContext;


    public static boolean init = false;  //是否初始化过
    public static Map<String, DataSource> dataSourcesMtApp = new TreeMap<>();


    @PostConstruct
    public void load() {
        dataSourcesMtApp.put(MultiTenantConstants.DEFAULT_TENANT_ID, dataSource);

    }

    @Override
    protected DataSource selectAnyDataSource() {
        return  dataSourcesMtApp.get(MultiTenantConstants.DEFAULT_TENANT_ID);
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        log.info(">>>>> 获取指定数据源 selectDataSource:==="+tenantIdentifier);
        return dataSourcesMtApp.get(tenantIdentifier);
    }

    /**
     * 进行初始化操作
     */
    public void initTenantDataSource(){
        if (!init) {
            TenantDataSource tenantDataSource = applicationContext.getBean(TenantDataSource.class);
            dataSourcesMtApp.putAll(tenantDataSource.getAll());
            init = true;
        }
    }

}