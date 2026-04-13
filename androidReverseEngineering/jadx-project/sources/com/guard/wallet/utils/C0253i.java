package com.guard.wallet.utils;

/* renamed from: com.guard.wallet.utils.i */
/* loaded from: classes.dex */
public final class C0253i {

    /* renamed from: a */
    public final long f412a;

    /* renamed from: b */
    public long f413b = 0;

    /* renamed from: c */
    public long f414c = -1;

    public C0253i(long j2) {
        if (j2 > 31 || j2 < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
        long j3 = ~((-1) << ((int) 1000));
        if (1000 > j3) {
            throw new IllegalArgumentException(String.format("maxDataCenterId Id can't be greater than %d or less than 0", Long.valueOf(j3)));
        }
        this.f412a = j2;
    }

    /* renamed from: a */
    public final synchronized long m723a() {
        long currentTimeMillis;
        currentTimeMillis = System.currentTimeMillis();
        long j2 = this.f414c;
        if (currentTimeMillis < j2) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", Long.valueOf(this.f414c - currentTimeMillis)));
        }
        if (j2 == currentTimeMillis) {
            long j3 = (~((-1) << ((int) 12))) & (this.f413b + 1);
            this.f413b = j3;
            if (j3 == 0) {
                do {
                    currentTimeMillis = System.currentTimeMillis();
                } while (currentTimeMillis <= j2);
            }
        } else {
            this.f413b = 0L;
        }
        this.f414c = currentTimeMillis;
        return ((currentTimeMillis - 1565020800000L) << ((int) 22)) | (1000 << ((int) 17)) | (this.f412a << ((int) 12)) | this.f413b;
    }
}
