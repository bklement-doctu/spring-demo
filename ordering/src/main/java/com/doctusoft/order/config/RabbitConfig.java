package com.doctusoft.order.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.doctusoft.order.amqp.OrderRejectedEventListener;
import com.doctusoft.order.amqp.PaymentEventListener;

@Configuration
public class RabbitConfig {

	private String paymentExchangeName = "payment";
	private String paymentQueueName = "payment-success-ordering";
	
	public static String ORDER_EXCHANGE_NAME = "order";
	private String orderRejectedQueueName = "order-rejected-order";

	@Bean
	public TopicExchange paymentExchange() {
		return new TopicExchange(paymentExchangeName);
	}
	
	@Bean
	public TopicExchange orderExchange() {
		return new TopicExchange(ORDER_EXCHANGE_NAME);
	}
	
	@Bean
	public Queue paymentQueue() {
		return new Queue(paymentQueueName);
	}
	
	@Bean
	public Queue orderRejectedQueue() {
		return new Queue(orderRejectedQueueName);
	}
	
	@Bean
	public Binding paymentBinding(Queue paymentQueue, TopicExchange paymentExchange) {
		return BindingBuilder.bind(paymentQueue).to(paymentExchange).with("success");
	}
	
	@Bean
	public Binding orderBinding(Queue orderRejectedQueue, TopicExchange orderExchange) {
		return BindingBuilder.bind(orderRejectedQueue).to(orderExchange).with("rejected");
	}
	
	@Bean
	public SimpleMessageListenerContainer paymentEventContainer(ConnectionFactory connectionFactory, PaymentEventListener listener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(paymentQueueName);
		container.setMessageListener(listener);
		return container;
	}
	
	@Bean
	public SimpleMessageListenerContainer orderRejectedEventContainer(ConnectionFactory connectionFactory, OrderRejectedEventListener listener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(orderRejectedQueueName);
		container.setMessageListener(listener);
		return container;
	}
	
}
