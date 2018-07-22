package com.doctusoft.book;

import static com.doctusoft.book.Genre.ADVENTURE;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.hateoas.Link;

import com.doctusoft.book.Book;
import com.doctusoft.book.BookResource;
import com.doctusoft.book.Genre;
import com.doctusoft.book.converter.BookToBookResourceConverter;

public class BookToBookResourceConverterTest {

	private static final String AUTHOR = "author";
	private static final String TITLE = "title";
	private static final String ID = "id";
	
	private BookToBookResourceConverter underTest = new BookToBookResourceConverter();
	
	@Test
	public void shouldHaveCorrectFieldValuesAndSelfLinkWhenCreatingFromEntity() {
		Book book = Mockito.spy(getEntity());
		Mockito.when(book.getId()).thenReturn(ID);
		BookResource resource = underTest.convert(book);
		Assert.assertEquals(AUTHOR, resource.getAuthor());
		Assert.assertEquals(TITLE, resource.getTitle());
		Assert.assertEquals(ADVENTURE, resource.getGenre());
		Link selfLink = resource.getLink(Link.REL_SELF);
		Assert.assertNotNull(selfLink);
		Assert.assertEquals("/api/book/id", selfLink.getHref());
	}
	
	private Book getEntity() {
		Book entity = new Book();
		entity.setAuthor(AUTHOR);
		entity.setTitle(TITLE);
		entity.setGenre(Genre.ADVENTURE);
		return entity;
	}
	
}
