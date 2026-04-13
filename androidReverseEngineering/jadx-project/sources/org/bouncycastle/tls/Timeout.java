package org.bouncycastle.tls;

/* loaded from: classes.dex */
class Timeout {
    private long durationMillis;
    private long startMillis;

    public Timeout(long j2) {
        this(j2, System.currentTimeMillis());
    }

    public static int constrainWaitMillis(int i2, Timeout timeout, long j2) {
        int waitMillis;
        if (i2 >= 0 && (waitMillis = getWaitMillis(timeout, j2)) >= 0) {
            return i2 == 0 ? waitMillis : waitMillis == 0 ? i2 : Math.min(i2, waitMillis);
        }
        return -1;
    }

    public static Timeout forWaitMillis(int i2) {
        return forWaitMillis(i2, System.currentTimeMillis());
    }

    public static int getWaitMillis(Timeout timeout, long j2) {
        if (timeout == null) {
            return 0;
        }
        long remainingMillis = timeout.remainingMillis(j2);
        if (remainingMillis < 1) {
            return -1;
        }
        if (remainingMillis > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        return (int) remainingMillis;
    }

    public static boolean hasExpired(Timeout timeout, long j2) {
        return timeout != null && timeout.remainingMillis(j2) < 1;
    }

    public synchronized long remainingMillis(long j2) {
        long j3 = this.startMillis;
        if (j3 > j2) {
            this.startMillis = j2;
            return this.durationMillis;
        }
        long j4 = this.durationMillis - (j2 - j3);
        if (j4 > 0) {
            return j4;
        }
        this.durationMillis = 0L;
        return 0L;
    }

    public Timeout(long j2, long j3) {
        this.durationMillis = Math.max(0L, j2);
        this.startMillis = Math.max(0L, j3);
    }

    public static Timeout forWaitMillis(int i2, long j2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("'waitMillis' cannot be negative");
        }
        if (i2 > 0) {
            return new Timeout(i2, j2);
        }
        return null;
    }
}
