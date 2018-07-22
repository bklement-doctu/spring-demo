package com.doctusoft.bff.resource;

import java.util.List;

import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceSupport;

import com.doctusoft.bff.Genre;

public class AggregateResource extends ResourceSupport {
	
	private String author;
	
	private String title;
	
	private Genre genre;
	
	private Boolean inStock;
	
	private PagedResources<ReviewResource> reviews;
	
	public AggregateResource() {
		
	}
	
	public AggregateResource(BookResource book) {
		author = book.getAuthor();
		title = book.getTitle();
		genre = book.getGenre();
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Boolean isInStock() {
		return inStock;
	}

	public void setInStock(Boolean inStock) {
		this.inStock = inStock;
	}

	public PagedResources<ReviewResource> getReviews() {
		return reviews;
	}

	public void setReviews(PagedResources<ReviewResource> reviews) {
		this.reviews = reviews;
	}

}
