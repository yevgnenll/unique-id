package me.yevgnenll.uniqueid.id;

import java.util.concurrent.atomic.AtomicLong;

import static me.yevgnenll.uniqueid.id.InstagramId.Component.SHARD;

public class InstagramId extends UniqueId implements Comparable<InstagramId> {

    private static final long serialVersionUID = 7526471155622776147L;

    private static AtomicLong sequence = new AtomicLong(0);

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
    public static InstagramId makeId(long timestamp, long shardId, long sequence) {
        long value =  timestamp - OUT_EPOCH_TIME << Component.TITEMSTAMP.getShiftBits() |
                shardId << SHARD.getShiftBits() |
                sequence;
        return new InstagramId(value);
    }

    protected enum Component {
        TITEMSTAMP(41, 23),
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
}