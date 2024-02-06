package com.example.samplewebfluxApp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class SampleRestController {
    @Autowired
    PostRepository repository;

    private final WebClient webClient;

    public SampleRestController(WebClient.Builder builder) {
        super();
        webClient = builder.baseUrl("jsonplaceholder.typicode.com").build();
    }

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

    @RequestMapping("/file")
    public Mono<String> file() {
        String result = "";
        try {
            ClassPathResource cr = new ClassPathResource("sample.txt");
            InputStream is = cr.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (IOException e) {
            result = e.getMessage();
        }
        return Mono.just(result);
    }

    @RequestMapping("/web/{id}")
    public Mono<Post> web(@PathVariable int id) {
        return this.webClient.get().uri("/posts/" + id).accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToMono(Post.class);
    }

    @RequestMapping("/web")
    public Flux<Post> web2() {
        return this.webClient.get().uri("/posts").accept(MediaType.APPLICATION_JSON).retrieve()
                .bodyToFlux(Post.class);
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
