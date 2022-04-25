package com.fersanp.gleidson.integration;

import com.fersanp.gleidson.config.WireMockConfig;
import com.fersanp.gleidson.domain.gateway.CustomerClient;
import com.fersanp.gleidson.domain.model.Customer;
import com.fersanp.gleidson.domain.service.CustomerService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClientBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
public class CustomerServiceTest {

    WireMockServer wireMockServer;

    @Autowired
    private CustomerService customerService;

    @BeforeEach
    void setUp() throws URISyntaxException {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
//        wireMockServer.startRecording("http://localhost:8082");
        wireMockServer.startRecording("https://fakestoreapi.com/");
    }

    @Test
    public void test(){
        Customer customer = customerService.findCustomer(1L);
        customer.getEmail();
    }

    @AfterEach
    public void afterEach(){
        wireMockServer.stopRecording();
        wireMockServer.stop();
    }
}
