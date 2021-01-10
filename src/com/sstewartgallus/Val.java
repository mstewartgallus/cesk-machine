package com.sstewartgallus;

public interface Val<A> {
    enum BangVal implements Val<Unit> {
        BANG_VAL;

        @Override
        public String toString() {
            return "!";
        }
    }

    record ZVal(int value) implements Val<Z> {
        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    record ThunkVal<A>(Env env, Jump<A> jump) implements Val<U<A>> {

    }
}

