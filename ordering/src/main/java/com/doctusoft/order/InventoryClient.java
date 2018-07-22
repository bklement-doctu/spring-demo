package com.doctusoft.order;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("http://inventory")
public interface InventoryClient {
	
	@RequestMapping(value = "/api/item/{id}/reserve", method = RequestMethod.POST)
    void reserve(@PathVariable("id") String id, @RequestParam("quantity") Integer quantity);

}
