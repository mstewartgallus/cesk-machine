package com.sstewartgallus;

import java.util.function.Function;

public class Main {
    static private final Function<Visitor, Jump<F<Z>>> SAMPLE = v ->
            v.pass(v.lam(x -> v.ret(v.add(v.z(4), x))), v.z(5));

    public static void main(String[] args) {
        var frame = Frame.inject(SAMPLE, false);
        Val<Z> haltVal;
        var step = 0;
        for (; ; ++step) {
            haltVal = frame.halted();
            if (haltVal != null) {
                break;
            }
            System.err.println(step + ":");
            System.err.println("\t" + frame);
            frame = frame.step();
        }
        System.err.println("HALT:");
        System.err.println("\t" + frame);
        System.err.println("RESULT " + haltVal);
    }
}
