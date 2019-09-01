package com.swipejobs.test.service;

import static org.junit.Assert.assertTrue;

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
import com.swipejobs.test.model.Worker;


@RunWith(MockitoJUnitRunner.class)
public class WorkerServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@Mock
	private Configuration configuration;

	@InjectMocks
	private WorkerService workerService = new WorkerService();
	
	private final static String VALID_WORKER_ID = "1";
	private final static String INVALID_WORKER_ID = "ABC";

	@Test
	public void testGetWorkerOk() {
		Worker w = new Worker();
		w.setUserId(VALID_WORKER_ID);
		ResponseEntity<Worker[]> entity = new ResponseEntity<>(new Worker[] { w }, HttpStatus.OK);
		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Matchers.eq(Worker[].class)))
			.thenReturn(entity);

		APIResult<Worker> result = workerService.getWorker(VALID_WORKER_ID);
		assertTrue(result.getErrorMessage().equals(APIResult.OK));
	}
	
	@Test
	public void testGetWorkerBadRequest() {
		Worker w = new Worker();
		w.setUserId(VALID_WORKER_ID);

		ResponseEntity<Worker[]> entity = new ResponseEntity<>(new Worker[] { w }, HttpStatus.OK);
		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Matchers.eq(Worker[].class)))
			.thenReturn(entity);

		APIResult<Worker> result = workerService.getWorker(INVALID_WORKER_ID);


		assertTrue(result.getHttpStatus() == HttpStatus.BAD_REQUEST);
		assertTrue(result.getErrorMessage().equals(INVALID_WORKER_ID + " does not exist."));
	}
	
	@Test
	public void testGetWorkerThrowException() {
		Mockito.when(restTemplate.getForEntity(Mockito.anyString(), Matchers.eq(Worker[].class)))
				.thenThrow(new RuntimeException("Exception while trying to get worker from WorkerService"));

		APIResult<Worker> result = workerService.getWorker(VALID_WORKER_ID);

		assertTrue(result.getHttpStatus() == HttpStatus.INTERNAL_SERVER_ERROR);
		assertTrue(result.getErrorMessage().equals("Exception while trying to get worker from WorkerService"));
	}

}
