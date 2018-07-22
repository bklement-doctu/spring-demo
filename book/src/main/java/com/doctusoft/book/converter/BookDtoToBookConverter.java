package com.doctusoft.book.converter;

import org.springframework.core.convert.converter.Converter;

import com.doctusoft.book.Book;
import com.doctusoft.book.BookDto;

public class BookDtoToBookConverter implements Converter<BookDto, Book> {

	@Override
	public Book convert(BookDto dto) {
		 Book book = new Book();
		 book.setAuthor(dto.getAuthor());
		 book.setTitle(dto.getTitle());
		 book.setGenre(dto.getGenre());
		 return book;
	}

}
