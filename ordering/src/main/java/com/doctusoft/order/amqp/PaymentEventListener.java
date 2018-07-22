package com.doctusoft.order.amqp;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doctusoft.order.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class PaymentEventListener implements MessageListener {
	
	@Autowired
	private OrderService service;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	public void onMessage(Message message) {
		try {
			PaymentSuccessfulEvent event = mapper.readValue(message.getBody(), PaymentSuccessfulEvent.class);
			service.processPayment(event.getOrderId());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
