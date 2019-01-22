
package com.mmtap.cma.demo.saas.tenant;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Slf4j
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = { "com.mmtap.cma.demo" },
        entityManagerFactoryRef = "masterEntityManagerFactory",
        transactionManagerRef = "masterTransactionManager"
)
public class MasterDatabaseConfig {

    @Autowired
    DataSourceBasedMultiTenantConnectionProviderImpl dsProvider;

    @Autowired
    TenantIdentifierResolver tenantResolver;

    @Autowired
    private DataSource dataSource;

    @Bean(name = "masterEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean masterEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setDataSource(dataSource);

        em.setPackagesToScan(
                "com.mmtap.cma.demo.entry","com.mmtap.cma.demo.dao.test",
                "com.mmtap.cma.demo.saas.entiy","com.mmtap.cma.demo.saas.dao");
        em.setPersistenceUnitName("masterdb-persistence-unit");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        em.setJpaProperties(hibernateProperties());
        log.info("Setup of masterEntityManagerFactory succeeded.");
        return em;
    }

    @Bean(name = "masterTransactionManager")
    public JpaTransactionManager masterTransactionManager(
            @Qualifier("masterEntityManagerFactory") EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put(AvailableSettings.MULTI_TENANT, MultiTenancyStrategy.DATABASE.name());
        properties.put(AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,dsProvider);
        properties.put(AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,tenantResolver);

        properties.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
        properties.put(org.hibernate.cfg.Environment.SHOW_SQL, true);
//        properties.put(org.hibernate.cfg.Environment.FORMAT_SQL, true);
        properties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "none");
        return properties;
    }
}