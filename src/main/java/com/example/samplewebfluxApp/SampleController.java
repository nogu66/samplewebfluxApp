package com.example.samplewebfluxApp;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Controller
public class SampleController {
    
    @Bean
    public RouterFunction<ServerResponse> routes() {
        return route(GET("/f/hello"), this::hello).andRoute(GET("/f/hello2"), this::hello2);
    }

    Mono<ServerResponse> hello(ServerRequest req) {
        return ok().body(Mono.just("Hello, Functional routing world!"), String.class);
    }

     Mono<ServerResponse> hello2(ServerRequest req) {
        return ok().body(Mono.just("関数ルーティングの世界へようこそ！"), String.class);
    }
}
