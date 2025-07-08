package com.etica.apirecomendaciones.controller;

import com.etica.apirecomendaciones.client.RecommendationGrpcClient;
import com.etica.apirecomendaciones.model.HelloResponse;
import com.etica.apirecomendaciones.model.RecommendationRequestModel;
import com.etica.apirecomendaciones.model.RecommendationResponseModel;
import com.etica.apirecomendaciones.proto.HelloReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationGrpcClient recommendationClient;
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Autowired
    public RecommendationController(RecommendationGrpcClient recommendationClient) {
        this.recommendationClient = recommendationClient;
    }

    @GetMapping("/hello/{name}")
    public HelloResponse sayHello(@PathVariable String name) {
        String message = recommendationClient.sayHello(name);
        return new HelloResponse(message);
    }

    @GetMapping(value = "/stream/{name}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamHello(@PathVariable String name) {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);

        executorService.execute(() -> {
            try {
                Iterator<HelloReply> responses = recommendationClient.streamHello(name);
                while (responses.hasNext()) {
                    HelloReply reply = responses.next();
                    emitter.send(new HelloResponse(reply.getMessage()));
                    Thread.sleep(500); // Add a small delay between messages
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

    @PostMapping(value = "/obtener")
    public RecommendationResponseModel getRecommendation(@RequestBody RecommendationRequestModel req){
        return recommendationClient.getRecommendation(req);
    }
}
