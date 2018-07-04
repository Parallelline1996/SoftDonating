package com.softdonating.response;

public class BookDetail {

	private Integer bookId;
    private String isbn;
    private String name;
    private String author;
    private String publisher;
    private String content;
    private String photo;
    private Integer number;
    private boolean weatherLikeThisBook;
	public BookDetail() {
	}
    public BookDetail(Integer bookId, String isbn, String name, String author, String publisher, String content,
			String photo, Integer number, boolean weatherLikeThisBook) {
		super();
		this.bookId = bookId;
		this.isbn = isbn;
		this.name = name;
		this.author = author;
		this.publisher = publisher;
		this.content = content;
		this.photo = photo;
		this.number = number;
		this.weatherLikeThisBook = weatherLikeThisBook;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
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
		BookDetail other = (BookDetail) obj;
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
		return "BookDetail [bookId=" + bookId + ", isbn=" + isbn + ", name=" + name + ", author=" + author
				+ ", publisher=" + publisher + ", content=" + content + ", photo=" + photo + ", number=" + number
				+ ", weatherLikeThisBook=" + weatherLikeThisBook + "]";
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
	public boolean isWeatherLikeThisBook() {
		return weatherLikeThisBook;
	}
	public void setWeatherLikeThisBook(boolean weatherLikeThisBook) {
		this.weatherLikeThisBook = weatherLikeThisBook;
	}
    
}
