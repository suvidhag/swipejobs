package com.swipejobs.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.swipejobs.test.config.Configuration;
import com.swipejobs.test.model.Job;
import com.swipejobs.test.model.Worker;
import com.swipejobs.test.service.APIResult;
import com.swipejobs.test.service.JobMatchingService;
import com.swipejobs.test.service.JobService;
import com.swipejobs.test.service.WorkerService;

@RestController
public class Controller {

	@Autowired
	private JobMatchingService jobMatcherService;

	@Autowired
	private WorkerService workerService;

	@Autowired
	private JobService jobService;
	
	@Autowired
	private Configuration config;

	/**
	 * API to determine the best matching jobs for a given worker based on workerId.
	 * 
	 * @param workerId
	 * @return List of jobs matching worker's Driver License requirement, location
	 *         preference, skills and required certifications - criteria as specified in configuration
	 */
	@RequestMapping(path = "/matches/{workerId}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<List<Job>> findMatches(@PathVariable(name = "workerId", required = true) String workerId) {

		APIResult<Worker> workerResult = workerService.getWorker(workerId);

		if (!workerResult.getErrorMessage().equals(APIResult.OK)) {
			// something went wrong. cannot progress!
			return ResponseEntity.status(workerResult.getHttpStatus()).build();
		}

		APIResult<List<Job>> jobsResult = jobService.getJobs();

		if (!jobsResult.getErrorMessage().equals(APIResult.OK)) {
			// no Jobs, so return
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

		// find matching jobs
		List<Job> matchingJobs = jobMatcherService.findBestNMatchingJobs(workerResult.getValue(), jobsResult.getValue(), config.getMaxJobSearchResults());

		if (matchingJobs.isEmpty()) {
			// no matching jobs found.
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(matchingJobs);
	}
}

