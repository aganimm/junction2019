package com.junction.vk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class
})
public class JunctionVk {
    public static void main(String[] args) {
        SpringApplication.run(JunctionVk.class, args);
    }
}
