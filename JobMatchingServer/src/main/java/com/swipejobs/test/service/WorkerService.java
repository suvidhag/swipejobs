package com.swipejobs.test.service;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.swipejobs.test.config.Configuration;
import com.swipejobs.test.model.Worker;

@Component
public class WorkerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WorkerService.class);

	@Autowired
	private Configuration configuration;

	@Autowired
	RestTemplate restTemplate;

	public APIResult<Worker> getWorker(String workerId) {
		APIResult<Worker> result = null;
		try {
			ResponseEntity<Worker[]> workersResponse = restTemplate.getForEntity(configuration.getWorkerResourceUrl(),
					Worker[].class);
			if (workersResponse.getStatusCode() == HttpStatus.OK) {
				// get the first element matching the filter
				Optional<Worker> o = Arrays.asList(workersResponse.getBody()).stream()
						.filter(p -> workerId.equals(p.getUserId())).findFirst();

				if (o.isPresent()) {
					result = new APIResult<>(o.get());
				} else {
					LOGGER.error("Invalid WorkerId " + workerId);
					result = new APIResult<>(HttpStatus.BAD_REQUEST, workerId + " does not exist.");
				}
			} else {
				LOGGER.error("Failed to get proper response from Worker Service. StatusCode={}",
						workersResponse.getStatusCode());
				result = new APIResult<>(workersResponse.getStatusCode(), "Unknown Error");
			}
		} catch (Exception e) {
			LOGGER.error("Exception while trying to get worker from WorkerService" + e.getMessage());
			result = new APIResult<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return result;
	}


}