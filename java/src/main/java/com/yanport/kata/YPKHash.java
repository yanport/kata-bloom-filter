package com.yanport.kata;

public class YPKHash implements HashAlgorithm {

    private final int seed;

    public YPKHash(int seed) {
        this.seed = seed;
    }

    public int hash(String key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode() * seed) ^ (h >>> 16);
    }

}
