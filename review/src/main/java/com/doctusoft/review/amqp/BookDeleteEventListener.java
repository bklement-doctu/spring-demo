package com.doctusoft.review.amqp;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.doctusoft.review.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class BookDeleteEventListener implements MessageListener {

	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Override
	public void onMessage(Message message) {
		try {
			BookDeleteEvent event = mapper.readValue(message.getBody(), BookDeleteEvent.class);
			repository.deleteAllByBookId(event.getId());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
