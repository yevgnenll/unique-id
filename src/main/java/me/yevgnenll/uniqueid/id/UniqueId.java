package me.yevgnenll.uniqueid.id;

public abstract class UniqueId {

    public abstract Long toLong();
    public abstract String toBase62();

    public abstract long getShardId();
}
