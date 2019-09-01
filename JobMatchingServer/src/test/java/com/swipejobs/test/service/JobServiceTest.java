package com.swipejobs.test.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.swipejobs.test.config.Configuration;
import com.swipejobs.test.model.Job;


@RunWith(MockitoJUnitRunner.class)
public class JobServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private Configuration configuration;

	@InjectMocks
	private JobService jobService = new JobService();
	
	@Test
	public void testGetJobsOk() {
		Job j = new Job();
		ResponseEntity<Job[]> entity = new ResponseEntity<>(new Job[] { j }, HttpStatus.OK);
		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Matchers.eq(Job[].class)))
			.thenReturn(entity);

		APIResult<List<Job>> result = jobService.getJobs();

		assertTrue(result.getErrorMessage().equals(APIResult.OK));
	}
	
	@Test
	public void testGetJobsNotOK() {
		Job j = new Job();
		ResponseEntity<Job[]> entity = new ResponseEntity<>(new Job[] { j }, HttpStatus.NOT_FOUND);
		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Matchers.eq(Job[].class)))
			.thenReturn(entity);

		APIResult<List<Job>> result = jobService.getJobs();

		assertFalse(result.getErrorMessage().equals(APIResult.OK));
	}
	
	@Test
	public void testGetJobsTemplateThrowException() {
		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Matchers.eq(Job[].class)))
				.thenThrow(new RuntimeException("Exception while trying to get jobs  from JobService "));

		APIResult<List<Job>> result = jobService.getJobs();

		assertTrue(result.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR);
		assertTrue(result.getErrorMessage().equals("Exception while trying to get jobs  from JobService "));
	}

}
