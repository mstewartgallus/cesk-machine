package com.sstewartgallus;

public record ImpureAddr<A>(Thunk.Unforced<A> thunk) implements Addr<A> {
}
