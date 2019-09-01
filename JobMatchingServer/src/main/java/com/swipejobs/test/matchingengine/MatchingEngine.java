package com.swipejobs.test.matchingengine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.swipejobs.test.config.Configuration;
import com.swipejobs.test.model.Job;
import com.swipejobs.test.model.Worker;

@Component
public class MatchingEngine {

	private final Map<ConditionType, Condition> conditionMap = new HashMap<>();
	
	@Autowired
	Configuration config;

	@Autowired
	public MatchingEngine(){
		initConditionsMap();
	}
	
	private void initConditionsMap() {
		conditionMap.put(ConditionType.DISTANCE, Conditions.Distance);
		conditionMap.put(ConditionType.REQD_CERTIFICATES, Conditions.Certificates);
		conditionMap.put(ConditionType.SKILLS, Conditions.Skills);
		conditionMap.put(ConditionType.LICENSE, Conditions.DrivingLicence);
	}

	public List<Job> findBestMatchingJobs(Worker worker, List<Job> jobs) {
		List<Job> matchedJobs = jobs;
		
		/*
		 *  Step 1: Iterate the list of configured conditions in order they are configured  
		 *  Step 2: Apply condition to retrieve the list of matching jobs
		 */
		 
		for (ConditionType c : config.getConditions()) {
			matchedJobs = conditionMap.get(c).apply(worker, matchedJobs);
		}
		return matchedJobs;
	}
}
