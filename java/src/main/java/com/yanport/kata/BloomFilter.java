package com.yanport.kata;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BloomFilter {

    private MessageDigest md5;
    private int max;
    private boolean[] storage;

    public BloomFilter(int max) {
        this.max = max;
        this.storage = new boolean[max+1];

        try {
            this.md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public void save(String word) {
        storage[hash(word)] = true;
    }

    public boolean isPresent(String word) {
        return storage[hash(word)];
    }

    public int hash(String word) {
        byte[] bytes = md5.digest(word.getBytes());
        return ((bytes[0] & 0xFF) << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | bytes[3] & 0xFF) & max;
    }

}
