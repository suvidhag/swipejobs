package com.swipejobs.test.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Availability {

	private String title;
	private int dayIndex;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getDayIndex() {
		return dayIndex;
	}

	public void setDayIndex(int dayIndex) {
		this.dayIndex = dayIndex;
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
			Availability rhs = (Availability)obj;
			return new EqualsBuilder().appendSuper(super.equals(obj)).append(dayIndex, rhs.dayIndex).isEquals();
		}
	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(dayIndex);
		return builder.toHashCode();
	}

	@Override
	public String toString() {
		return "Availability{" + "title='" + title + '\'' + ", dayIndex=" + dayIndex + '}';
	}
}
