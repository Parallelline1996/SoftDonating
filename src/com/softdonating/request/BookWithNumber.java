package com.softdonating.request;

public class BookWithNumber {

	private Integer donateId;
	private Integer number;
	
	public BookWithNumber() {
	}
	public BookWithNumber(Integer donateId, Integer number) {
		super();
		this.donateId = donateId;
		this.number = number;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((donateId == null) ? 0 : donateId.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookWithNumber other = (BookWithNumber) obj;
		if (donateId == null) {
			if (other.donateId != null)
				return false;
		} else if (!donateId.equals(other.donateId))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BookWithNumber [donateId=" + donateId + ", number=" + number + "]";
	}
	public Integer getDonateId() {
		return donateId;
	}
	public void setDonateId(Integer donateId) {
		this.donateId = donateId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
	
}
