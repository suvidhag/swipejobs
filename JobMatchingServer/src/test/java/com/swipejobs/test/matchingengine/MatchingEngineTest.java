package com.swipejobs.test.matchingengine;

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

import com.swipejobs.test.config.Configuration;
import com.swipejobs.test.model.Job;
import com.swipejobs.test.model.JobSearchAddress;
import com.swipejobs.test.model.Location;
import com.swipejobs.test.model.Worker;

@RunWith(MockitoJUnitRunner.class)
public class MatchingEngineTest {

	@Mock
	Configuration configuration;
	
	@InjectMocks
	MatchingEngine matchingEngine = new MatchingEngine();
	
	private Worker w = new Worker();

	@Before
	public void setUp() {
	}

	@Test
	public void testFindBestMatchingJobs_ByMatchingCriteriaLicenseAll() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.LICENSE));
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		j1.setDriverLicenseRequired(true);
		j2.setDriverLicenseRequired(true);
		w.setHasDriversLicense(true);

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 2);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
		assertTrue(matchingJobs.get(1).getJobId().equals("J2"));
	}

	@Test
	public void testFindBestMatchingJobs_FilterByMatchingCriteriaLicense() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.LICENSE));
		w.setHasDriversLicense(true);
		
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		j1.setDriverLicenseRequired(true);
		j2.setDriverLicenseRequired(false);

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 1);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
	}

	
	@Test
	public void testFindBestMatchingJobs_ByMatchingCriteriaSkillsAll() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.SKILLS));
		w.setSkills(Arrays.asList("Skill 1", "Skill 2"));
		
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		j1.setJobTitle("Skill 1");
		j2.setJobTitle("Skill 2");

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 2);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
		assertTrue(matchingJobs.get(1).getJobId().equals("J2"));
	}

	@Test
	public void testFindBestMatchingJobs_FilterByMatchingCriteriaSkills() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.SKILLS));
		w.setSkills(Arrays.asList("Skill 1"));
		
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		j1.setJobTitle("Skill 1");
		j2.setJobTitle("Skill 2");

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 1);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
	}

	@Test
	public void testFindBestMatchingJobs_ByMatchingCriteriaLicenseSkills() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.LICENSE, ConditionType.SKILLS));
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		j1.setDriverLicenseRequired(true);
		j1.setJobTitle("Skill 1");
		j2.setDriverLicenseRequired(true);
		j2.setJobTitle("Skill 2");

		w.setHasDriversLicense(true);
		w.setSkills(Arrays.asList("Skill 1"));

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 1);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
	}
	
	@Test
	public void testFindBestMatchingJobs_ByMatchingCriteriaRequiredCertificatesAll() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.REQD_CERTIFICATES));
		
		w.setCertificates(Arrays.asList("Certificate A", "Certificate B", "Certificate C"));
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		j1.setRequiredCertificates(Arrays.asList("Certificate A", "Certificate B"));
		j2.setRequiredCertificates(Arrays.asList("Certificate B", "Certificate C"));

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 2);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
		assertTrue(matchingJobs.get(1).getJobId().equals("J2"));
	}

	@Test
	public void testFindBestMatchingJobs_OrderByMatchingReqdCertificates() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.REQD_CERTIFICATES));

		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		j1.setRequiredCertificates(Arrays.asList("Certificate A", "Certificate E"));
		j2.setRequiredCertificates(Arrays.asList("Certificate A", "Certificate B"));

		w.setCertificates(Arrays.asList("Certificate A", "Certificate B"));

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 2);
		assertTrue(matchingJobs.get(0).getJobId().equals("J2"));
		assertTrue(matchingJobs.get(1).getJobId().equals("J1"));
	}

	@Test
	public void testFindBestMatchingJobs_FilterByMatchingCriteriaRequiredCertificates() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.REQD_CERTIFICATES));
		
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");

		j1.setRequiredCertificates(Arrays.asList("Certificate A", "Certificate B"));
		j2.setRequiredCertificates(Arrays.asList("Certificate B", "Certificate C"));
		w.setCertificates(Arrays.asList("Certificate A"));

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 1);
	}

	@Test
	public void testFindBestMatchingJobs_WithLicenseSkillsCertificatesAll() {
		Mockito.when(configuration.getConditions()).thenReturn(
				Arrays.asList(ConditionType.LICENSE, ConditionType.SKILLS, ConditionType.REQD_CERTIFICATES));

		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");


		w.setCertificates(Arrays.asList("Certificate A", "Certificate B", "Certificate C"));
		j1.setRequiredCertificates(Arrays.asList("Certificate A", "Certificate B"));
		j2.setRequiredCertificates(Arrays.asList("Certificate B", "Certificate C"));

		w.setHasDriversLicense(true);
		j1.setDriverLicenseRequired(true);
		j2.setDriverLicenseRequired(true);

		w.setSkills(Arrays.asList("Skill 1", "Skill 2"));
		j1.setJobTitle("Skill 1");
		j2.setJobTitle("Skill 2");

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 2);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
		assertTrue(matchingJobs.get(1).getJobId().equals("J2"));
	}

	@Test
	public void testFindBestMatchingJobs_FilterByLicenseSkillsCertificates() {
		Mockito.when(configuration.getConditions()).thenReturn(
				Arrays.asList(ConditionType.LICENSE, ConditionType.SKILLS, ConditionType.REQD_CERTIFICATES));

		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");

		w.setCertificates(Arrays.asList("Certificate A", "Certificate B"));
		j1.setRequiredCertificates(Arrays.asList("Certificate A", "Certificate B"));
		j2.setRequiredCertificates(Arrays.asList("Certificate C"));

		w.setHasDriversLicense(true);
		j1.setDriverLicenseRequired(true);
		j2.setDriverLicenseRequired(true);

		w.setSkills(Arrays.asList("Skill 1", "Skill 2"));
		j1.setJobTitle("Skill 1");
		j2.setJobTitle("Skill 2");

		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));

		assertTrue(matchingJobs.size() == 1);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
	}
	
	@Test
	public void testFindBestMatchingJobs_ByMatchingCriteriaDistance() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.DISTANCE));

		//distance between the points is near about 420km
		JobSearchAddress add = new JobSearchAddress();
		add.setLatitude("32.9697");
		add.setLongitude("-96.80322");
		add.setMaxJobDistance(500);
		add.setUnit("km");
		w.setJobSearchAddress(add);
		
		Job j1 = new Job();
		Job j2 = new Job();
		j1.setJobId("J1");
		j2.setJobId("J2");
		
		Location loc = new Location();
		loc.setLatitude("29.46786");
		loc.setLongitude("-98.53506");
		j1.setLocation(loc);
		
		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1, j2 }));
		assertTrue(matchingJobs.size() == 1);
		assertTrue(matchingJobs.get(0).getJobId().equals("J1"));
	}
	
	@Test
	public void testFindBestMatchingJobs_FilterByMatchingCriteriaDistance() {
		Mockito.when(configuration.getConditions()).thenReturn(Arrays.asList(ConditionType.DISTANCE));

		JobSearchAddress add = new JobSearchAddress();
		add.setLatitude("32.9697");
		add.setLongitude("-96.80322");
		add.setMaxJobDistance(400);
		add.setUnit("km");
		w.setJobSearchAddress(add);
		
		Job j1 = new Job();
		j1.setJobId("J1");

		Location loc = new Location();
		loc.setLatitude("29.46786");
		loc.setLongitude("-98.53506");
		j1.setLocation(loc);
		
		//distance between the points is more than 400km, so job location is out of range
		List<Job> matchingJobs = matchingEngine.findBestMatchingJobs(w, Arrays.asList(new Job[] { j1 }));
		assertTrue(matchingJobs.size() == 0);
	}
}
