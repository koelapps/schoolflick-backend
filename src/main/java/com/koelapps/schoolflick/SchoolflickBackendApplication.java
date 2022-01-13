package com.koelapps.schoolflick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.koelapps.schoolflick"})
public class SchoolflickBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolflickBackendApplication.class, args);
	}


}
