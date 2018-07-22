package com.doctusoft.inventory.converter;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.core.convert.converter.Converter;

import com.doctusoft.inventory.Inventory;
import com.doctusoft.inventory.InventoryController;
import com.doctusoft.inventory.InventoryResource;

public class InventoryToInventoryResourceConverter implements Converter<Inventory, InventoryResource> {

	@Override
	public InventoryResource convert(Inventory entity) {
		InventoryResource resource = new InventoryResource();
		resource.setAvailable(entity.getAvailable());
		resource.setReserved(entity.getReserved());
		resource.add(linkTo(methodOn(InventoryController.class).get(entity.getId())).withSelfRel());
		return resource;
	}

}
