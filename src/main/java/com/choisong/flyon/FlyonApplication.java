package com.choisong.flyon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
@ConfigurationPropertiesScan
public class FlyonApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlyonApplication.class, args);
    }

}
