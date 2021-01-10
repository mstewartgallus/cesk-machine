package com.sstewartgallus;

public abstract class U<A> {
    static {
        if (true) {
            throw new RuntimeException("phantom");
        }
    }

    private U() {
    }
}
