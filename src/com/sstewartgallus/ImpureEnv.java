package com.sstewartgallus;

import java.util.Arrays;
import java.util.Objects;

public final class ImpureEnv implements Env {
    private final Val<?>[] map;
    private final Addr<?>[] addrs;

    public ImpureEnv(int values, int needs) {
        this.map = new Val[values];
        this.addrs = new Addr[needs];
    }

    private ImpureEnv(Val<?>[] map, Addr<?>[] addrs) {
        this.map = map.clone();
        this.addrs = addrs.clone();
    }

    @Override
    public <A> Env put(int variable, Val<A> val) {
        Objects.requireNonNull(val);
        map[variable] = val;
        return this;
    }

    @Override
    public Val<?> getVal(int variable) {
        var val = map[variable];
        return Objects.requireNonNull(val);
    }

    @Override
    public <A> Env put(int variable, Addr<A> addr) {
        Objects.requireNonNull(addr);
        addrs[variable] = addr;
        return this;
    }

    @Override
    public <A> Addr<A> need(SetTag<A> aTag, int needVar) {
        // fixme.. check cast
        return (Addr) addrs[needVar];
    }

    @Override
    public Env copy() {
        return new ImpureEnv(map, addrs);
    }

    @Override
    public String toString() {
        return "ImpureEnv{" +
                "map=" + Arrays.toString(map) +
                '}';
    }
}
