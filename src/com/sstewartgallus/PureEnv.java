package com.sstewartgallus;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public final record PureEnv(Map<Integer, Val<?>> map) implements Env {
    @Override
    public <A> Env put(int variable, Val<A> val) {
        var newmap = new TreeMap<>(map);
        newmap.put(variable, val);
        return new PureEnv(newmap);
    }

    @Override
    public Val<?> getVal(int variable) {
        var val = map.get(variable);
        return Objects.requireNonNull(val);
    }

    public <A> PureEnv put(int variable, Addr<A> addr) {
        throw new RuntimeException("unimplemented");
    }

    @Override
    public Addr<?> get(int variable) {
        throw new RuntimeException("unimplemented");
    }

    @Override
    public Env copy() {
        return this;
    }

    @Override
    public <A> Addr<A> need(SetTag<A> aTag, int needVar) {
        throw new RuntimeException("unimplemented");
    }
}
