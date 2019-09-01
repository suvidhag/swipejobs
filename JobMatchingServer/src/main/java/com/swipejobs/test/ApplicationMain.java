package com.swipejobs.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApplicationMain {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationMain.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ApplicationMain.class, args);
        LOGGER.info("Job Matching Application started to find best matching jobs...");
    }
    
    @Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
