package com.aroviq.aroviqd.TestingAndHealth.Service;

import com.aroviq.aroviqd.TestingAndHealth.Dto.EndpointHealth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthService {

    private final RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public HealthService(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public List<EndpointHealth> discoverEndpoints() {
        return this.handlerMapping.getHandlerMethods().keySet().stream()
                .map(mapping -> {
                    String endpoint = mapping.getDirectPaths().stream().findFirst().orElse(mapping.toString());
                    String methods = mapping.getMethodsCondition().getMethods().stream()
                            .map(Enum::name)
                            .collect(Collectors.joining(", "));
                    return new EndpointHealth(endpoint, methods, "UP", "Endpoint is registered and active");
                })
                .collect(Collectors.toList());
    }

    public String getOverallHealth() {
        // Basic health check for the daemon
        return "HEALTHY";
    }
}
