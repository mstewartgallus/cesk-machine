package com.sstewartgallus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ImpureStore implements Store {
    private Addr<?> theLatest;

    public <A> ImpureStore allocate(Val<A> h) {
        Objects.requireNonNull(h);
        theLatest = new ImpureAddr<>(h);
        return this;
    }

    public <A> Addr<A> latest() {
        return (Addr)theLatest;
    }

    @Override
    public <A> Val<A> get(Addr<A> addr) {
        Objects.requireNonNull(addr);
        var impure = (ImpureAddr<A>)addr;
        return impure.value();
    }

    @Override
    public String toString() {
        return "ImpureStore{" +
                "theLatest=" + theLatest +
                '}';
    }
}
