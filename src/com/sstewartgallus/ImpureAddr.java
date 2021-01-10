package com.sstewartgallus;

public final class ImpureAddr<A> implements Addr<A> {
    Thunk<A> thunk;

    public ImpureAddr(Thunk.Unforced<A> thunk) {
        this.thunk = thunk;
    }

    @Override
    public String toString() {
        return thunk.toString();
    }
}
