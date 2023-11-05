package me.yevgnenll.uniqueid.id;

public class InstagramId extends UniqueId implements Comparable<InstagramId> {

    private final long value;

    private InstagramId(long value) {
        this.value = value;
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
}
