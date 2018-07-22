package com.doctusoft.bff;

public class BookDeleteEvent {

	public String id;

	public BookDeleteEvent() {

	}
	
	public BookDeleteEvent(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
