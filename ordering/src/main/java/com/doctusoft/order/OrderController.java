package com.doctusoft.order;

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
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ConversionService conversionService;
	
	@Autowired
	private OrderService service;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public PagedResources<OrderResource> list(@PageableDefault Pageable pageable, @ApiIgnore PagedResourcesAssembler<Order> assembler) {
		Page<Order> page = repository.findAll(pageable);
		return assembler.toResource(page, b -> conversionService.convert(b, OrderResource.class));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OrderResource get(@PathVariable String id) {
		return conversionService.convert(repository.getOne(id), OrderResource.class);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Void> post(@Valid @RequestBody OrderDto dto) {
		Order order = conversionService.convert(dto, Order.class);
		String id = service.createOrder(order);
		return status(HttpStatus.CREATED).header("Location", createLink(id)).build();
	}
	
	private String createLink(String id) {
		return linkTo(methodOn(OrderController.class).get(id)).withSelfRel().getHref();
	}
	
}
