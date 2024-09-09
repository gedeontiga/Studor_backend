package com.studor.orientation_student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OrientationStudentApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(OrientationStudentApplication.class, args);
	}
}
