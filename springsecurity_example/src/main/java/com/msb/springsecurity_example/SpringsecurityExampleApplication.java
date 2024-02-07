package com.msb.springsecurity_example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan("com.msb.springsecurity_example.mapper")
public class SpringsecurityExampleApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(SpringsecurityExampleApplication.class, args);
		System.out.println("Spring Security Example starts!!!");
	}
}
