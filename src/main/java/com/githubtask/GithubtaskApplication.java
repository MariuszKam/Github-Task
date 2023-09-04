package com.githubtask;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;

@SpringBootApplication
public class GithubtaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubtaskApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
