package com.sstewartgallus;

import java.util.List;
import java.util.Objects;

public final class ImpureStore implements Store {
    private Addr<?> theLatest;

    public <A> ImpureStore allocate(Val<A> h) {
        Objects.requireNonNull(h);
        theLatest = new ImpureAddr<>(h);
        return this;
    }

    public <A> Addr<A> latest() {
        return (Addr) theLatest;
    }

    @Override
    public <A> Val<A> get(Addr<A> addr) {
        Objects.requireNonNull(addr);
        var impure = (ImpureAddr<A>) addr;
        return impure.value();
    }

    @Override
    public <A> Store updateAddresses(List<Addr<A>> addresses, Val<A> value) {
        if (addresses.isEmpty()) {
            return this;
        }
        throw new RuntimeException("unimplemented");
    }

    @Override
    public String toString() {
        return "ImpureStore{" +
                "theLatest=" + theLatest +
                '}';
    }
}
