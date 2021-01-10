package com.sstewartgallus;

import java.util.Map;

public interface Env {
    static Env pure(int layout) {
        return new PureEnv(Map.of());
    }

    static Env impure(int values, int needs) {
        return new ImpureEnv(values, needs);
    }

    // fixme... put tags here...
    <A> Env put(int variable, Val<A> val);

    Val<?> getVal(int variable);

    <A> Env put(int variable, Addr<A> addr);

    Env copy();

    <A> Addr<A> need(SetTag<A> aTag, int needVar);
}
