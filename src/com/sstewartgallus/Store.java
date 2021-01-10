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

    <A> Store allocate(Thunk.Unforced<A> thunk);

    <A> Addr<A> latest();

    <A> Store updateAddresses(List<Addr<A>> addresses, Val<A> h);

    <A> Thunk<A> getThunk(Addr<A> addr);
}
