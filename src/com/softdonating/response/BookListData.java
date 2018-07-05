package com.softdonating.response;

import com.softdonating.domain.Books;

public class BookListData {

	private Integer bookId;
	private String isbn;
	private String author;
	private String name;
    private String publisher;
    private String photo;
    private Integer number;
    private Integer followNumber;
    private boolean weatherLikeThisBook;	
    public BookListData(Books books, boolean weatherLikeThisBook){
		this.bookId = books.getBookId();
		this.isbn = books.getIsbn();
		this.author = books.getAuthor();
		this.name = books.getName();
		this.publisher = books.getPublisher();
		this.photo = books.getPhoto();
		this.number = books.getNumber();
		this.followNumber = books.getFollowNumber();
		this.weatherLikeThisBook = weatherLikeThisBook;
    }
    public BookListData() {
	}
    public BookListData(Integer bookId, String isbn, String author, String name, String publisher, String photo,
			Integer number, Integer followNumber, boolean weatherLikeThisBook) {
		super();
		this.bookId = bookId;
		this.isbn = isbn;
		this.author = author;
		this.name = name;
		this.publisher = publisher;
		this.photo = photo;
		this.number = number;
		this.followNumber = followNumber;
		this.weatherLikeThisBook = weatherLikeThisBook;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((followNumber == null) ? 0 : followNumber.hashCode());
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((number == null) ? 0 : number.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + (weatherLikeThisBook ? 1231 : 1237);
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
		BookListData other = (BookListData) obj;
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
		if (followNumber == null) {
			if (other.followNumber != null)
				return false;
		} else if (!followNumber.equals(other.followNumber))
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
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
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
		if (weatherLikeThisBook != other.weatherLikeThisBook)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "BookListData [bookId=" + bookId + ", isbn=" + isbn + ", author=" + author + ", name=" + name
				+ ", publisher=" + publisher + ", photo=" + photo + ", number=" + number + ", followNumber="
				+ followNumber + ", weatherLikeThisBook=" + weatherLikeThisBook + "]";
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getFollowNumber() {
		return followNumber;
	}
	public void setFollowNumber(Integer followNumber) {
		this.followNumber = followNumber;
	}
	public boolean isWeatherLikeThisBook() {
		return weatherLikeThisBook;
	}
	public void setWeatherLikeThisBook(boolean weatherLikeThisBook) {
		this.weatherLikeThisBook = weatherLikeThisBook;
	}
    
}
