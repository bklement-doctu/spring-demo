package com.doctusoft.order.amqp;

import com.doctusoft.order.Order;

public class OrderRejectedEvent {

	private String orderId;
	
	private String bookId;
	
	private Integer quantity;
	
	public OrderRejectedEvent() {
		
	}
	
	public OrderRejectedEvent(Order order) {
		this.orderId = order.getId();
		this.bookId = order.getBookId();
		this.quantity = order.getQuantity();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String itemId) {
		this.bookId = itemId;
	}
	
	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
