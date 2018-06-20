package com.softdonating.domain;

import java.util.Date;

public class Donate {
    private Integer donateId;
    private Integer userId;
    private Integer bookId;
    private Integer status;
    private Date donateTime;
	
    public Donate() {
	}
    public Donate(Integer donateId, Integer userId, Integer bookId, Integer status, Date donateTime) {
		super();
		this.donateId = donateId;
		this.userId = userId;
		this.bookId = bookId;
		this.status = status;
		this.donateTime = donateTime;
	}
	@Override
	public String toString() {
		return "Donate [donateId=" + donateId + ", userId=" + userId + ", bookId=" + bookId + ", status=" + status
				+ ", donateTime=" + donateTime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((donateId == null) ? 0 : donateId.hashCode());
		result = prime * result + ((donateTime == null) ? 0 : donateTime.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		Donate other = (Donate) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (donateId == null) {
			if (other.donateId != null)
				return false;
		} else if (!donateId.equals(other.donateId))
			return false;
		if (donateTime == null) {
			if (other.donateTime != null)
				return false;
		} else if (!donateTime.equals(other.donateTime))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	public Integer getDonateId() {
		return donateId;
	}
	public void setDonateId(Integer donateId) {
		this.donateId = donateId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getDonateTime() {
		return donateTime;
	}
	public void setDonateTime(Date donateTime) {
		this.donateTime = donateTime;
	}
	
    
}
