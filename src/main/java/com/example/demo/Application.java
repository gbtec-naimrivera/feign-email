package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableFeignClients
@EnableJpaRepositories(basePackages = "com.example.demo.repository")
@EnableElasticsearchRepositories(basePackages = "com.example.demo.repositoryElastic")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
