package com.csoft.kmonad;

import java.util.function.Function;

public interface Monad<T> {

    Monad<T> wrap(T t);

    <W> Monad<W> bind(Function<T, Monad<W>> application);

}
