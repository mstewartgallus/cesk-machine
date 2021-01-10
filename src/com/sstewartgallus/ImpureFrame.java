package com.sstewartgallus;

public final class ImpureFrame<A, R> implements Frame<R> {
    private Jump<A> control;
    private Env env;
    private Store store;
    private Kont<A, R> kont;

    public ImpureFrame(Jump<A> control, Env env, Store store, Kont<A, R> kont) {
        this.control = control;
        this.env = env;
        this.store = store;
        this.kont = kont;
    }

    public Frame<R> step() {
        return control.step(this, env, store, kont);
    }

    public Val<R> halted() {
        return Jump.halt(control, env, store, kont);
    }

    @Override
    public <B> Frame<R> update(Jump<B> body, Env env, Store store, Kont<B, R> kont) {
        this.control = (Jump) body;
        this.env = env;
        this.store = store;
        this.kont = (Kont) kont;
        return this;
    }

    @Override
    public String toString() {
        return "ImpureFrame{" +
                "control=" + control +
                ", env=" + env +
                ", store=" + store +
                ", kont=" + kont +
                '}';
    }
}
