package com.doctusoft.bff;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.doctusoft.bff.resource.BookResource;

@FeignClient("http://book")
public interface BookClient {
	
	@RequestMapping(value = "/api/book/{id}", method = RequestMethod.GET)
    BookResource get(@PathVariable("id") String id);
	
}
