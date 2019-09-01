package com.swipejobs.test.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.swipejobs.test.matchingengine.MatchingEngine;
import com.swipejobs.test.model.Job;
import com.swipejobs.test.model.Worker;

@RunWith(MockitoJUnitRunner.class)
public class JobMatchingServiceTest {

	@Mock
	MatchingEngine matchingEngine;

	@InjectMocks
	JobMatchingService jobMatchingService = new JobMatchingService();

	private Worker w;
	private Job j1;
	private Job j2;

	private List<Job> jobs;
	
	@Before
	public void setUp() {
		w = new Worker();
		j1 = new Job();
		j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		jobs = Arrays.asList(new Job[] { j1, j2 });
		Mockito.when(matchingEngine.findBestMatchingJobs(w, jobs)).thenReturn(Arrays.asList(new Job[] { j1, j2 }));
	}

	@Test
	public void testFindNBestMatchingJobsWithinLimit() {

		List<Job> matchingJobs = jobMatchingService.findBestNMatchingJobs(w, jobs, 3);

		assertTrue(matchingJobs.size() == 2);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
		assertTrue(matchingJobs.get(1).getJobId().equals("J2"));
	}
	
	@Test
	public void testFindNBestMatchingJobsFilterOutOfLimit() {
		List<Job> matchingJobs = jobMatchingService.findBestNMatchingJobs(w, jobs, 1);
		assertTrue(matchingJobs.size() == 1);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));

	}
}
