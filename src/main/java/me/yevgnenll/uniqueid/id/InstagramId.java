package me.yevgnenll.uniqueid.id;

public class InstagramId extends UniqueId implements Comparable<InstagramId> {

    private static final long serialVersionUID = 7526471155622776147L;

    private final long value;

    private InstagramId(long value) {
        this.value = value;
    }

    enum Component {
        TITEMSTAMP(41),
        SHARD(13),
        SEQUENCE(10),
        ;

        private final long bit;

        Component(long bit) {
            this.bit = bit;
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
    }

    @Override
    public Long toLong() {
        return null;
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
        return 0;
    }
}
