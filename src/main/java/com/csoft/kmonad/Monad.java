package com.csoft.kmonad;

import java.util.function.Function;

public interface Monad<T> {

    Monad<T> wrap(final T t);

    <W> Monad<W> bind(final Function<T, Monad<W>> application);

}
