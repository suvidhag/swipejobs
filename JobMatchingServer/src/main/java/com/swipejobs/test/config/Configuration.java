package com.swipejobs.test.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.swipejobs.test.matchingengine.ConditionType;

/**
 * Configuration class defining all the properties used in the application
 */
@Component
public class Configuration {

    // URL of the API which returns all the jobs
    @Value("${jobResourceUrl:http://test.swipejobs.com/api/jobs}")
    private String jobResourceUrl;

    // URL of the API which returns all the workers
    @Value("${workerResourceUrl:http://test.swipejobs.com/api/workers}")
    private String workerResourceUrl;

    // configures how many search results should be returned for each worker
    @Value("${maxJobSearchResults:3}")
    private int maxJobSearchResults;
    
    // configures the matching condition, will be applied in order they are configured
    // Can have values one or more from - LICENSE, REQD_CERTIFICATES, DISTANCE, SKILLS
    @Value("${matching.conditions: LICENSE, REQD_CERTIFICATES, DISTANCE}")
    private List<ConditionType> conditions;
    
    //getter methods for all properties
    public int getMaxJobSearchResults() {
        return maxJobSearchResults;
    }

    public String getJobResourceUrl() {
        return jobResourceUrl;
    }

    public String getWorkerResourceUrl() {
        return workerResourceUrl;
    }

	public List<ConditionType> getConditions() {
		return conditions;
	}

}
