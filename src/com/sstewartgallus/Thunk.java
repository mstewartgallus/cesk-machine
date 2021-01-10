package com.sstewartgallus;

public interface Thunk<A> {
    record Unforced<A>(Env env, Jump<F<A>> body) implements Thunk<A> {
    }

    record Forced<A>(Val<A> value) implements Thunk<A> {
    }
}
