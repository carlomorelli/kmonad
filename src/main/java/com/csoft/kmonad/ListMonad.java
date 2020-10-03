package com.csoft.kmonad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ListMonad<T> implements Monad<T> {

    private final List<T> list = new ArrayList<>();

    private ListMonad() {
    }

    private ListMonad(final T t) {
        list.add(t);
    }

    private ListMonad(final List<T> ts) {
        list.addAll(ts);
    }

    @SafeVarargs
    public static <T> ListMonad<T> of(final T... args) {
        return new ListMonad<>(Arrays.asList(args));
    }

    public static <T> ListMonad<T> empty() {
        return new ListMonad<>();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public List<T> unwrap() {
        return list;
    }

    private void concatenate(final ListMonad<T> t) {
        list.addAll(t.unwrap());
    }

    @Override
    public Monad<T> wrap(final T t) {
        return new ListMonad<>(t);
    }

    @Override
    public <W> Monad<W> bind(final Function<T, Monad<W>> application) {
        if (!this.isEmpty()) {
            ListMonad<W> returnMonad = ListMonad.empty();
            for (T t : list) {
                ListMonad<W> w = (ListMonad<W>) application.apply(t);
                returnMonad.concatenate(w);
            }
            return returnMonad;
        }
        return ListMonad.empty();

    }

}
