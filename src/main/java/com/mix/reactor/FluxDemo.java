package com.mix.reactor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


/**
 * @author Ryan z
 */
public class FluxDemo {

    public static void main(String[] args) {
        Flux<String> flux = Flux.just("a", "b", "c");
        Mono<List<String>> listMono =
                flux.map(String::toUpperCase)
                        .filter("A"::equals)
                        .collectList();
        List<String> block = listMono.toFuture().join();
        assert block != null;
        block.forEach(System.out::println);
    }

}
