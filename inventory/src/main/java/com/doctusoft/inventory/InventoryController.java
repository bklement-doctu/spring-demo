package com.doctusoft.inventory;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

	@Autowired
	private InventoryRepository repository;
	
	@Autowired
	private ConversionService conversionService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public PagedResources<InventoryResource> list(@PageableDefault Pageable pageable, @ApiIgnore PagedResourcesAssembler<Inventory> assembler) {
		Page<Inventory> page = repository.findAll(pageable);
		return assembler.toResource(page, b -> conversionService.convert(b, InventoryResource.class));
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public InventoryResource get(@PathVariable String id) {
		return conversionService.convert(repository.getOne(id), InventoryResource.class);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> put(@PathVariable String id, @RequestParam(required = false) Integer quantity) {
		repository.save(new Inventory(id, quantity));
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
	@RequestMapping(value = "/{id}/reserve", method = RequestMethod.POST)
	@Transactional
	public ResponseEntity<Void> reserve(@PathVariable String id, @RequestParam Integer quantity) {
		Inventory item = repository.findOneAndLock(id);
		if (item == null) {
			throw new EntityNotFoundException("Item not found with id: " + id);
		}
		item.reserve(quantity);
		repository.save(item);
		return ResponseEntity.status(HttpStatus.OK).build();
	}
	
}
