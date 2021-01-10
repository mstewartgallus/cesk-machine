package com.sstewartgallus;

public abstract class Void {
    static {
        if (true) {
            throw new RuntimeException("phantom");
        }
    }

    private Void() {
    }
}
