package com.msb.easypoi_boot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class EasypoiBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasypoiBootApplication.class, args);
    }

}
