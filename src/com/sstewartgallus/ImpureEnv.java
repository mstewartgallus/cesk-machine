package com.sstewartgallus;

import java.util.Arrays;
import java.util.Objects;

public final class ImpureEnv implements Env {
    private final Addr<?>[] map;

    public ImpureEnv(int size) {
        this.map = new Addr[size];
    }

    private ImpureEnv(Addr<?>[] src) {
        this.map = src.clone();
    }

    public <A> ImpureEnv put(int variable, Addr<A> addr) {
        Objects.requireNonNull(addr);
        map[variable] = addr;
        return this;
    }

    @Override
    public Addr<?> get(int variable) {
        var addr = map[variable];
        return Objects.requireNonNull(addr);
    }

    @Override
    public Env copy() {
        return new ImpureEnv(map);
    }

    @Override
    public String toString() {
        return "ImpureEnv{" +
                "map=" + Arrays.toString(map) +
                '}';
    }
}
