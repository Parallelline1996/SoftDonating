package com.softdonating.request;

import java.sql.Blob;
import java.util.Date;

public class UnconfirmDonateBook {

	private Integer bookId;
    private String isbn;
    private String name;
    private String author;
    private String publisher;
    private String content;
    private Blob photo;
    private Integer donateId;
    private Date donateTime;
	
    public UnconfirmDonateBook() {
	}
    public UnconfirmDonateBook(Integer bookId, String isbn, String name, String author, String publisher,
			String content, Blob photo, Integer donateId, Date donateTime) {
		super();
		this.bookId = bookId;
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.content = content;
		this.photo = photo;
		this.donateId = donateId;
		this.donateTime = donateTime;
	}
	@Override
	public String toString() {
		return "UnconfirmDonateBook [bookId=" + bookId + ", isbn=" + isbn + ", name=" + name + ", author=" + author
				+ ", publisher=" + publisher + ", content=" + content + ", photo=" + photo + ", donateId=" + donateId
				+ ", donateTime=" + donateTime + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((donateId == null) ? 0 : donateId.hashCode());
		result = prime * result + ((donateTime == null) ? 0 : donateTime.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
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
		UnconfirmDonateBook other = (UnconfirmDonateBook) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (bookId == null) {
			if (other.bookId != null)
				return false;
		} else if (!bookId.equals(other.bookId))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
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
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		return true;
	}
	public Integer getBookId() {
		return bookId;
	}
	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Blob getPhoto() {
		return photo;
	}
	public void setPhoto(Blob photo) {
		this.photo = photo;
	}
	public Integer getDonateId() {
		return donateId;
	}
	public void setDonateId(Integer donateId) {
		this.donateId = donateId;
	}
	public Date getDonateTime() {
		return donateTime;
	}
	public void setDonateTime(Date donateTime) {
		this.donateTime = donateTime;
	}
	
    
    
    
}
