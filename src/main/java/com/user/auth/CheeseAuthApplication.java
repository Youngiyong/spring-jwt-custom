package com.user.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.Date;
import java.util.TimeZone;

@EntityScan(basePackages = {"com.cheese.domain"})
@EnableJpaRepositories(basePackages = {"com.cheese.domain"})
@SpringBootApplication
public class CheeseAuthApplication {

	@PostConstruct
	public void started() {
		System.out.println("현재시각: " + new Date());
		TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("UTC")));
		System.out.println("현재시각: " + new Date());
	}

	public static void main(String[] args) {
		SpringApplication.run(CheeseAuthApplication.class, args);
	}

}
