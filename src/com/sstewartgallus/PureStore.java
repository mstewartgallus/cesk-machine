package com.sstewartgallus;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;

public record PureStore(PureAddr<?> theLatest, Map<PureAddr<?>, Val<?>> map) implements Store {
    public <A> PureStore allocate(Val<A> h) {
        Objects.requireNonNull(h);

        // should be a treemap but addr doesn't work that way
        var newmap = new WeakHashMap<>(map);
        // not really pure but whatever
        var addr = new PureAddr<>();
        newmap.put(addr, h);
        return new PureStore(addr, newmap);
    }

    public <A> Addr<A> latest() {
        return Objects.requireNonNull((Addr)theLatest);
    }

    @Override
    public <A> Val<A> get(Addr<A> addr) {
        Objects.requireNonNull(addr);
        var val = (Val<A>) map.get(addr);
        return Objects.requireNonNull(val);
    }
}
