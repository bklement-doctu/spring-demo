package com.doctusoft.inventory.amqp;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doctusoft.inventory.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookDeleteEventListener implements MessageListener {

	@Autowired
	private InventoryRepository repository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public void onMessage(Message message) {
		try {
			BookDeleteEvent event = mapper.readValue(message.getBody(), BookDeleteEvent.class);
			repository.deleteById(event.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
