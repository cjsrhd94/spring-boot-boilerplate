package com.cjsrhd94.boilerplate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
@SpringBootApplication
public class BoilerplateApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoilerplateApplication.class, args);
	}

}
