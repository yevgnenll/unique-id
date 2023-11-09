package me.yevgnenll.uniqueid.id;

import me.yevgnenll.uniqueid.util.Bytes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class InstagramIdTest {

    @Test
    @DisplayName(value = "component에 해당하는 값을 마스킹하기 위한 마스크 값")
    void getMaskTest() {
        // 13 = 8 + 4 + 2 + 1
        // 13개 비트가 1로 채워져 있어야 한다.
        long shardMaskValue = InstagramId.Component.SHARD.getMask();
        assertThat(shardMaskValue).isEqualTo(8191);
        assertThat(Bytes.toByte(shardMaskValue, 8)).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0x1f, -1});
        assertThat(Bytes.toByte(shardMaskValue, 8)).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 31, -1});
        System.out.println(shardMaskValue + ", " + Arrays.toString(Bytes.toByte(shardMaskValue, 8)));

        // 10개 비트가 1로 채워져야 한다
        long seqMaskValue = InstagramId.Component.SEQUENCE.getMask();
        assertThat(seqMaskValue).isEqualTo(1023);
        assertThat(Bytes.toByte(seqMaskValue, 8)).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 0x3, -1});
        assertThat(Bytes.toByte(seqMaskValue, 8)).isEqualTo(new byte[] {0, 0, 0, 0, 0, 0, 3, -1});
        System.out.println(Arrays.toString(Bytes.toByte(seqMaskValue, 8)));

        // 41개 비트가 1로 채워져야 한다.
        long timestampMaskValue = InstagramId.Component.TIMESTAMP.getMask();
        assertThat(timestampMaskValue).isEqualTo(2199023255551L);
        assertThat(Bytes.toByte(timestampMaskValue, 8)).isEqualTo(new byte[] {0, 0, 1, -1, -1, -1, -1, -1});
        System.out.println(timestampMaskValue + ", " + Arrays.toString(Bytes.toByte(timestampMaskValue, 8)));
    }

    @Test
    void exportShardId() {
        InstagramId id = InstagramId.makeId(1672498800001L, 10, 1);
        assertThat(id.getShardId()).isEqualTo(10);
    }

    @Test
    void exportTimestamp() {
        InstagramId id = InstagramId.makeId(1672498800001L, 10, 1);
        assertThat(id.getTimestamp()).isEqualTo(1672498800001L);
    }

    @Test
    void exportSequence() {
        InstagramId id = InstagramId.makeId(1672498800001L, 10, 15);
        assertThat(id.getSequence()).isEqualTo(15L);
    }

    @Test
    void upperBound() {
        InstagramId id = InstagramId.makeId();
        InstagramId upperBound = InstagramId.upperBound(id.getTimestamp());
        assertThat(upperBound.getTimestamp()).isEqualTo(id.getTimestamp() + 1);
        assertThat(upperBound.getSequence()).isEqualTo(0);
        assertThat(upperBound.getShardId()).isEqualTo(0);
    }

    @Test
    void lowerBound() {
        InstagramId id = InstagramId.makeId();
        InstagramId lowerBound = InstagramId.lowerBound(id.getTimestamp());
        assertThat(lowerBound.getTimestamp()).isEqualTo(lowerBound.getTimestamp());
        assertThat(lowerBound.getSequence()).isEqualTo(0);
        assertThat(lowerBound.getShardId()).isEqualTo(0);
    }
}