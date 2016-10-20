package com.jtech.imaging.util;

import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * pair的链式调用
 */
public class PairChain<V extends View, K extends String> {
    List<Pair<V, K>> pairs;

    public PairChain() {
        this.pairs = new ArrayList<>();
    }

    public static PairChain build(View v, String k) {
        return new PairChain().addPair(v, k);
    }

    public PairChain addPair(V v, K k) {
        this.pairs.add(Pair.create(v, k));
        return this;
    }

    public Pair<V, K>[] toArray() {
        return pairs.toArray(new Pair[pairs.size()]);
    }
}