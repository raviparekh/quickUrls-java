package com.raviparekh.quickUrls.config;

import com.raviparekh.quickUrls.repositories.JdbcURLDao;
import com.raviparekh.quickUrls.repositories.URLDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.sql.DataSource;

@Configuration
@ComponentScan(basePackages = "com.raviparekh.quickUrls.repositories")
public class RepositoryConfig {

    @Bean
    public DataSource getDataSource() {
        // TODO: CHANGE ME
        // TODO: currently in memory db and single connection, change db, use connection pool and use property/yaml/env to get db details
        DriverManagerDataSource dataSource = new SingleConnectionDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:tcp://localhost/~/test");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean
    public JdbcTemplate getJdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(getDataSource());
        return jdbcTemplate;
    }

    @Bean
    public URLDAO getURLDao() {
        return new JdbcURLDao();
    }
}
