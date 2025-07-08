package com.etica.apirecomendaciones.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.ArrayList;

public class RecommendationResponseModel {
    @JsonProperty("places")
    private List<PlaceModel> places;

    public RecommendationResponseModel() {
        this.places = new ArrayList<>();
    }

    public RecommendationResponseModel(List<PlaceModel> places) {
        this.places = places;
    }

    public List<PlaceModel> getPlaces() {
        return places;
    }
    
    public void setPlaces(List<PlaceModel> places) {
        this.places = places;
    }

    public void addPlace(PlaceModel place) {
        if (this.places == null) {
            this.places = new ArrayList<>();
        }
        this.places.add(place);
    }
}