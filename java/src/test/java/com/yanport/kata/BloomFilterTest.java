package com.yanport.kata;

import org.junit.Test;


import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.util.Lists.newArrayList;


public class BloomFilterTest {

    @Test
    public void test_bloom_filter_save() throws Exception {
        BloomFilter bloomFilter = new BloomFilter(4, newArrayList(new YPHash()));
        bloomFilter.save("toto1");
        bloomFilter.save("toto22");

        assertThat(bloomFilter.isPresent("toto1")).isTrue();
        assertThat(bloomFilter.isPresent("toto")).isFalse();
        List<Integer> integers = Arrays.asList(bloomFilter.storage).stream().map(b -> b == null ? 0 : 1).collect(toList());
        assertThat(integers).containsExactly(0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0);
    }

}