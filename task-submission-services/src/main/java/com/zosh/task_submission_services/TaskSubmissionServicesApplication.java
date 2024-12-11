package com.zosh.task_submission_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class TaskSubmissionServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskSubmissionServicesApplication.class, args);
	}

}
