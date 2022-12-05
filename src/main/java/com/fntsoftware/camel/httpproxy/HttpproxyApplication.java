package com.fntsoftware.camel.httpproxy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("com.fntsoftware.camel.httpproxy")
public class HttpproxyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HttpproxyApplication.class, args);
	}

}
