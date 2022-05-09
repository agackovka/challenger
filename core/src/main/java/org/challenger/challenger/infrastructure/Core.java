package org.challenger.challenger.infrastructure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@EnableJdbcRepositories
public class Core {

	public static void main(String[] args) {
		SpringApplication.run(Core.class, args);
	}

}
