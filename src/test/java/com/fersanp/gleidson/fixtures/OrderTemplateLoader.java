package com.fersanp.gleidson.fixtures;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.fersanp.gleidson.domain.model.Order;
import com.fersanp.gleidson.domain.model.Product;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Order.class).addTemplate("default", new Rule(){{
            add("id", UUID.randomUUID());
            add("date", LocalDateTime.now());
            add("products", has(3).of(Product.class, "default"));
        }});

        Fixture.of(Product.class).addTemplate("default", new Rule(){{
            add("id", UUID.randomUUID());
            add("name", random("xpto", "xyz", "foo"));
            add("description", random("Product xpto", "Product xyz", "Product foo"));
        }});
    }
}
