package com.yanport.kata;

public class YPHash implements HashAlgorithm {

    public int hash(String key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

}
