package com.swipejobs.test.matchingengine;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.swipejobs.test.model.Job;
import com.swipejobs.test.model.JobSearchAddress;
import com.swipejobs.test.model.Worker;
import com.swipejobs.test.util.DistanceCalculator;

public class Conditions {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(Conditions.class);
	
	public Conditions() {
	}
	
	/**
	 * Implementation for Driving License Condition
	 * 
	 * This implementation matches the condition where worker's driving license property
	 * is matched with job's requirement.
	 * 
	 * Filters the job that don't match the condition
	 * 
	 * return list of matching jobs 
	 * 
	 */
	public static final Condition DrivingLicence = new Condition() {

		@Override
		public List<Job> apply(Worker worker, List<Job> jobs) {
			List<Job> matchedJobs = jobs.stream().filter(job -> job.isDriverLicenseRequired() == worker.isHasDriversLicense())
					.collect(Collectors.toList());
			LOGGER.info("Number of matched jobs after Driving License Requirement are {}", matchedJobs.size());
			return matchedJobs;
		}
	};
	
	/**
	 * This implementation matches the worker's skill with Job Title
	 * 
	 * Filters the job that don't match the skills
	 * 
	 * return list of matching jobs 
	 *
	 */
	public static final Condition Skills = new Condition() {
		@Override
		public List<Job> apply(Worker worker, List<Job> jobs) {

			List<Job> matchedJobs = jobs.stream().filter(job -> worker.getSkills().contains(job.getJobTitle()))
					.collect(Collectors.toList());
			LOGGER.info("Number of matched jobs after JobTitle with Skills are {}", matchedJobs.size());
			return matchedJobs;
		}
	};
	
	/**
	 * Implementation for Required Certificates Condition.
	 * 
	 * This Criteria finds if the Worker has any of mandatory
	 * certification required for picking the Job.
	 * 
	 * Filtered jobs are then ordered based upon matching jobs count
	 * 
	 * return list of matching jobs 
	 */
	public static final Condition Certificates = new Condition() {

		class JobMatchingCertificateCount {
			private Job job;
			private long count;

			public JobMatchingCertificateCount(Job job, long count) {
				this.job = job;
				this.count = count;
			}
		}

		@Override
		public List<Job> apply(Worker worker, List<Job> jobs) {
			List<Job> matchedJobs = jobs.stream().map(
					job -> {
						long certCount = job.getRequiredCertificates().stream().filter(
								rc -> worker.getCertificates().contains(rc)).count();
						return new JobMatchingCertificateCount(job, certCount);
					})
					// filter records that don't have any matching certificates
					.filter(mr -> mr.count != 0)
					// reverse sorted on the base of matching certificate count
					.sorted((mr1, mr2) -> Long.compare(mr2.count, mr1.count))
					// map to Jobs list
					.map(m -> m.job).collect(Collectors.toList());
			LOGGER.info("Number of matched jobs after Required Certificate check are {}", matchedJobs.size());
			return matchedJobs;
		}
	};
	
	/**
	 * Implementation for Distance Condition.
	 * 
	 * This implementation finds the distance between the Job location and workers jobAddress preference.
	 * It filters the jobs that are out of range.
	 * 
	 * return list of matching jobs 
	 */
	public static final Condition Distance = new Condition() {

		@Override
		public List<Job> apply(Worker worker, List<Job> jobs) {
			JobSearchAddress workerAddress = worker.getJobSearchAddress();
			
			double workerLatitude = Double.valueOf(workerAddress.getLatitude());
			double workerLongitude = Double.valueOf(workerAddress.getLongitude());
			
			int maxJobDistance = workerAddress.getMaxJobDistance();
			final String unit = workerAddress.getUnit().equalsIgnoreCase("KM") ? "K" : "M";

			LOGGER.info("Max Allowed distance by worker is " + maxJobDistance + " " + unit);
			List<Job> matchedJobs = jobs.stream().filter(j -> {
				if (j.getLocation() == null) {
					return false;
				}
				Double jobLatitude = Double.valueOf(j.getLocation().getLatitude());
				Double jobLongitude = Double.valueOf(j.getLocation().getLongitude());
				double calulatedDistance = DistanceCalculator.distance(workerLatitude, workerLongitude,
						jobLatitude, jobLongitude,
						unit);
				return calulatedDistance <= maxJobDistance;
			}).collect(Collectors.toList());

			LOGGER.info("Number of matched jobs after filtering by maxJobDistance are {}", matchedJobs.size());
			return matchedJobs;
		}
	};
}
