package com.fersanp.gleidson.consumer;

import com.fersanp.gleidson.domain.model.Order;
import com.fersanp.gleidson.domain.service.OrderProcessorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class OrderListener {

    private final OrderProcessorService orderProcessorService;

    @KafkaListener(topics = "${topic.order.consumer}", groupId = "group_id")
    public void consume(Order order){
        log.info("Order: {}", order);
        orderProcessorService.process(order);
    }
}
