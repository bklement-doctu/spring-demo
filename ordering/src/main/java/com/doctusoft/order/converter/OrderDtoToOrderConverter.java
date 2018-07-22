package com.doctusoft.order.converter;

import org.springframework.core.convert.converter.Converter;

import com.doctusoft.order.Order;
import com.doctusoft.order.OrderDto;
import com.doctusoft.order.OrderStatus;

public class OrderDtoToOrderConverter implements Converter<OrderDto, Order> {
	
	@Override
	public Order convert(OrderDto dto) {
		Order entity = new Order();
		entity.setBookId(dto.getBookId());
		entity.setQuantity(dto.getQuantity());
		entity.setStatus(OrderStatus.PENDING);
		return entity;
	}

}
