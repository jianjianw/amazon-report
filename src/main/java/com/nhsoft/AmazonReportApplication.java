package com.nhsoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

//@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})


@SpringBootApplication(exclude={JpaRepositoriesAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class AmazonReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmazonReportApplication.class, args);
	}
}
