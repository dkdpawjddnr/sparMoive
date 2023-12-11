package com.sparta.sparmovie.test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public ControllerTest controllerTest() {
        return new ControllerTest();
    }
}
