package com.mcp.osc.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author: KG
 * @description:
 * @date: Created in 09:51 2022/9/21
 * @modified by:
 */
@EnableScheduling
@EnableAsync
@SpringBootApplication(scanBasePackages="com.mcp.osc")
public class OscApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(OscApiApplication.class, args);
    }
}


