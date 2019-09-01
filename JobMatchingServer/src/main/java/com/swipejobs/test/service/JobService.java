package com.swipejobs.test.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.swipejobs.test.config.Configuration;
import com.swipejobs.test.model.Job;

@Component
public class JobService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobService.class);

    @Autowired
    private Configuration configuration;

	@Autowired
	private RestTemplate restTemplate;
	
	public APIResult<List<Job>> getJobs() {
		APIResult<List<Job>> result = null;
		try {
			ResponseEntity<Job[]> response = restTemplate.getForEntity(configuration.getJobResourceUrl(), Job[].class);
			if (null == response) {
                LOGGER.error("Failed to get Response from Jobs service");
                result = new APIResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
            }
			if (response.getStatusCode() == HttpStatus.OK) {
				List<Job> jobs =  Arrays.asList(response.getBody());
				if(jobs.isEmpty()) {
					result = new APIResult<>(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
				} else {
					result = new APIResult<>(jobs);
				}
			} else {
				result = new APIResult<>(response.getStatusCode(), "Unknown Error");
			}
		} catch (Exception e) {
			LOGGER.error("Exception while trying to get jobs  from JobService " + e.getMessage());
			result = new APIResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}
}
