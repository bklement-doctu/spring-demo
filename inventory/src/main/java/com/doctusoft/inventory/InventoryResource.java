package com.doctusoft.inventory;

import org.springframework.hateoas.ResourceSupport;

public class InventoryResource extends ResourceSupport {

	private Integer available;
	
	private Integer reserved;

	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public Integer getReserved() {
		return reserved;
	}

	public void setReserved(Integer reserved) {
		this.reserved = reserved;
	}

}
