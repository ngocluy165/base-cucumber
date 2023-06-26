package com.ngocluy;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

@CucumberContextConfiguration
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = { SpringTestConfig.class })
public class SpringIntegrationTest {
    // executeGet implementation
}