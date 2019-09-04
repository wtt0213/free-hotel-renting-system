package com.example.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProjectProperties {

    @Value("${com.example.backend.frontendRootUrl}")
    private String frontendRootUrl;

    @Value("${com.example.backend.backendRootUrl}")
    private String backendRootUrl;

    public String getFrontendRootUrl() {
        return frontendRootUrl;
    }

    public void setFrontendRootUrl(String frontendRootUrl) {
        this.frontendRootUrl = frontendRootUrl;
    }

    public String getBackendRootUrl() {
        return backendRootUrl;
    }

    public void setBackendRootUrl(String backendRootUrl) {
        this.backendRootUrl = backendRootUrl;
    }
}
