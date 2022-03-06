package com.fersanp.gleidson.producer;

import com.fersanp.gleidson.domain.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderProducer {

    @Value("${topic.order.producer}")
    private String topicName;

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public void send(Order order){
        log.info("Payload sent: {}", order);
        kafkaTemplate.send(topicName, order);
    }
}
