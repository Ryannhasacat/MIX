package com.mix.reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;

/**
 *  pub 服务提供 ==> sync sub 服务消费 ==> nonblock
 *
 * proof Publisher has sync
 *
 * proof Subscriber has nonblock
 *
 * Subscription : what does it act? & What it does?
 * @param <T>
 */
public abstract class Flux<T> implements Publisher<T> {

    public abstract void subscribe(Subscriber<? super T> subscriber);

    public static <T> Flux<T> just(T... data) {

        return new FluxArray<>(data);
    }
}
