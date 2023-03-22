package com.module2.monitoring;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Endpoint(id = "custom")
@Component
@Profile("!prod")
public class CustomActuatorEndpoint {

    @ReadOperation
    public Map<String, String> customTestEndpoint() {
        Map<String, String> values = new HashMap<>();
        values.put("Reason", "Learning");
        return values;
    }
}
