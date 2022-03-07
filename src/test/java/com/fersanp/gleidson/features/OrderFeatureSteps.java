package com.fersanp.gleidson.features;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fersanp.gleidson.config.KafkaConfig;
import com.fersanp.gleidson.consumer.OrderListener;
import com.fersanp.gleidson.domain.model.Order;
import com.fersanp.gleidson.domain.service.OrderProcessorService;
import com.fersanp.gleidson.fixtures.OrderTemplateLoader;
import com.fersanp.gleidson.producer.OrderProducer;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

@Import(KafkaConfig.class)
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@CucumberContextConfiguration
@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
public class OrderFeatureSteps {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private OrderProducer orderProducer;

    @SpyBean
    private OrderProcessorService orderProcessorService;

    @Autowired
    private OrderListener orderListener;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    private Order order;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @ParameterType(".*")
    public Order order(String id) {
        FixtureFactoryLoader.loadTemplates(OrderTemplateLoader.class.getPackageName());
        return Fixture.from(Order.class).gimme("default");
    }

    @Given("an {order} is published")
    public void anOrderIsPublished(Order order) {
        this.order = order;
        orderProducer.send(order);
    }

    @Then("the order listener consumer the message")
    public void theOrderListenerConsumerTheMessage() {
        orderProcessorService = (OrderProcessorService) context.getBean("orderProcessorService");
        try {
            Thread.sleep(300L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mockito.verify(orderProcessorService, Mockito.times(1)).process(orderCaptor.capture());
        Order value = orderCaptor.getValue();
        Assertions.assertEquals(value, order);
    }
//    @Given("^another order with an id as (.*) is published$")
//    public void anotherOrderWithAnIdAsNullIsPublished(String id) {
//        FixtureFactoryLoader.loadTemplates(OrderTemplateLoader.class.getPackageName());
//        this.order = Fixture.from(Order.class).gimme("default");
//        this.order.setId(null);
//        orderProducer.send(order);
//    }
//
//    @Then("the order listener consumer and throw exception")
//    public void theOrderListenerConsumerAndThrowException() {
//        orderProcessorService = (OrderProcessorService) context.getBean("orderProcessorService");
//        try {
//            Thread.sleep(300L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        Assertions.assertThrows(RuntimeException.class, () -> Mockito.verify(orderProcessorService).process(order));
//    }
}
