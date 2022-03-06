package com.fersanp.gleidson.domain.service;

import com.fersanp.gleidson.domain.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderProcessorService {
    public void process(Order order) {
      log.info("order processed={}", order);
    }
}
