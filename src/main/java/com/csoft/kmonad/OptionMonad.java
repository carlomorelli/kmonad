package com.csoft.kmonad;

import java.util.function.Function;

public class OptionMonad<T> implements Monad<T> {

    private final T t;

    private OptionMonad(final T t) {
        this.t = t;
    }

    public static <T> OptionMonad<T> of(final T t) {
        return new OptionMonad<>(t);
    }

    public static <T> OptionMonad<T> empty() {
        return new OptionMonad<>(null);
    }

    public T get() {
        if (t == null) {
            throw new RuntimeException();
        }
        return this.t;
    }

    public boolean isPresent() {
        return t != null;
    }

    @Override
    public Monad<T> wrap(final T t) {
        return OptionMonad.of(t);
    }

    @Override
    public <W> Monad<W> bind(final Function<T, Monad<W>> application) {
        if (this.isPresent()) {
            T t = this.get();
            return application.apply(t);
        }
        return OptionMonad.empty();
    }

}
