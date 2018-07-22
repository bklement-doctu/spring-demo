package com.doctusoft.book;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.status;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookRepository repository;
	
	@Autowired
	private ConversionService conversionService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public PagedResources<BookResource> list(@PageableDefault Pageable pageable, @ApiIgnore PagedResourcesAssembler<Book> assembler) {
		Page<Book> page = repository.findAll(pageable);
		return assembler.toResource(page, b -> conversionService.convert(b, BookResource.class));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public BookResource get(@PathVariable String id) {
		return conversionService.convert(repository.getOne(id), BookResource.class);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> post(@Valid @RequestBody BookDto dto) {
		Book book = repository.save(conversionService.convert(dto, Book.class));
		return status(HttpStatus.CREATED).header("Location", createLink(book.getId())).build();
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public PagedResources<BookResource> searchByTitle(@PageableDefault Pageable pageable, @ApiIgnore PagedResourcesAssembler<Book> assembler, @RequestParam String titleContains) {
		Page<Book> page = repository.findAllByTitleContainingIgnoreCase(pageable, titleContains);
		return assembler.toResource(page, b -> conversionService.convert(b, BookResource.class));
	}

	private String createLink(String id) {
		return linkTo(methodOn(BookController.class).get(id)).withSelfRel().getHref();
	}
	
}
