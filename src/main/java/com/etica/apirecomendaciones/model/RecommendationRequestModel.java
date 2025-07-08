package com.etica.apirecomendaciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

public class RecommendationRequestModel {
    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("lon")
    private Double longitude;
    @JsonProperty("prompt")
    private String prompt;

    public String getPrompt() {
        return prompt;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public RecommendationRequestModel(Double latitude, Double longitude, String prompt) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.prompt = prompt;
    }

    public RecommendationRequestModel(Builder build) {
        this.longitude = build.longitude;
        this.latitude = build.latitude;
        this.prompt = build.prompt;
    }

    public static class Builder {
        private Double longitude;
        private Double latitude;
        private String prompt;

        public Builder longitude(Double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder latitude(Double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder prompt(String prompt) {
            this.prompt = prompt;
            return this;
        }

        public RecommendationRequestModel build() {
            return new RecommendationRequestModel(this);
        }

    }
}