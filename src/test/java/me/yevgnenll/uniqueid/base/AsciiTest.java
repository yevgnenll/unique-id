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

    @Test
    void isLowerCaseTrueWhenAtoZ() {
        for (char c = 'a'; c <= 'z'; c ++) {
            assertThat(Ascii.isLowerCase(c)).isTrue();
        }
    }

    @Test
    void isLowerCaseFalseWhenATOZ() {
        for (char c = 0; c < 256; c ++) {
            if (c >= 'a' && c <= 'z') {
                continue;
            }
            assertThat(Ascii.isLowerCase(c)).isFalse();
        }
    }

    @Test
    void isLowerCaseToStringHasOneUpperThanReturnFalse() {
        assertThat(Ascii.isLowerCase("abcdefghijklmnopqrstuvwxyzA")).isFalse();
    }

    @Test
    void isLowerCaseToStringAllLowerCaseThanReturnTrue() {
        assertThat(Ascii.isLowerCase("abcdefghijklmnopqrstuvwxyz")).isTrue();
    }

    @Test
    void toUpperCaseIfLowerAThanUpperA() {
        assertThat(Ascii.toUpperCase('a')).isEqualTo('A');
        for (char c = 'a'; c <= 'z'; c ++) {
            assertThat(Ascii.toUpperCase(c)).isEqualTo((char)(c ^ 32));
        }
        assertThat(Ascii.toUpperCase('z')).isEqualTo('Z');
    }

    @Test
    void toUpperCaseIfHasLowerThanAllUpperCase() {
        String str = "ABCEFGhijkLMNOP";
        assertThat(Ascii.toUpperCase(str)).isEqualTo("ABCEFGHIJKLMNOP");
    }

    @Test
    void toUpperCaseReturnItsSelfWhenUpper() {
        assertThat(Ascii.toUpperCase('A')).isEqualTo('A');
        assertThat(Ascii.toUpperCase('Z')).isEqualTo('Z');
    }

    @Test
    void toUpperCaseWhenString() {
        for (char c = 'a'; c <= 'z'; c ++) {
            assertThat(Ascii.toUpperCase(String.valueOf(c))).isEqualTo(String.valueOf((char)(c ^ 32)));
        }
    }
}