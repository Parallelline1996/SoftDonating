package com.softdonating.domain;

import java.util.Set;

public class User {
	private Integer userId;
    private String code;
    private String name;
    private String photo;
    private String faculty;
    private String grade;
    private String phoneNumber;
    private Set<Books> books;
	
    public User() {
	}
    public User(Integer userId, String code, String name, String photo, String faculty, String grade,
			String phoneNumber, Set<Books> books) {
		super();
		this.userId = userId;
		this.code = code;
		this.name = name;
		this.photo = photo;
		this.faculty = faculty;
		this.grade = grade;
		this.phoneNumber = phoneNumber;
		this.books = books;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", code=" + code + ", name=" + name + ", photo=" + photo + ", faculty="
				+ faculty + ", grade=" + grade + ", phoneNumber=" + phoneNumber + ", books=" + books + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((faculty == null) ? 0 : faculty.hashCode());
		result = prime * result + ((grade == null) ? 0 : grade.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
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
		User other = (User) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (faculty == null) {
			if (other.faculty != null)
				return false;
		} else if (!faculty.equals(other.faculty))
			return false;
		if (grade == null) {
			if (other.grade != null)
				return false;
		} else if (!grade.equals(other.grade))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getFaculty() {
		return faculty;
	}
	public void setFaculty(String faculty) {
		this.faculty = faculty;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Set<Books> getBooks() {
		return books;
	}
	public void setBooks(Set<Books> books) {
		this.books = books;
	}
    
   
	
   
    
}
