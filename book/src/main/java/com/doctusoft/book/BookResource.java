package com.doctusoft.book;

import org.springframework.hateoas.ResourceSupport;

public class BookResource extends ResourceSupport {

	private String title;

	private String author;

	private Genre genre;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

}
