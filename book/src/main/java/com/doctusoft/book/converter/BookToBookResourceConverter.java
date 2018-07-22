package com.doctusoft.book.converter;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.core.convert.converter.Converter;

import com.doctusoft.book.Book;
import com.doctusoft.book.BookController;
import com.doctusoft.book.BookResource;

public class BookToBookResourceConverter implements Converter<Book, BookResource> {

	@Override
	public BookResource convert(Book entity) {
		 BookResource resource = new BookResource();
		 resource.setAuthor(entity.getAuthor());
		 resource.setTitle(entity.getTitle());
		 resource.setGenre(entity.getGenre());
		 resource.add(linkTo(methodOn(BookController.class).get(entity.getId())).withSelfRel());
		 return resource;
	}

}
