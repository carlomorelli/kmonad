package com.csoft.kmonad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class ListMonad<T> implements Monad<T> {

    private List<T> list = new ArrayList<>();

    private ListMonad() {
    }

    private ListMonad(T t) {
        list.add(t);
    }

    private ListMonad(List<T> ts) {
        list.addAll(ts);
    }

    @SafeVarargs
    public static <T> ListMonad<T> of(T... args) {
        return new ListMonad<>(Arrays.asList(args));

    }

    public static <T> ListMonad<T> empty() {
        return new ListMonad<>();
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public List<T> unwrap() {
        return list;
    }

    private void concatenate(ListMonad<T> t) {
        list.addAll(t.unwrap());
    }

    @Override
    public Monad<T> wrap(T t) {
        return new ListMonad<>(t);
    }

    @Override
    public <W> Monad<W> bind(Function<T, Monad<W>> application) {
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
