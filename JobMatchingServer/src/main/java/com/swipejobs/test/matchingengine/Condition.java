/**
 * 
 */
package com.swipejobs.test.matchingengine;

import java.util.List;

import com.swipejobs.test.model.Job;
import com.swipejobs.test.model.Worker;

@FunctionalInterface
public interface Condition {
	public List<Job> apply(Worker worker, List<Job> jobs);
}
