package com.doctusoft.inventory.amqp;

public class OrderRejectedEvent {

	private String itemId;
	
	private Integer quantity;
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}
