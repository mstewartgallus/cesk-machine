package com.sstewartgallus;

import java.util.function.Function;

public class Main {
    static private final Function<Visitor, Jump<F<Z>>> SAMPLE = v ->
            v.need(SetTag.ZTag.Z_TAG, new AlgTag.FTag<>(SetTag.ZTag.Z_TAG), v.ret(SetTag.ZTag.Z_TAG, v.z(5)), x ->
                    v.to(SetTag.ZTag.Z_TAG, new AlgTag.FTag<>(SetTag.ZTag.Z_TAG), x, y ->
                            v.ret(SetTag.ZTag.Z_TAG, v.add(v.z(4), y))));

    public static void main(String[] args) {
        var frame = Frame.inject(SetTag.ZTag.Z_TAG, SAMPLE, false);
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
