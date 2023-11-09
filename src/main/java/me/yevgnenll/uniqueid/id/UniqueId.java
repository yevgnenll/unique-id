package me.yevgnenll.uniqueid.id;

import java.security.SecureRandom;

public abstract class UniqueId extends Number {

    public abstract Long toLong();
    public abstract String toBase62();
    public abstract String toBase32();

    public abstract long getTimestamp();
    public abstract long getShardId();
    public abstract long getSequence();

    protected static class RandomHolder {
        static final SecureRandom generatedNumber = new SecureRandom();
    }

    protected static long currentShardId() {
        return RandomHolder.generatedNumber.nextLong();
    }
}
