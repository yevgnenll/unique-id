package me.yevgnenll.uniqueid.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChecksTest {

    @Test
    void nullThenException() {
        assertThatIllegalArgumentException().isThrownBy(() -> Checks.nonNull(null, "not null must be"));
    }

    @Test
    void nonNullThenNothing() {
        assertDoesNotThrow(() -> Checks.nonNull("non null", "not null must be"));
    }

}