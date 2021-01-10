package com.sstewartgallus;

import java.util.List;
import java.util.Objects;

public final class ImpureStore implements Store {
    private Addr<?> theLatest;

    public <A> ImpureStore allocate(Thunk.Unforced<A> thunk) {
        Objects.requireNonNull(thunk);
        theLatest = new ImpureAddr<>(thunk);
        return this;
    }

    public <A> Addr<A> latest() {
        return (Addr) theLatest;
    }

    @Override
    public <A> Store updateAddresses(List<Addr<A>> addresses, Val<A> value) {
        if (addresses.isEmpty()) {
            return this;
        }
        throw new RuntimeException("unimplemented");
    }

    @Override
    public <A> Thunk<A> getThunk(Addr<A> addr) {
        throw new RuntimeException("unimplemented");
    }

    @Override
    public String toString() {
        return "ImpureStore{" +
                "theLatest=" + theLatest +
                '}';
    }
}
