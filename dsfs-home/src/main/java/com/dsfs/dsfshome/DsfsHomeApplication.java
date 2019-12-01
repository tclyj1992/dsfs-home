package com.dsfs.dsfshome;


import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.dsfs.dsfshome.mapper")
@SpringBootApplication
public class DsfsHomeApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DsfsHomeApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(DsfsHomeApplication.class);
	}
}
