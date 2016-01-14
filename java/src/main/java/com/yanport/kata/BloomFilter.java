package com.yanport.kata;

import java.util.Arrays;
import java.util.List;


public class BloomFilter {

    private final int max;
    public List<HashAlgorithm> hashFunctions;
    public Byte[] storage;

    public BloomFilter(int powerOf2, List<HashAlgorithm> hashFunctions) {
        this.hashFunctions = hashFunctions;
        this.max = 1 << powerOf2;
        this.storage = new Byte[1 << powerOf2];
    }

    public void save(String word) {
        hashFunctions.stream()
                .map(f -> f.hash(word) & (max - 1))
                .forEach(hash -> storage[hash] = 1);
    }

    public boolean isPresent(String word) {
        return hashFunctions.stream()
                .map(f -> f.hash(word) & (max - 1))
                .allMatch(hash -> storage[hash & (max - 1)] != null);
    }


    @Override
    public String toString() {
        return "BloomFilter: " + Arrays.toString(storage);
    }
}
