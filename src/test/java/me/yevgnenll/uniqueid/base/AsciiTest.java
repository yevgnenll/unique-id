package me.yevgnenll.uniqueid.base;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AsciiTest {

    @Test
    void isUpperCaseTrueWhenAtoZ() {
        for (char c = 'A'; c < 'Z'; c ++) {
            assertThat(Ascii.isUpperCase(c)).isTrue();
        }
    }

    @Test
    void isUpperCaseFalseWhenNotAtoZ() {
        for (char c = 0; c < 256; c ++) {
            if (c >= 'A' && c <= 'Z') {
                continue;
            }
            assertThat(Ascii.isUpperCase(c)).isFalse();
        }
    }

    @Test
    void isUpperCaseFalseWhenControlCharacter() {
        assertThat(Ascii.isUpperCase('\u0000')).isFalse();
        assertThat(Ascii.isUpperCase('\u0001')).isFalse();
    }

    @Test
    void isUpperCaseToStringHasOneLowerThanReturnFalse() {
        assertThat(Ascii.isUpperCase("ABcDF")).isFalse();
        assertThat(Ascii.isUpperCase("z")).isFalse();
    }

    @Test
    void isUpperCaseToStringAllUpperThanReturnTrue() {
        assertThat(Ascii.isUpperCase("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue();
    }

}