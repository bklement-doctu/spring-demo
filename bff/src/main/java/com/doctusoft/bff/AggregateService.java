package com.doctusoft.bff;

import static com.doctusoft.bff.config.RabbitConfig.BOOK_EXCHANGE_NAME;

import java.util.function.Consumer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.PagedResources;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.doctusoft.bff.resource.BookResource;
import com.doctusoft.bff.resource.InventoryResource;
import com.doctusoft.bff.resource.ReviewResource;

@Service
public class AggregateService {

	@Autowired
	private BookClient bookClient;
	
	@Autowired
	private InventoryClient inventoryClient;
	
	@Autowired
	private ReviewClient reviewClient;
	
	@Autowired
	private RabbitTemplate rabbitTemplate; 
	
	@Cacheable("book")
	public BookResource getBookById(String id) {
		System.err.println("GetBook Started");
		long start = System.currentTimeMillis();
		BookResource bookResource = bookClient.get(id);
		System.err.println("GetBook finished in " + (System.currentTimeMillis() - start));
		return bookResource;
	}
	
	@CacheEvict("book")
	public void deleteBookById(String id) {
		rabbitTemplate.convertAndSend(BOOK_EXCHANGE_NAME, "delete", new BookDeleteEvent(id));
	}
	
	public InventoryResource getInventory(String id) {
		return inventoryClient.get(id).execute();
	}
	
	public void getInventory(String id, Consumer<InventoryResource> callback, Consumer<Throwable> errorCallback) {
		inventoryClient.get(id).toObservable().subscribe(r -> callback.accept(r), t -> errorCallback.accept(t));
	}
	
	public void searchReviewsByBookId(String id, Consumer<PagedResources<ReviewResource>> callback, Consumer<Throwable> errorCallback) {
		reviewClient.searchReviewsByBookId(id).toObservable().subscribe(r -> callback.accept(r), t -> errorCallback.accept(t));
	}
	
	@Async
	public void async() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			Thread.sleep(2000);
			System.err.println("Working hard..");
		}
	}
	
}
