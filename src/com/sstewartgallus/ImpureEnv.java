package com.sstewartgallus;

import java.util.Arrays;
import java.util.Objects;

public final class ImpureEnv implements Env {
    private final Val<?>[] map;

    public ImpureEnv(int size) {
        this.map = new Val[size];
    }

    private ImpureEnv(Val<?>[] src) {
        this.map = src.clone();
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
        throw new RuntimeException("unimplemented");
    }


    @Override
    public Addr<?> get(int variable) {
        throw new RuntimeException("unimplemented");
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
