package com.doctusoft.bff.resource;

import org.springframework.hateoas.ResourceSupport;

public class ReviewResource extends ResourceSupport {

	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
