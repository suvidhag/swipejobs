package com.swipejobs.test.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swipejobs.test.matchingengine.MatchingEngine;
import com.swipejobs.test.model.Job;
import com.swipejobs.test.model.Worker;

@Component
public class JobMatchingService {

	@Autowired
	private MatchingEngine matchingEngine;

	public List<Job> findBestNMatchingJobs(Worker worker, List<Job> jobs, int maxJobSearchResults) {
		List<Job> matchedJobs = matchingEngine.findBestMatchingJobs(worker, jobs);
		// limit the no. of result to configured MaxJobSearchResults
		return matchedJobs.stream().limit(maxJobSearchResults).collect(Collectors.toList());
	}
}
