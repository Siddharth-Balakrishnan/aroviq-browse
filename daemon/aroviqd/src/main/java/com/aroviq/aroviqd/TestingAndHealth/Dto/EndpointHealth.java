package com.aroviq.aroviqd.TestingAndHealth.Dto;

public class EndpointHealth {
    private String endpoint;
    private String method;
    private String status;
    private String description;

    public EndpointHealth(String endpoint, String method, String status, String description) {
        this.endpoint = endpoint;
        this.method = method;
        this.status = status;
        this.description = description;
    }

    // Getters and Setters
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
