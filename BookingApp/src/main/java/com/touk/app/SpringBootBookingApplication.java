package com.touk.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class SpringBootBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBookingApplication.class, args);
	}

}
