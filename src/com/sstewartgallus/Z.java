package com.sstewartgallus;

public abstract class Z {
    static {
        if (true) {
            throw new RuntimeException("phantom");
        }
    }

    private Z() {
    }
}
