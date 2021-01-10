package com.sstewartgallus;

public interface Kont<A, R> {

    record PassKont<A, B, R>(Val<A> value, Kont<B, R> kont) implements Kont<Fn<A, B>, R> {

    }

    record ToKont<A, B, R>(int variable, Env env, Jump<A> next, Kont<A, R> kont) implements Kont<F<B>, R> {

    }

    record AbsurdKont<R>() implements Kont<Void, R> {
    }
}

