package com.doctusoft.bff;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedResources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.doctusoft.bff.resource.ReviewResource;
import com.netflix.hystrix.HystrixCommand;

@FeignClient("http://review")
public interface ReviewClient {
	
	@RequestMapping(value = "/api/review/search", method = RequestMethod.GET)
    HystrixCommand<PagedResources<ReviewResource>> searchReviewsByBookId(@RequestParam("bookId") String bookId);

}
