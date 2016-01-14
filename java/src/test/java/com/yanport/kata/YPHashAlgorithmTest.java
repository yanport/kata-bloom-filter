package com.yanport.kata;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class YPHashAlgorithmTest {

    private YPHash ypHash = new YPHash();

    @Test
    public void test_hash() throws Exception {
        for (int i = 0; i < 1000; i++) {
            String random = RandomStringUtils.random(100);
            System.out.println(ypHash.hash(random));
        }
    }

}