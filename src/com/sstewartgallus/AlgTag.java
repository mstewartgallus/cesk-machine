package com.sstewartgallus;

public interface AlgTag<A> {
    // fixme... provide a nicer interface
    enum VoidTag implements AlgTag<Void> {
        VOID_TAG
    }

    record FTag<A>(SetTag<A> value) implements AlgTag<F<A>> {
    }

    record FnTag<A, B>(SetTag<A> domain, AlgTag<B> range) implements AlgTag<Fn<A, B>> {
    }
}
