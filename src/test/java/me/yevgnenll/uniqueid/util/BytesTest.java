package me.yevgnenll.uniqueid.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class BytesTest {

    @Test
    void byteToLong() {
        byte[] b = new byte[] {0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x1};
        System.out.println(Arrays.toString(b));
        assertThat(Bytes.toLong(b)).isEqualTo(1);
    }

    @Test
    void byteToLongMinus() {
        byte[] b = Bytes.toByte(-1, Long.BYTES);
        System.out.println(Arrays.toString(b));
        assertThat(Bytes.toLong(b)).isEqualTo(-1);
    }

    @Test
    void sizeTest() {
        assertThatThrownBy(() -> Bytes.toByte(0, 0));
    }
}
