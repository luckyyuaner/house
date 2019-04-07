package com.yuan.house;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.yuan.house.dao")
public class HouseApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(HouseApplication.class, args);
	}
}
