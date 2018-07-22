package com.doctusoft.bff;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.doctusoft.bff.resource.InventoryResource;
import com.netflix.hystrix.HystrixCommand;

@FeignClient("http://inventory")
public interface InventoryClient {
	
	@RequestMapping(value = "/api/inventory/{id}", method = RequestMethod.GET)
    HystrixCommand<InventoryResource> get(@PathVariable("id") String id);

}
