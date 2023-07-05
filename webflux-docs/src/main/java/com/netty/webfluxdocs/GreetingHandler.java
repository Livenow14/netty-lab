package com.netty.webfluxdocs;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {

    public Mono<ServerResponse> hello(ServerRequest request) { // Rx Publisher (emit at most one item)
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(new Greeting("Helloooo")));
    }

//    // RestController 방식 (상단에 @RestController 추가 필요)
      // 라우터형식이 아니면 servlet의 명세를 간직하게 되는 장단점이 있을 수 있다.
//    // @GetMapping("/hello")
//    public Mono<ResponseEntity> hello(ServerRequest request) { // Rx Publisher (emit at most one item)
//        return Mono.just(ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
//            .body(new Greeting("Helloooo")));
//    }

}
