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
        var forced = new Thunk.Forced<>(value);
        for (var addr : addresses) {
            var impureAddr = (ImpureAddr<A>)addr;
            impureAddr.thunk = forced;
        }
        return this;
    }

    @Override
    public <A> Thunk<A> getThunk(Addr<A> addr) {
        var impureAddr = (ImpureAddr<A>)addr;
        return impureAddr.thunk;
    }

    @Override
    public String toString() {
        return "{}";
    }
}
