package com.doctusoft.book;

import static com.doctusoft.book.Genre.ADVENTURE;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryITest {

	private static final String AUTHOR = "author";
	private static final String TITLE = "title";

	@Autowired
	private BookRepository repository;
	
	@Test
	public void shouldSaveEntity() {
		Book book = getBook();
		String id = repository.save(book).getId();
		Assert.assertNotNull(id);
		book = repository.getOne(id);
		Assert.assertEquals(AUTHOR, book.getAuthor());
		Assert.assertEquals(TITLE, book.getTitle());
		Assert.assertEquals(ADVENTURE, book.getGenre());
	}
	
	private Book getBook() {
		Book book = new Book();
		book.setAuthor(AUTHOR);
		book.setTitle(TITLE);
		book.setGenre(ADVENTURE);
		return book;
	}
	
}
