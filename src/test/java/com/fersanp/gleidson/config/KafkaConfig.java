package com.fersanp.gleidson.config;

import com.fersanp.gleidson.domain.model.Order;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.ClassRule;
import org.mockito.internal.matchers.Or;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class KafkaConfig {

    @Bean
    @Profile("test")
    public KafkaContainer kafkaContainer() {
        Network network = Network.newNetwork();
        KafkaContainer kafka =
                new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3")).withNetwork(network);
        kafka.start();
        return kafka;
    }

    @Bean
    @Profile("test")
    public Map<String, Object> consumerConfigs(KafkaContainer kafkaContainer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        return props;
    }

    @Bean
    @Profile("test")
    public ProducerFactory<String, Order> producerFactory(KafkaContainer kafkaContainer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonSerializer");
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    @Profile("test")
    public KafkaTemplate<String, Order> kafkaTemplate(KafkaContainer kafkaContainer) {
        return new KafkaTemplate<>(producerFactory(kafkaContainer));
    }

    @Bean
    @Profile("test")
    public Map<String, Object> consumerProperties(KafkaContainer kafkaContainer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.springframework.kafka.support.serializer.JsonDeserializer");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put("spring.kafka.consumer.properties.spring.json.trusted.packages", "*");
        props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 10000);
        return props;
    }

    @Bean
    @Profile("test")
    public DefaultKafkaConsumerFactory<String, Order> consumerFactory(KafkaContainer kafkaContainer) {
        return new DefaultKafkaConsumerFactory<>(consumerProperties(kafkaContainer), new StringDeserializer(), new JsonDeserializer<>(Order.class));
    }

    @Bean
    @Profile("test")
    public ConcurrentKafkaListenerContainerFactory<String, Order> kafkaListenerContainerFactory(KafkaContainer kafkaContainer) {
        ConcurrentKafkaListenerContainerFactory<String, Order> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory(kafkaContainer));
        factory.setBatchListener(true);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
