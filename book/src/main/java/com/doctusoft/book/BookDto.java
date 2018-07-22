package com.doctusoft.book;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BookDto {

	@NotNull
	@Size(max = 128)
	private String title;
	
	@NotNull
	@Size(max = 128)
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
