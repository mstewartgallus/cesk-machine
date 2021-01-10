package com.sstewartgallus;

public record PureFrame<A, R>(Jump<A> control, Env env, Store store, Kont<A, R> kont) implements Frame<R> {
    public Frame<R> step() {
        return control.step(this, env, store, kont);
    }

    public Val<R> halted() {
        return Jump.halt(control, env, store, kont);
    }

    @Override
    public <B> Frame<R> update(Jump<B> body, Env env, Store store, Kont<B, R> kont) {
        return new PureFrame<>(body, env, store, kont);
    }
}
