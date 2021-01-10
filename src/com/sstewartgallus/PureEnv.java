package com.sstewartgallus;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final record PureEnv(Map<Integer, Addr<?>> map) implements Env {
    public <A> PureEnv put(int variable, Addr<A> addr) {
        var newmap = new TreeMap<>(map);
        newmap.put(variable, addr);
        return new PureEnv(newmap);
    }

    @Override
    public Addr<?> get(int variable) {
        var addr = map.get(variable);
        return Objects.requireNonNull(addr);
    }

    @Override
    public Env copy() {
        return this;
    }
}
