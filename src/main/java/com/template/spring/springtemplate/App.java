package com.template.spring.springtemplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
/**
 * https://gigsterous.github.io/engineering/2017/03/01/spring-boot-4.html
 */
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
