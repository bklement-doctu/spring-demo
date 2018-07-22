package com.doctusoft.bff.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String BOOK_EXCHANGE_NAME = "book";
	
	@Bean
	public TopicExchange bookExchange() {
		return new TopicExchange(BOOK_EXCHANGE_NAME);
	}
	
}
