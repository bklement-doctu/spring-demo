package com.doctusoft.order.converter;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.core.convert.converter.Converter;

import com.doctusoft.order.Order;
import com.doctusoft.order.OrderController;
import com.doctusoft.order.OrderResource;

public class OrderToOrderResourceConverter implements Converter<Order, OrderResource> {

	@Override
	public OrderResource convert(Order entity) {
		OrderResource resource = new OrderResource();
		resource.setBookId(entity.getBookId());
		resource.setQuantity(entity.getQuantity());
		resource.setStatus(entity.getStatus());
		resource.add(linkTo(methodOn(OrderController.class).get(entity.getId())).withSelfRel());
		return resource;
	}

}
