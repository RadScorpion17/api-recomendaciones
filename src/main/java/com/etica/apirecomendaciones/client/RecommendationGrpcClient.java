package com.etica.apirecomendaciones.client;

import com.etica.apirecomendaciones.model.PlaceModel;
import com.etica.apirecomendaciones.model.RecommendationRequestModel;
import com.etica.apirecomendaciones.model.RecommendationResponseModel;
import com.etica.apirecomendaciones.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RecommendationGrpcClient {
    private final ManagedChannel channel;
    private final RecommendationGrpc.RecommendationBlockingStub blockingStub;

    public RecommendationGrpcClient(@Value("${grpc.server.host:localhost}") String host,
                                   @Value("${grpc.server.port:9090}") int port) {
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext() // Disable TLS for simplicity (use TLS in production)
                .build();
        this.blockingStub = RecommendationGrpc.newBlockingStub(channel);
    }

    public String sayHello(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        HelloReply response = blockingStub.sayHello(request);
        return response.getMessage();
    }

    public Iterator<HelloReply> streamHello(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        return blockingStub.streamHello(request);
    }

    public RecommendationResponseModel getRecommendation(RecommendationRequestModel req) {
        RecommendationRequest grpcReq = RecommendationRequest.newBuilder()
                .setLat(req.getLatitude())
                .setLon(req.getLongitude())
                .setPrompt(req.getPrompt())
                .build();

        RecommendationResponse grpcResponse = blockingStub.getRecommendations(grpcReq);

        List<PlaceModel> places = new ArrayList<>();
        for (Place place : grpcResponse.getPlacesList()) {
            places.add(new PlaceModel(
                place.getId(),
                place.getNombre(),
                place.getDescuento()
            ));
        }

        return new RecommendationResponseModel(places);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }
}
