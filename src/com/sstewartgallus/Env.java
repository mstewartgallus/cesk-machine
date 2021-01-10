package com.sstewartgallus;

import java.util.Map;

public interface Env {
    static Env pure(int layout) {
        return new PureEnv(Map.of());
    }

    static Env impure(int size) {
        return new ImpureEnv(size);
    }

    // fixme... put tags here...
    <A> Env put(int variable, Val<A> val);
    Val<?> getVal(int variable);

    <A> Env put(int variable, Addr<A> addr);

    Addr<?> get(int variable);

    Env copy();

    <A> Addr<A> need(SetTag<A> aTag, int needVar);
}
