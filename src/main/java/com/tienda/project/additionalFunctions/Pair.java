package com.tienda.project.additionalFunctions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pair<T> {
    
    private T fst;
    private T snd;

    public Pair() {}

    public Pair(T fst, T snd) {
        this.fst = fst;
        this.snd = snd;
    }
}
