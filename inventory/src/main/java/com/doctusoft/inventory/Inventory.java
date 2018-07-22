package com.doctusoft.inventory;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {

	@Id
	@Column(name = "id", unique = true)
	private String id;
	
	@Column(nullable = false)
	private Integer available;
	
	@Column(nullable = false)
	private Integer reserved;
	
	public Inventory() {
		
	}
	
	public Inventory(String id, Integer available) {
		this.id = id;
		this.available = available;
		reserved = 0;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
	
	public void reserve(Integer quantity) {
		if (quantity > available) {
			throw new BookNotAvailableException(id);
		}
		available -= quantity;
		reserved += quantity;
	}
	
	public void release(Integer quantity) {
		available += quantity;
		reserved -= quantity;
	}
		
}