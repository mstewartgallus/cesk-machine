package com.sstewartgallus;

public abstract class Unit {
    static {
        if (true) {
            throw new RuntimeException("phantom");
        }
    }

    private Unit() {
    }
}
