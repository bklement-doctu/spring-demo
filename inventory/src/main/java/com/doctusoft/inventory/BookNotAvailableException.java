package com.doctusoft.inventory;

public class BookNotAvailableException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	private final String bookId;
	
	public BookNotAvailableException(String bookId) {
		super();
		this.bookId = bookId;
	}

	public String getBookId() {
		return bookId;
	}
	
}
