package com.sstewartgallus;

public interface SetTag<A> {
    // fixme... provide a nicer interface
    enum UnitTag implements SetTag<Unit> {
        UNIT_TAG
    }

    enum ZTag implements SetTag<Z> {
        Z_TAG
    }

    record UTag<A>(AlgTag<A> result) implements SetTag<U<A>> {
    }
}
