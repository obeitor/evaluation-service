package com.evaluation.service.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {
                "com.evaluation.service.**.repository",
        },
        transactionManagerRef = "transactionManager",
        entityManagerFactoryRef = "entityManagerFactory"
)
public class DatabaseConfiguration {
    @Value("${database.url:jdbc:mysql://localhost:3306/eval?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC}")
    private String databaseUrl;
    @Value("${database.username:root}")
    private String databaseUsername;

    @Value("${database.password:}")
    private String databasePassword;

    @Value("${atabase.driver:com.mysql.jdbc.Driver}")
    private String databaseDriver;

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dataEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();

        factoryBean.setDataSource(dataSource());
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factoryBean.setJpaPropertyMap(additionalProperties());
        factoryBean.setPackagesToScan(
                "com.evaluation.service.**.models"
        );

        return factoryBean;
    }

    @Primary
    @Bean
    public DataSource dataSource() {

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(databaseUsername);
        hikariConfig.setJdbcUrl(databaseUrl);
        hikariConfig.setPassword(databasePassword);
        hikariConfig.setMaximumPoolSize(100);
        hikariConfig.setDriverClassName(databaseDriver);
        hikariConfig.setConnectionTestQuery("SELECT 1");

        return new HikariDataSource(hikariConfig);
    }

    private Map<String, String> additionalProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.physical_naming_strategy", "com.softobt.jpa.helpers.impl.PhysicalNamingStrategyImpl");
        return props;
    }

    @Primary
    @Bean(name = "transactionManager")
    @DependsOn(value = "entityManagerFactory")
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager(dataEntityManagerFactory().getObject());
    }
}
