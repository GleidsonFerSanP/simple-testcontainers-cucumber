package com.fersanp.gleidson.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class WireMockConfig {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public WireMockServer mockCustomerService() {
        WireMockServer wireMockServer = new WireMockServer(9561);
        wireMockServer.startRecording("https://fakestoreapi.com/");
        return wireMockServer;
    }

}
