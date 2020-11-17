package io.github.mooy1.infinityexpansion.utils;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class TriList<A, B, C> {
    
    @Getter
    private final List<A> a;
    @Getter
    private final List<B> b;
    @Getter
    private final List<C> c;
    private int size;
    
    public TriList() {
        this.a = new ArrayList<>();
        this.b = new ArrayList<>();
        this.c = new ArrayList<>();
        this.size = 0;
    }
    
    public void add(A a, B b, C c) {
        this.a.add(a);
        this.b.add(b);
        this.c.add(c);
        this.size = this.size + 1;
    }
    
    public int size() {
        return this.size;
    }
}
