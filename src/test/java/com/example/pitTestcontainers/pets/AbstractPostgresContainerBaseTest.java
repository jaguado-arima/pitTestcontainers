package com.example.pitTestcontainers.pets;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractPostgresContainerBaseTest {
    static final PostgreSQLContainer POSTGRE_SQL_CONTAINER;

    static {
        System.err.println("--------- Creating and starting PostresSQLContainer manually");
        POSTGRE_SQL_CONTAINER = new PostgreSQLContainer("postgres");
        POSTGRE_SQL_CONTAINER.start();
    }

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        System.err.println("--------- Dynamic property registry "+POSTGRE_SQL_CONTAINER.getJdbcUrl()+" ("+POSTGRE_SQL_CONTAINER.isRunning()+")");
        registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
    }
}
