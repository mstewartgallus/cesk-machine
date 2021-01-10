package com.sstewartgallus;

import java.util.Map;

public interface Env {
    static Env pure(int layout) {
        return new PureEnv(Map.of());
    }

    static Env impure(int size) {
        return new ImpureEnv(size);
    }

    <A> Env put(int variable, Addr<A> addr);

    Addr<?> get(int variable);

    Env copy();
}
