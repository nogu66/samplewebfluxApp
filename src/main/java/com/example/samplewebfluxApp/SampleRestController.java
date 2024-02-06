package com.example.samplewebfluxApp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SampleRestController {
    @Autowired
    PostRepository repository;

    @RequestMapping("/")
    public String hello() {
        return "Hello, Flux!";
    }

    @RequestMapping("/flux")
    public Mono<String> flux() {
        return Mono.just("Hello, Flux (Mono).");
    }
    
    @RequestMapping("/flux2")
    public Flux<String> flux2() {
        return Flux.just("Hello, Flux.", "これはFluxのサンプルです。");
    }

    @RequestMapping("/post")
    public Flux<Object> post() {
        List<Post> posts = repository.findAll();
        return Flux.fromArray(posts.toArray());
    }

    @RequestMapping("/post/{id}")
    public Mono<Post> post(@PathVariable int id) {
        Post post = repository.findById(id);
        return Mono.just(post);
    }

    @PostConstruct
    public void init() {
        Post p1 = new Post(1, 1, "Hello", "Hello Flux!");
        Post p2 = new Post(2, 2, "Smaple", "This is sample post.");
        Post p3 = new Post(3, 3, "ハロー", "これはサンプルです");
        repository.save(p1);
        repository.save(p2);
        repository.save(p3);
    }
}
