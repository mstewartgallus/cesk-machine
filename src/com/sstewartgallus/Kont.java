package com.sstewartgallus;

import java.util.List;

public interface Kont<A, R> {

    record PassKont<A, B, R>(Val<A> value, Kont<B, R> kont) implements Kont<Fn<A, B>, R> {
        @Override
        public String toString() {
            return "(" + value + " :: " + kont + ")";
        }
    }

    record ToKont<A, B, R>(List<Addr<B>> addresses, int variable, Env env, Jump<A> next,
                           Kont<A, R> kont) implements Kont<F<B>, R> {
        @Override
        public String toString() {
            return addresses + ":=(to #" + variable + ". " + next + " :: " + kont + ")";
        }
    }

    record AbsurdKont<R>() implements Kont<Void, R> {
        @Override
        public String toString() {
            return "absurd";
        }
    }
}

