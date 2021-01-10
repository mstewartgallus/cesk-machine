package com.sstewartgallus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public interface Jump<A> {

    static <A> Jump<Void> halt(Instr<A> value) {
        return new HaltJump<>(value);
    }

    static <A> Jump<A> force(Instr<U<A>> thunk) {
        return new ForceJump<>(thunk);
    }

    static <A, B> Jump<B> need(SetTag<A> aTag, AlgTag<B> bTag, Jump<F<A>> body, int needVar, Jump<B> next) {
        return new NeedJump<>(body, needVar, next);
    }

    static <A> Jump<F<A>> once(SetTag<A> aTag, int needVar) {
        return new OnceJump<>(aTag, needVar);
    }

    static <A, B> Jump<B> to(Jump<F<A>> body, int variable, Jump<B> next) {
        return new ToJump<>(body, variable, next);
    }

    static <A, B> Jump<Fn<A, B>> lam(int variable, Jump<B> next) {
        return new LamJump<>(variable, next);
    }

    static <A> Jump<F<A>> ret(Instr<A> value) {
        return new ReturnJump<>(value);
    }

    static <A, B> Jump<B> pass(Jump<Fn<A, B>> f, Instr<A> value) {
        return new PassJump<>(f, value);
    }

    static <A, R> Val<R> halt(Jump<A> jump, Env env, Store store, Kont<A, R> kont) {
        if (!(jump instanceof HaltJump<?> halt)) {
            return null;
        }
        // fixme.. unsafe with no justification
        Val<R> val = (Val) halt.value().eval(env, store);
        return Objects.requireNonNull(val);
    }

    <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<A, R> kont);
}

record HaltJump<A>(Instr<A> value) implements Jump<Void> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<Void, R> kont) {
        throw new RuntimeException("halt!");
    }

    @Override
    public String toString() {
        return "(halt " + value + ")";
    }
}

record ForceJump<A>(Instr<U<A>> thunk) implements Jump<A> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<A, R> kont) {
        var thunkval = (Val.ThunkVal<A>) thunk.eval(env, store);
        var newenv = thunkval.env().copy();
        return frame.update(thunkval.jump(), newenv, store, kont);
    }

    @Override
    public String toString() {
        return "(force " + thunk + ")";
    }
}

record ToJump<A, B>(Jump<F<A>> body, int variable, Jump<B> next) implements Jump<B> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<B, R> kont) {
        var copy = env.copy();
        return frame.update(body, env, store, new Kont.ToKont<>(Collections.emptyList(), variable, copy, next, kont));
    }

    @Override
    public String toString() {
        return "(" + body + " to #" + variable + ". " + next + ")";
    }
}

record OnceJump<A>(SetTag<A> aTag, int needVar) implements Jump<F<A>> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<F<A>, R> kont) {
        var toKont = (Kont.ToKont<?, A, R>) kont;
        return stepToKont(frame, env, store, toKont);
    }

    private <R, B> Frame<R> stepToKont(Frame<R> frame, Env env, Store store, Kont.ToKont<B, A, R> toKont) {
        var addr = env.need(aTag, needVar);
        var thunk = store.getThunk(addr);

        if (thunk instanceof Thunk.Forced<A> forced) {
            var val = forced.value();
            var e = toKont.env();
            e = e.put(toKont.variable(), val);
            store = store.updateAddresses(toKont.addresses(), val);
            return frame.update(toKont.next(), e, store, toKont.kont());
        }
        var unforced = (Thunk.Unforced<A>) thunk;

        var addresses = toKont.addresses();

        // fixme... dumb
        addresses = new ArrayList<>(addresses);
        addresses.add(addr);

        return frame.update(unforced.body(), unforced.env(), store, new Kont.ToKont<>(addresses, toKont.variable(), toKont.env(), toKont.next(), toKont.kont()));
    }

    @Override
    public String toString() {
        return "p" + needVar;
    }
}

record NeedJump<A, B>(Jump<F<A>> body, int variable, Jump<B> next) implements Jump<B> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<B, R> kont) {
        store = store.allocate(new Thunk.Unforced<>(env.copy(), body));
        env = env.put(variable, store.latest());
        return frame.update(next, env, store, kont);
    }

    @Override
    public String toString() {
        return "(" + body + " need p" + variable + ". " + next + ")";
    }
}

record ReturnJump<A>(Instr<A> value) implements Jump<F<A>> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<F<A>, R> kont) {
        var toKont = (Kont.ToKont<?, A, R>) kont;
        return stepToKont(frame, env, store, toKont);
    }

    private <B, R> Frame<R> stepToKont(Frame<R> frame, Env env, Store store, Kont.ToKont<B, A, R> toKont) {
        var h = value.eval(env, store);
        var e = toKont.env();
        e = e.put(toKont.variable(), h);
        store = store.updateAddresses(toKont.addresses(), h);
        return frame.update(toKont.next(), e, store, toKont.kont());
    }

    @Override
    public String toString() {
        return "(return " + value + ")";
    }
}

record LamJump<A, B>(int variable, Jump<B> next) implements Jump<Fn<A, B>> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<Fn<A, B>, R> kont) {
        var fnkont = (Kont.PassKont<A, B, R>) kont;
        var h = fnkont.value();
        var t = fnkont.kont();
        env = env.put(variable, h);
        return frame.update(next, env, store, t);
    }

    @Override
    public String toString() {
        return "(lam #" + variable + ". " + next + ")";
    }
}

record PassJump<A, B>(Jump<Fn<A, B>> fn, Instr<A> x) implements Jump<B> {
    @Override
    public <R> Frame<R> step(Frame<R> frame, Env env, Store store, Kont<B, R> kont) {
        var xval = x.eval(env, store);
        return frame.update(fn, env, store, new Kont.PassKont<>(xval, kont));
    }
    @Override
    public String toString() {
        return "(" + fn + " " + x + ")";
    }
}