package com.sstewartgallus;

public record ImpureAddr<A>(Val<A> value) implements Addr<A> {
}
