package com.doctusoft.review;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.status;

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
@RequestMapping("/api/review")
public class ReviewController {

	@Autowired
	private ReviewRepository repository;

	@Autowired
	private ConversionService conversionService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public PagedResources<ReviewResource> list(@PageableDefault Pageable pageable,
			@ApiIgnore PagedResourcesAssembler<Review> assembler) {
		Page<Review> page = repository.findAll(pageable);
		return assembler.toResource(page, b -> conversionService.convert(b, ReviewResource.class));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ReviewResource get(@PathVariable String id) {
		return conversionService.convert(repository.findById(id), ReviewResource.class);
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public PagedResources<ReviewResource> search(@PageableDefault Pageable pageable, @ApiIgnore PagedResourcesAssembler<Review> assembler, @RequestParam String bookId) {
		Page<Review> page = repository.findAllByBookId(pageable, bookId);
		return assembler.toResource(page, b -> conversionService.convert(b, ReviewResource.class));
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> put(@RequestBody ReviewDto dto) {
		Review review = repository.save(conversionService.convert(dto, Review.class));
		return status(HttpStatus.CREATED).header("Location", createLink(review.getId())).build();
	}

	private String createLink(String id) {
		return linkTo(methodOn(ReviewController.class).get(id)).withSelfRel().getHref();
	}

}
