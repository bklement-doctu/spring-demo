package com.doctusoft.bff.resource;

import org.springframework.hateoas.ResourceSupport;

public class ReviewResource extends ResourceSupport {

	private String itemId;
	
	private String text;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
