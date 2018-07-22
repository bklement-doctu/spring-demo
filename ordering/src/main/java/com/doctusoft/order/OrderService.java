package com.doctusoft.order;

import static com.doctusoft.order.config.RabbitConfig.ORDER_EXCHANGE_NAME;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.doctusoft.order.amqp.OrderRejectedEvent;
import com.netflix.client.ClientException;

@Service
public class OrderService {

	@Autowired
	private InventoryClient inventory;
	
	@Autowired
	private OrderRepository repository;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Scheduled(fixedDelay = 60000)
	public void rejectOldPendingOrders() {
		List<Order> statuses = repository.getAllByStatus(OrderStatus.PENDING);
		statuses.stream().filter(o -> System.currentTimeMillis() - o.getPlacedAt() > 600000).forEach(o -> rejectOrder(o));
	}

	private void rejectOrder(Order order) {
		rabbitTemplate.convertAndSend(ORDER_EXCHANGE_NAME, "rejected", new OrderRejectedEvent(order));
	}
	
	@Retryable(include = { ClientException.class, TimeoutException.class, SocketTimeoutException.class },
			maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 2.0))
	public String createOrder(Order order) {
		inventory.reserve(order.getBookId(), order.getQuantity());
		order.setPlacedAt(System.currentTimeMillis());
		return repository.save(order).getId();
	}
	
	public void processPayment(String id) {
		Order order = repository.getOne(id);
		order.setStatus(OrderStatus.ACCEPTED);
		repository.save(order);
	}
	
}
