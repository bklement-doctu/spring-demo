package com.doctusoft.book;

import static com.doctusoft.book.Genre.ADVENTURE;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.doctusoft.book.config.RabbitConfig;
import com.doctusoft.book.config.SwaggerConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = BookController.class)
@RunWith(SpringRunner.class)
@ComponentScan(excludeFilters={ @ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=RabbitConfig.class),
		@ComponentScan.Filter(type=FilterType.ASSIGNABLE_TYPE, value=SwaggerConfig.class) })
@EnableSpringDataWebSupport 
public class BookControllerITest {

	private static final String RESOURCE_LOCATION = "http://localhost/api/book/id";
	private static final String ID = "id";
	private static final String AUTHOR = "author";
	private static final String TITLE = "title";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookRepository repository;

	@Mock
	private Page<Book> page;

	@Autowired
	private ObjectMapper mapper;

	@Captor
	private ArgumentCaptor<Book> captor;

	@Test
	public void shouldReturnEntityForGet() throws Exception {
		Book book = getBook();
		given(repository.getOne(ID)).willReturn(book);
		String body = mockMvc.perform(get("/api/book/id")).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		BookResource resource = mapper.readValue(body, BookResource.class);
		Assert.assertEquals(AUTHOR, resource.getAuthor());
		Assert.assertEquals(TITLE, resource.getTitle());
		Assert.assertEquals(ADVENTURE, resource.getGenre());
		Assert.assertEquals(RESOURCE_LOCATION, resource.getLink(Link.REL_SELF).getHref());
		Assert.assertTrue(resource.getLink(Link.REL_SELF).getHref().endsWith("/api/book/id"));
	}

	@Test
	public void shouldReturnNotFoundForMissingEntity() throws Exception {
		given(repository.getOne(ID)).willThrow(EntityNotFoundException.class);
		mockMvc.perform(get("/api/book/id")).andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldSaveEntity() throws Exception {
		Book book = getBook();
		given(repository.save(captor.capture())).willReturn(book);
		String location = mockMvc.perform(post("/api/book").content(getDtoBytes()).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isCreated()).andReturn().getResponse().getHeader("Location");
		Assert.assertEquals(RESOURCE_LOCATION, location);
		Book capture = captor.getValue();
		Assert.assertEquals(AUTHOR, capture.getAuthor());
		Assert.assertEquals(TITLE, capture.getTitle());
	}

	private byte[] getDtoBytes() throws JsonProcessingException {
		BookDto dto = new BookDto();
		dto.setAuthor(AUTHOR);
		dto.setTitle(TITLE);
		dto.setGenre(ADVENTURE);
		return mapper.writeValueAsBytes(dto);
	}
	
	private Book getBook() {
		Book book = Mockito.spy(new Book());
		book.setAuthor(AUTHOR);
		book.setGenre(ADVENTURE);
		book.setTitle(TITLE);
		Mockito.when(book.getId()).thenReturn(ID);
		return book;
	}

}
