package com.doctusoft.inventory.amqp;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doctusoft.inventory.Inventory;
import com.doctusoft.inventory.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderEventListener implements MessageListener {

	@Autowired
	private InventoryRepository repository;
	
	@Autowired
	private ObjectMapper mapper;

	@Override
	@Transactional
	public void onMessage(Message message) {
		try {
			OrderRejectedEvent event = mapper.readValue(message.getBody(), OrderRejectedEvent.class);
			Inventory item = repository.findOneAndLock(event.getItemId());
			item.release(event.getQuantity());
			repository.save(item);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}