package com.fzerey.user.host;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "com.fzerey.user")
@EntityScan(basePackages = "com.fzerey.user.domain.model")
@EnableJpaRepositories(basePackages = "com.fzerey.user.infrastructure.repository")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
