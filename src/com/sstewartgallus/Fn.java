package com.sstewartgallus;

public abstract class Fn<A, B> {
    static {
        if (true) {
            throw new RuntimeException("phantom");
        }
    }

    private Fn() {
    }
}
