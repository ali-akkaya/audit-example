package me.aliakkaya.auditexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AuditExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuditExampleApplication.class, args);
	}

}
