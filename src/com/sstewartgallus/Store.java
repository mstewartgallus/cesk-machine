package com.sstewartgallus;

import java.util.List;
import java.util.Map;

public interface Store {
    static Store pure() {
        return new PureStore(null, Map.of());
    }
    static Store impure() {
        return new ImpureStore();
    }

    <A> Store allocate(Val<A> h);

    <A> Addr<A> latest();

    <A> Val<A> get(Addr<A> addr);

    <A> Store updateAddresses(List<Addr<A>> addresses, Val<A> h);
}
