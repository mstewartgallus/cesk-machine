package com.sstewartgallus;

public abstract class F<A> {
    static {
        if (true) {
            throw new RuntimeException("phantom");
        }
    }

    private F() {
    }
}
