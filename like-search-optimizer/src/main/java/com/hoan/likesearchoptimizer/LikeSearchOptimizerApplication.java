package com.hoan.likesearchoptimizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hoan.likesearchoptimizer.search.config.NaverApiProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(NaverApiProperties.class)
public class LikeSearchOptimizerApplication implements CommandLineRunner {

	private final NaverApiProperties configuration;

    public LikeSearchOptimizerApplication(NaverApiProperties configuration) {
        this.configuration = configuration;
    }

    public static void main(String[] args) {
		SpringApplication.run(LikeSearchOptimizerApplication.class, args);
	}

	@Override
	public void run(String... args) {

		Logger logger = LoggerFactory.getLogger(LikeSearchOptimizerApplication.class);

		logger.info("----------------------------------------");
		logger.info("Configuration properties");
		logger.info("   url is {}", configuration.getUrl());
		logger.info("   id is {}", configuration.getId());
		logger.info("   secret is {}", configuration.getSecret());
		logger.info("----------------------------------------");
	}
}
