package com.guard.wallet.utils;

/**
 * SnowflakeIdGenerator -- Snowflake ID 生成器。
 * 格式: timestamp(22bit) | dataCenterId(5bit) | workerId(5bit) | sequence(12bit)
 * 基准时间: 2019-08-06 (1565020800000L)
 * vendor 原始类名: com.guard.wallet.utils.i
 */
public final class SnowflakeIdGenerator {
    private static final long EPOCH = 1565020800000L;
    private static final long WORKER_ID = 1000L;
    private static final int SEQUENCE_BITS = 12;
    private static final int WORKER_ID_BITS = 5;
    private static final int TIMESTAMP_LEFT_SHIFT = 22;
    private static final int DATACENTER_LEFT_SHIFT = 17;
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    public final long dataCenterId;
    public long sequence = 0L;
    public long lastTimestamp = -1L;

    public SnowflakeIdGenerator(long dataCenterId) {
        if (dataCenterId > 31L || dataCenterId < 0L) {
            throw new IllegalArgumentException(
                String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
        this.dataCenterId = dataCenterId;
    }

    public final synchronized long nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0L) {
                while (timestamp <= lastTimestamp) {
                    timestamp = System.currentTimeMillis();
                }
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (WORKER_ID << DATACENTER_LEFT_SHIFT)
                | (dataCenterId << SEQUENCE_BITS)
                | sequence;
    }
}
