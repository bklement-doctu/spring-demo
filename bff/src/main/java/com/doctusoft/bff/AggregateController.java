package com.doctusoft.bff;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;
import static org.springframework.http.ResponseEntity.status;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.doctusoft.bff.resource.AggregateResource;

@RestController
@RequestMapping("/api/aggregate")
@EnableHystrix
public class AggregateController {

	@Autowired
	private AggregateService service;
	
	@RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
	public AggregateResource get(@PathVariable String bookId) throws InterruptedException {
		AggregateResource resource = new AggregateResource(service.getBookById(bookId));
		CountDownLatch latch = new CountDownLatch(2);
		service.getInventory(bookId, ir -> {
			if (ir.getAvailable() > 0) {
				resource.setInStock(true);
			} else {
				resource.setInStock(false);
			}
			latch.countDown();
		}, t -> { 
			t.printStackTrace();
			latch.countDown();
		});
		service.searchReviewsByBookId(bookId, pr -> {
			resource.setReviews(pr);
			latch.countDown();
		}, t -> { 
			t.printStackTrace();
			latch.countDown();
		});
		latch.await(10, TimeUnit.SECONDS);
		resource.add(createLink(bookId));
		return resource;
	}
	
	@RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable String bookId) {
		service.deleteBookById(bookId);
		return status(HttpStatus.ACCEPTED).build();
	}
	
	@RequestMapping(value = "/async", method = RequestMethod.POST)
	public ResponseEntity<Void> async() throws InterruptedException {
		service.async();
		return status(HttpStatus.ACCEPTED).build();
	}

	private Link createLink(String id) throws InterruptedException {
		return linkTo(methodOn(AggregateController.class).get(id)).withSelfRel();
	}
	
}
