package com.manish.WorkHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WorkHubApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkHubApiGatewayApplication.class, args);
	}

}
