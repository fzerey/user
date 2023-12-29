package com.fzerey.user.infrastructure.config;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class FlywayConfig {

    private Flyway flyway;

    

    public FlywayConfig(Flyway flyway) {
        this.flyway = flyway;
    }



    @PostConstruct
    public void migrateFlyway() {
        flyway.migrate();
    }
}
