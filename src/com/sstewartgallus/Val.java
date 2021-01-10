package com.sstewartgallus;

public interface Val<A> {
    enum BangVal implements Val<Unit> {
        BANG_VAL
    }
    record ZVal(int value) implements Val<Z> {

    }
    record ThunkVal<A>(Env env, Jump<A> jump) implements Val<U<A>> {

    }
}

