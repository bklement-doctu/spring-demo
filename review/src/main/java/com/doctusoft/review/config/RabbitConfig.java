package com.doctusoft.review.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.doctusoft.review.amqp.BookDeleteEventListener;

@Configuration
public class RabbitConfig {

	private String bookExchangeName = "book";
	private String bookDeleteQueueName = "book-delete-review";
	
	@Bean
	public TopicExchange bookExchange() {
		return new TopicExchange(bookExchangeName);
	}
	
	@Bean
	public Queue bookDeletetQueue() {
		return new Queue(bookDeleteQueueName);
	}
	
	@Bean
	public Binding bookDeleteQueueBinding(Queue bookDeletetQueue, TopicExchange bookExchange) {
		return BindingBuilder.bind(bookDeletetQueue).to(bookExchange).with("delete");
	}
	
	@Bean
	public SimpleMessageListenerContainer wordCreateEventContainer(ConnectionFactory connectionFactory, BookDeleteEventListener listener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(bookDeleteQueueName);
		container.setMessageListener(listener);
		return container;
	}
	
}
