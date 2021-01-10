package com.sstewartgallus;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class Visitor {
    // fixme.. create a stack layout object
    private List<SetTag<?>> vars = new ArrayList<>();

    // fixme... consider removing some abstraction

    int layout() {
        return vars.size();
    }

    Instr<Unit> bang() {
        return Instr.bang();
    }

    Instr<Z> z(int value) {
        return Instr.z(value);
    }

    Instr<Z> add(Instr<Z> left, Instr<Z> right) {
        return Instr.add(left, right);
    }

    <A> Instr<U<A>> thunk(AlgTag<A> aTag, Jump<A> jump) {
        return Instr.thunk(jump);
    }

    <A> Jump<A> force(AlgTag<A> aTag, Instr<U<A>> thunk) {
        return Jump.force(thunk);
    }

    <A, B> Jump<B> to(SetTag<A> aTag, AlgTag<B> bTag, Jump<F<A>> body, Function<Instr<A>, Jump<B>> next) {
        var v = var(aTag);
        return Jump.to(body, v, next.apply(Instr.load(v)));
    }

    <A> Jump<F<A>> ret(SetTag<A> aTag, Instr<A> value) {
        return Jump.ret(value);
    }

    <A, B> Jump<Fn<A, B>> lam(SetTag<A> aTag, AlgTag<B> bTag, Function<Instr<A>, Jump<B>> body) {
        var v = var(aTag);
        return Jump.lam(v, body.apply(Instr.load(v)));
    }

    <A, B> Jump<B> pass(SetTag<A> aTag, AlgTag<B> bTag, Jump<Fn<A, B>> fn, Instr<A> x) {
        return Jump.pass(fn, x);
    }

    private <A> int var(SetTag<A> aTag) {
        var len = vars.size();
        vars.add(aTag);
        return len;
    }
}
