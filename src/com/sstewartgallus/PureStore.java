package com.sstewartgallus;

import java.util.*;

public record PureStore(PureAddr<?> theLatest, Map<PureAddr<?>, Thunk<?>> map) implements Store {
    public <A> PureStore allocate(Thunk.Unforced<A> thunk) {
        Objects.requireNonNull(thunk);

        // should be a treemap but addr doesn't work that way
        var newmap = new WeakHashMap<>(map);
        // not really pure but whatever
        var addr = new PureAddr<>();
        newmap.put(addr, thunk);
        return new PureStore(addr, newmap);
    }

    public <A> Addr<A> latest() {
        return Objects.requireNonNull((Addr)theLatest);
    }

    @Override
    public <A> Store updateAddresses(List<Addr<A>> addresses, Val<A> h) {
        if (addresses.isEmpty()) {
            return this;
        }
        throw new RuntimeException("unimplemented");
    }

    @Override
    public <A> Thunk<A> getThunk(Addr<A> addr) {
        throw new RuntimeException("unimplemented");
    }
}
