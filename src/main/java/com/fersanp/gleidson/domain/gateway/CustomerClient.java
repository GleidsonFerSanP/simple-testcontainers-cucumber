package com.fersanp.gleidson.domain.gateway;

import com.fersanp.gleidson.domain.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@FeignClient(value="simple-customer-client")
public interface CustomerClient {

    @RequestMapping("/users/{id}")
    Customer getCustomer(URI baseUrl, @PathVariable("id") Long id);

}
