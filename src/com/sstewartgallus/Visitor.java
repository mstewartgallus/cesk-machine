package com.sstewartgallus;

import java.util.function.Function;

public final class Visitor {
    // fixme.. create a stack layout object
    private int vars;

    // fixme... consider removing some abstraction

    int layout() {
        return vars;
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

    <A> Instr<U<A>> thunk(Jump<A> jump) {
        return Instr.thunk(jump);
    }

    <A> Jump<A> force(Instr<U<A>> thunk) {
        return Jump.force(thunk);
    }

    <A, B> Jump<B> to(Jump<F<A>> body, Function<Instr<A>, Jump<B>> next) {
        var v = var();
        return Jump.to(body, v, next.apply(Instr.load(v)));
    }


    <A> Jump<F<A>> ret(Instr<A> value) {
        return Jump.ret(value);
    }

    <A, B> Jump<Fn<A, B>> lam(Function<Instr<A>, Jump<B>> body) {
        var v = var();
        return Jump.lam(v, body.apply(Instr.load(v)));
    }

    <A, B> Jump<B> pass(Jump<Fn<A, B>> fn, Instr<A> x) {
        return Jump.pass(fn, x);
    }

    private int var() {
        return vars++;
    }
}
