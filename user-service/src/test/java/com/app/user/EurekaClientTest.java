package com.app.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "eureka.client.enabled=true",
    "eureka.client.register-with-eureka=true", 
    "eureka.client.fetch-registry=true",
    "eureka.client.service-url.defaultZone=http://localhost:8761/eureka/"
})
public class EurekaClientTest {

    @Autowired(required = false)
    private EurekaClientConfigBean eurekaClientConfig;

    @Test
    public void testEurekaClientConfiguration() {
        // Verify Eureka client is configured properly
        assertNotNull(eurekaClientConfig, "Eureka client should be configured");
        assertTrue(eurekaClientConfig.isRegisterWithEureka(), "Should register with Eureka");
        assertTrue(eurekaClientConfig.isFetchRegistry(), "Should fetch registry");
        assertEquals("http://localhost:8761/eureka/", 
                    eurekaClientConfig.getServiceUrl().get("defaultZone"), 
                    "Default zone should be configured correctly");
    }
}