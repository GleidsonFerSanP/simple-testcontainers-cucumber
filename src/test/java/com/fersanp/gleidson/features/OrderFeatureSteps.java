package com.fersanp.gleidson.features;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@ExtendWith(MockitoExtension.class)
@CucumberContextConfiguration
@SpringBootTest
public class OrderFeatureSteps {

    @Autowired
    private OrderProducer orderProducer;

    @Mock
    private OrderProcessorService orderProcessorService;

    @InjectMocks
    private OrderListener orderListener;

    @Captor
    ArgumentCaptor<Order> orderCaptor;

    @Before
    public void before(){
        MockitoAnnotations.initMocks(this);
    }

    @ParameterType(".*")
    public Order order(String orderDesc) {
        FixtureFactoryLoader.loadTemplates(OrderTemplateLoader.class.getPackageName());
        return Fixture.from(Order.class).gimme("default");
    }

    @Given("an {order} is published")
    public void anOrderIsPublished(Order order) {
        orderProducer.send(order);
    }

    @When("it's processed")
    public void itSProcessed() {
        Mockito.verify(orderProcessorService).process(orderCaptor.capture());
        Order value = orderCaptor.getValue();
    }

    @Then("the order listener consumer the message")
    public void theOrderListenerConsumerTheMessage() {
    }

}
