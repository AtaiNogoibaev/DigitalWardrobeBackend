package com.digiwardrobe.configurations;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LiquibaseConfig.class);

    @Bean
    public SpringLiquibase liquibase(final DataSource dataSource) {
        final SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
        liquibase.setDataSource(dataSource);

        try {
            liquibase.afterPropertiesSet(); // Run Liquibase
        } catch (LiquibaseException e) {
            LOGGER.error("Liquibase migration failed: {}", e.getMessage(), e);
        }

        return liquibase;
    }
}