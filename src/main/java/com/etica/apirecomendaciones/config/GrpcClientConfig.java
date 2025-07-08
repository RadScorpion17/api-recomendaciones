package com.etica.apirecomendaciones.config;

import com.etica.apirecomendaciones.client.RecommendationGrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;

@Configuration
public class GrpcClientConfig {

    private RecommendationGrpcClient recommendationClient;

    @Autowired
    public void setRecommendationClient(RecommendationGrpcClient recommendationClient) {
        this.recommendationClient = recommendationClient;
    }

    @PreDestroy
    public void destroy() throws InterruptedException {
        if (recommendationClient != null) {
            recommendationClient.shutdown();
        }
    }
}
