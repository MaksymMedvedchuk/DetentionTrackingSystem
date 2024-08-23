package com.detentionsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DetentionApplication {//extends SpringBootServletInitializer  {
	public static void main(String[] args) {
		SpringApplication.run(DetentionApplication.class);
	}

	/*@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		return builder.sources(DetentionApplication.class);
	}*/
}
