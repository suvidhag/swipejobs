package com.swipejobs.test.model;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Worker {

    private int rating;

    private Boolean isActive;
    private List<String> certificates;

    private List<String> skills;
    private JobSearchAddress jobSearchAddress;
    private String transportation;
    private boolean hasDriversLicense;

    private Set<Availability> availability;

    private String phone;
    private String email;
    private Name name;

    private int age;
    private String guid;
    private String userId;

    public Worker(){}

    public Worker(String userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public Boolean isActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public List<String> getCertificates() {
        return certificates;
    }

    public void setCertificates(List<String> certificates) {
        this.certificates = certificates;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public JobSearchAddress getJobSearchAddress() {
        return jobSearchAddress;
    }

    public void setJobSearchAddress(JobSearchAddress jobSearchAddress) {
        this.jobSearchAddress = jobSearchAddress;
    }

    public String getTransportation() {
        return transportation;
    }

    public void setTransportation(String transportation) {
        this.transportation = transportation;
    }

    public boolean isHasDriversLicense() {
        return hasDriversLicense;
    }

    public void setHasDriversLicense(boolean hasDriversLicense) {
        this.hasDriversLicense = hasDriversLicense;
    }

    public Set<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(Set<Availability> availability) {
        this.availability = availability;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "rating=" + rating +
                ", isActive=" + isActive +
                ", certificates=" + certificates +
                ", skills=" + skills +
                ", jobSearchAddress=" + jobSearchAddress +
                ", transportation='" + transportation + '\'' +
                ", hasDriversLicense=" + hasDriversLicense +
                ", availability=" + availability +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", name=" + name +
                ", age=" + age +
                ", guid='" + guid + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Worker rhs = (Worker) obj;
		return new EqualsBuilder().appendSuper(super.equals(obj)).append(userId, rhs.userId).isEquals();
	}

    @Override
	public int hashCode() {
    	System.out.println("hascode");

		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(userId);
		return builder.toHashCode();
	}
}
