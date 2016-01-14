package com.yanport.kata;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BinaryFuckingStuff {

    @Test
    public void test_pipe() throws Exception {
        int value = 1 << 4 | 1 << 2;

        assertThat(Integer.toBinaryString(value)).isEqualTo("10100");
    }

    @Test
    public void test_left_shift() throws Exception {
        int value = 1 << 4;

        assertThat(Integer.toBinaryString(value)).isEqualTo("10000");
    }

    @Test
    public void test_right_shift() throws Exception {
        int value = 1 << 4; //10000
        value = value >> 2;

        assertThat(Integer.toBinaryString(value)).isEqualTo("100");
    }

    @Test
    public void test_right_shift_with_negative_value() throws Exception {
        int value = - (1 << 4); //11111111111111111111111111110000
        value = value >> 2;

        assertThat(value).isNegative();
        assertThat(Integer.toBinaryString(value)).isEqualTo("11111111111111111111111111111100");
    }

    @Test
    public void test_unsigned_right_shift_with_negative_value() throws Exception {
        int value = - (1 << 4); //11111111111111111111111111110000
        value = value >>> 2;

        assertThat(value).isPositive();
        assertThat(Integer.toBinaryString(value)).isEqualTo("111111111111111111111111111100");
    }

}
