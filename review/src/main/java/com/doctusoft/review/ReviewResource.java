package com.doctusoft.review;

import org.springframework.hateoas.ResourceSupport;

public class ReviewResource extends ResourceSupport {

	private String bookId;
	
	private String text;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
