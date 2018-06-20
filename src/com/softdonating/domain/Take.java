package com.softdonating.domain;

import java.util.Date;

public class Take {
    private Integer takeId;
    private Integer userId;
    private Integer donateUserId;
    private Integer bookId;
    private Date donateTime;
    private Date takeTime;
	
    public Take() {
	}
    public Take(Integer takeId, Integer userId, Integer donateUserId, Integer bookId, Date donateTime, Date takeTime) {
		super();
		this.takeId = takeId;
		this.userId = userId;
		this.donateUserId = donateUserId;
		this.bookId = bookId;
		this.donateTime = donateTime;
		this.takeTime = takeTime;
	}
	@Override
	public String toString() {
		return "Take [takeId=" + takeId + ", userId=" + userId + ", donateUserId=" + donateUserId + ", bookId=" + bookId
				+ ", donateTime=" + donateTime + ", takeTime=" + takeTime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((donateTime == null) ? 0 : donateTime.hashCode());
		result = prime * result + ((donateUserId == null) ? 0 : donateUserId.hashCode());
		result = prime * result + ((takeId == null) ? 0 : takeId.hashCode());
		result = prime * result + ((takeTime == null) ? 0 : takeTime.hashCode());
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
		Take other = (Take) obj;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (donateTime == null) {
			if (other.donateTime != null)
				return false;
		} else if (!donateTime.equals(other.donateTime))
			return false;
		if (donateUserId == null) {
			if (other.donateUserId != null)
				return false;
		} else if (!donateUserId.equals(other.donateUserId))
			return false;
		if (takeId == null) {
			if (other.takeId != null)
				return false;
		} else if (!takeId.equals(other.takeId))
			return false;
		if (takeTime == null) {
			if (other.takeTime != null)
				return false;
		} else if (!takeTime.equals(other.takeTime))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	public Integer getTakeId() {
		return takeId;
	}
	public void setTakeId(Integer takeId) {
		this.takeId = takeId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getDonateUserId() {
		return donateUserId;
	}
	public void setDonateUserId(Integer donateUserId) {
		this.donateUserId = donateUserId;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public Date getDonateTime() {
		return donateTime;
	}
	public void setDonateTime(Date donateTime) {
		this.donateTime = donateTime;
	}
	public Date getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(Date takeTime) {
		this.takeTime = takeTime;
	}
}
