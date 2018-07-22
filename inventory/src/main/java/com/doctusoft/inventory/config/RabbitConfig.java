package com.doctusoft.inventory.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.doctusoft.inventory.amqp.BookDeleteEventListener;
import com.doctusoft.inventory.amqp.OrderEventListener;

@Configuration
public class RabbitConfig {

	private String orderExchangeName = "order";
	private String orderQueueName = "order-rejected-inventory";
	
	private String bookExchangeName = "book";
	private String bookDeleteQueueName = "book-delete-inventory";
	
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
	public SimpleMessageListenerContainer bookDeleteEventContainer(ConnectionFactory connectionFactory, BookDeleteEventListener listener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(bookDeleteQueueName);
		container.setMessageListener(listener);
		return container;
	}
	
	@Bean
	public TopicExchange orderExchange() {
		return new TopicExchange(orderExchangeName);
	}
	
	@Bean
	public Queue orderQueue() {
		return new Queue(orderQueueName);
	}
	
	@Bean
	public Binding orderRejectedQueueBinding(Queue orderQueue, TopicExchange orderExchange) {
		return BindingBuilder.bind(orderQueue).to(orderExchange).with("rejected");
	}
	
	@Bean
	public SimpleMessageListenerContainer orderRejecetedEventContainer(ConnectionFactory connectionFactory, OrderEventListener listener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(orderQueueName);
		container.setMessageListener(listener);
		return container;
	}
	
}
