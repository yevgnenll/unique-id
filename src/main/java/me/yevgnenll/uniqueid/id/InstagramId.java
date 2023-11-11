package me.yevgnenll.uniqueid.id;

import com.google.common.io.BaseEncoding;
import me.yevgnenll.uniqueid.util.Bytes;
import me.yevgnenll.uniqueid.util.Checks;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicLong;

import static me.yevgnenll.uniqueid.id.InstagramId.Component.*;

public class InstagramId extends UniqueId implements Comparable<InstagramId> {

    private static final long serialVersionUID = 7526471155622776147L;

    private static AtomicLong sequence = new AtomicLong(0);
    private static final long SHARD_ID = SHARD.maskedValue(currentShardId());

    // 2023-01-01 00:00:00
    private static final long OUT_EPOCH_TIME = 1672498800000L;
    private final long value;

    private InstagramId(long value) {
        this.value = value;
    }

    /**
     * new instagram id
     *
     * 41 bits for time in milliseconds (gives us 41 years of IDs with a custom epoch)
     * 13 bits that represent the logical shard ID
     * 10 bits  that represent an auto-incrementing sequence, modulus 1024. This means we can generate 1024 IDs, per shard, per millisecond
     * @return this
     * @see <a href="http://instagram-engineering.tumblr.com/post/10853187575/sharding-ids-at-instagram"/>
     */
    static long makeRowId(long timestamp, long shardId, long sequence) {
        Checks.isTrue(timestamp >= OUT_EPOCH_TIME, "timestamp must be greather than, " + OUT_EPOCH_TIME);

        long modifiedTimestamp = timestamp - OUT_EPOCH_TIME;
        long maskedShardId = SHARD.maskedValue(shardId);
        long maskedSequenceId = SEQUENCE.maskedValue(sequence);

        return (modifiedTimestamp << TIMESTAMP.getShiftBits()) |
                (maskedShardId << SHARD.getShiftBits()) |
                (maskedSequenceId << SEQUENCE.getShiftBits());
    }

    public static InstagramId makeId(long timestamp, long shardId, long sequence) {
        return new InstagramId(makeRowId(timestamp, shardId, sequence));
    }


    protected enum Component {
        TIMESTAMP(41, 23),
        SHARD(13, 10),
        SEQUENCE(10, 0),
        ;

        private final long bit;
        private final long shiftBits;

        Component(long bit, long shiftBits) {
            this.bit = bit;
            this.shiftBits = shiftBits;
        }

        public long getBit() {
            return this.bit;
        }

        public long getMask() {
            // 1111
            // 1110
            // 0001
            return -1L ^ (-1L << this.bit);
        }

        public long getShiftBits() {
            return this.shiftBits;
        }

        public long maskedValue(long value) {
            return getMask() & value;
        }
    }

    private static long nextSequence() {
        return SEQUENCE.maskedValue(sequence.incrementAndGet());
    }

    public static InstagramId makeId() {
        return new InstagramId(makeRowId(System.currentTimeMillis(), SHARD_ID, nextSequence()));
    }

    public static long makeRowId() {
        return makeId().longValue();
    }

    public static InstagramId nextId() {
        return new InstagramId(makeRowId(System.currentTimeMillis(), SHARD_ID, nextSequence()));
    }

    @Override
    public Long toLong() {
        return value;
    }

    @Override
    public String toBase62() {
        return null;
    }

    @Override
    public int compareTo(InstagramId o) {
        return (int) (this.value - o.value);
    }

    @Override
    public long getShardId() {
        return SHARD.maskedValue(value >> SHARD.getShiftBits());
    }

    @Override
    public long getTimestamp() {
        return TIMESTAMP.maskedValue(value >> TIMESTAMP.getShiftBits()) + OUT_EPOCH_TIME;
    }

    @Override
    public long getSequence() {
        return SEQUENCE.maskedValue(value);
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return (double) value;
    }

    public static InstagramId lowerBound(Instant instant) {
        Checks.nonNull(instant, "instant must not be null");
        return lowerBound(instant.toEpochMilli());
    }

    public static InstagramId lowerBound() {
        return lowerBound(Clock.systemDefaultZone());
    }

    public static InstagramId lowerBound(Clock clock) {
        Checks.nonNull(clock, "clock must not be null");
        return lowerBound(clock.millis());
    }

    public static InstagramId lowerBound(long timestamp) {
        Checks.nonNull(timestamp, "timestamp must not be null");
        return makeId(timestamp, 0, 0);
    }

    public static InstagramId upperBound(Instant instant) {
        Checks.nonNull(instant, "instant must not be null");
        return upperBound(instant.toEpochMilli());
    }

    public static InstagramId upperBound(Clock clock) {
        Checks.nonNull(clock, "clock must not be null");
        return upperBound(clock.millis());
    }

    public static InstagramId upperBound(long timestamp) {
        return makeId(timestamp + 1, 0, 0);
    }

    public static InstagramId upperBound() {
        return upperBound(Clock.systemDefaultZone());
    }

    @Override
    public String toBase32() {
        return BaseEncoding.base32().encode(Bytes.toByte(value, Long.BYTES)).toLowerCase();
    }

    public static InstagramId withBase32(String base32) {
        Checks.nonNull(base32, "base32 value must not be null");
        return new InstagramId(Bytes.toLong(BaseEncoding.base32().decode(base32.toUpperCase())));
    }

    @Override
    public String toHex() {
        return BaseEncoding.base16().encode(Bytes.toByte(value, Long.BYTES));
    }

    public static InstagramId withHex(String hex) {
        Checks.nonNull(hex, "hex value must not be null");
        byte[] decode = BaseEncoding.base16().decode(hex);
        return new InstagramId(Bytes.toLong(decode));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InstagramId that = (InstagramId) o;

        return value == that.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }

    @Override
    public String toBase64() {
        byte[] encode = Bytes.toByte(value, Long.BYTES);
        return BaseEncoding.base64Url().encode(encode);
    }

    public static InstagramId withBase64(String base64Url) {
        Checks.nonNull(base64Url, "base64 must not be null");
        long value = Bytes.toLong(BaseEncoding.base64Url().decode(base64Url));
        return new InstagramId(value);
    }
}
