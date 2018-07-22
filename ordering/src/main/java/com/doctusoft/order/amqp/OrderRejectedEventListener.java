package com.doctusoft.order.amqp;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doctusoft.order.Order;
import com.doctusoft.order.OrderRepository;
import com.doctusoft.order.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderRejectedEventListener implements MessageListener {
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public void onMessage(Message message) {
		try {
			OrderRejectedEvent event = mapper.readValue(message.getBody(), OrderRejectedEvent.class);
			Order order = repository.getOne(event.getOrderId());
			order.setStatus(OrderStatus.REJECTED);
			repository.save(order);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
