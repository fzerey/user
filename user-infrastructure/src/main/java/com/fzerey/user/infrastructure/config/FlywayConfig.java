package com.fzerey.user.infrastructure.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class FlywayConfig {

    @Autowired
    private Flyway flyway;

    @PostConstruct
    public void migrateFlyway() {
        flyway.migrate();
    }
}
