package com.fersanp.gleidson.utils;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import com.fersanp.gleidson.domain.model.Order;
import com.fersanp.gleidson.fixtures.OrderTemplateLoader;
import com.fersanp.gleidson.producer.OrderProducer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class KafkaMessageGenerate {

    @BeforeAll
    static void beforeAll() {
        FixtureFactoryLoader.loadTemplates(OrderTemplateLoader.class.getPackageName());
    }

    @Autowired
    private OrderProducer orderProducer;


    @Test
    public void generate(){
        Order order = Fixture.from(Order.class).gimme("default");
        orderProducer.send(order);
    }

}
