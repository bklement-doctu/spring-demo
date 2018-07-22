package com.doctusoft.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;
	
	@Column(nullable = false)
	private String bookId;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	private long placedAt;
	
	public Order() {
		
	}
	
	public Order(String bookId, Integer quantity) {
		this.bookId = bookId;
		this.quantity = quantity;
		status = OrderStatus.PENDING;
	}
	
	public String getId() {
		return id;
	}

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}
	
	public long getPlacedAt() {
		return placedAt;
	}

	public void setPlacedAt(long placedAt) {
		this.placedAt = placedAt;
	}

}
