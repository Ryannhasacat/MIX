package com.mix.lambda.stream;

import java.util.function.Function;
import java.util.function.Supplier;

public class Lazy<T> implements Supplier<T> {

    private final Supplier<? extends T> supplier;

    private T value;

    private Lazy(Supplier<? extends T> supplier){
        this.supplier = supplier;
    }

    public static <T> Lazy<T> of(Supplier<? extends T> supplier){
        return new Lazy<>(supplier);
    }

    @Override
    public T get() {

        if (value == null){
            T newValue = supplier.get();

            if (newValue == null){
                throw new IllegalStateException("Lazy value can not be null!");
            }
            value = newValue;
        }

        return value;
    }

    /**
     * 函子
     * @param function
     * @param <S>
     * @return
     */
    public <S> Lazy<S> map(Function<? super T, ? extends S> function) {
        return Lazy.of(() -> function.apply(get()));
    }

    /**
     * 单子
     * @param function
     * @param <S>
     * @return
     */
    public <S> Lazy<S> flatMap(Function<? super T, Lazy<? extends S>> function) {
        return Lazy.of(() -> function.apply(get()).get());
    }
}
