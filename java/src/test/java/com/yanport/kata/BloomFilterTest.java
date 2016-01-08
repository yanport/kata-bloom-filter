package com.yanport.kata;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BloomFilterTest {

    @Test
    public void test_bloom_filter_hash_with_max_1() throws Exception {
        BloomFilter bloomFilter = new BloomFilter(1);

        for (int i = 0; i < 100; i++) {
            String random = RandomStringUtils.randomAscii(10);
            assertThat(bloomFilter.hash(random)).isBetween(0, 1);
        }
    }

    @Test
    public void test_bloom_filter_hash_with_max_16() throws Exception {
        BloomFilter bloomFilter = new BloomFilter(16);
        for (int i = 0; i < 1000; i++) {
            String random = RandomStringUtils.randomAscii(1);
            assertThat(bloomFilter.hash(random)).isBetween(0, 16);
        }
    }

    @Test
    public void test_bloom_filter_save() throws Exception {
        BloomFilter bloomFilter = new BloomFilter(1000);
        bloomFilter.save("toto1");

        assertThat(bloomFilter.isPresent("toto1")).isTrue();
        assertThat(bloomFilter.isPresent("toto")).isFalse();
    }

}