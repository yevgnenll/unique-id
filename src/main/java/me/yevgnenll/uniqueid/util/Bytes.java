package me.yevgnenll.uniqueid.util;

import java.nio.ByteBuffer;

public class Bytes {

    private static final int BIG_ENDIAN = 0;

    public static long toLong(byte[] value) {
        int pad = Long.BYTES - value.length;
        byte[] data = null;

        if (pad > 0) {
            data = new byte[Long.BYTES];
            System.arraycopy(value, 0, data, 0, value.length);
        } else {
            data = value;
        }

        ByteBuffer buffer = ByteBuffer.wrap(data);

        return buffer.getLong();
    }

    public static byte[] toByte(long value, int size) {
        Checks.isTrue(size > 0 && size <= Long.BYTES, "size must be smaller than Long.BYTES");

        ByteBuffer buf = ByteBuffer.allocate(Long.BYTES);
        buf.putLong(value);
        buf.position(0);
        byte[] raw = new byte[size];
        buf.get(raw, 0, size);
        return raw;
    }
}
