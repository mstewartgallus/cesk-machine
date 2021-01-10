package com.sstewartgallus;

import java.util.function.Function;

public interface Frame<R> {
    // fixme.. probably best to create an explicit layout structure
    static <A> Frame<A> inject(SetTag<A> aTag, Function<Visitor, Jump<F<A>>> f, boolean pure) {
        var v = new Visitor();
        var start = f.apply(v);
        var program = v.to(aTag, AlgTag.VoidTag.VOID_TAG, start, (Function<Instr<A>, Jump<Void>>) Jump::halt);

        var layout = v.layout();
        var needs = v.needs();

        var kont = new Kont.AbsurdKont<A>();
        if (pure) {
            return new PureFrame<>(program, Env.pure(layout), Store.pure(), kont);
        }
        return new ImpureFrame<>(program, Env.impure(layout, needs), Store.impure(), kont);
    }

    Frame<R> step();

    Val<R> halted();

    <A> Frame<R> update(Jump<A> body, Env env, Store store, Kont<A, R> kont);
}
