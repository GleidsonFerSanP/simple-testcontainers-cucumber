package com.fersanp.gleidson.domain.service;

import com.fersanp.gleidson.domain.gateway.CustomerClient;
import com.fersanp.gleidson.domain.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerClient customerClient;
    private final URI baseUrl;

    public Customer findCustomer(Long id) {
        return customerClient.getCustomer(baseUrl, id);
    }

}
