package com.sstewartgallus;

enum BangInstr implements Instr<Unit> {
    BANG_INSTR;

    @Override
    public Val<Unit> eval(Env env, Store store) {
        return Val.BangVal.BANG_VAL;
    }
}

public interface Instr<A> {
    static <A> Instr<A> load(int variable) {
        return new LoadInstr<>(variable);
    }

    static Instr<Unit> bang() {
        return BangInstr.BANG_INSTR;
    }

    static <A> Instr<U<A>> thunk(Jump<A> jump) {
        return new ThunkInstr<>(jump);
    }

    static Instr<Z> z(int value) {
        return new IntegerInstr(value);
    }

    static Instr<Z> add(Instr<Z> left, Instr<Z> right) {
        return new AddInstr(left, right);
    }

    Val<A> eval(Env env, Store store);
}

record LoadInstr<A>(int variable) implements Instr<A> {
    @Override
    public Val<A> eval(Env env, Store store) {
        Addr<A> addr = (Addr<A>) env.get(variable);
        var val = store.get(addr);
        return val;
    }
}

record ThunkInstr<A>(Jump<A> jump) implements Instr<U<A>> {
    @Override
    public Val<U<A>> eval(Env env, Store store) {
        return new Val.ThunkVal<>(env.copy(), jump);
    }
}

record IntegerInstr(int value) implements Instr<Z> {
    @Override
    public Val<Z> eval(Env env, Store store) {
        return new Val.ZVal(value);
    }
}

record AddInstr(Instr<Z> left, Instr<Z> right) implements Instr<Z> {
    @Override
    public Val.ZVal eval(Env env, Store store) {
        var l = (Val.ZVal) left.eval(env, store);
        var r = (Val.ZVal) right.eval(env, store);
        return new Val.ZVal(l.value() + r.value());
    }
}