package org.bouncycastle.crypto.params;

/* loaded from: classes.dex */
public class GOST3410ValidationParameters {

    /* renamed from: c */
    private int f1325c;
    private long cL;

    /* renamed from: x0, reason: collision with root package name */
    private int f2347x0;
    private long x0L;

    public GOST3410ValidationParameters(int i2, int i3) {
        this.f2347x0 = i2;
        this.f1325c = i3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GOST3410ValidationParameters)) {
            return false;
        }
        GOST3410ValidationParameters gOST3410ValidationParameters = (GOST3410ValidationParameters) obj;
        return gOST3410ValidationParameters.f1325c == this.f1325c && gOST3410ValidationParameters.f2347x0 == this.f2347x0 && gOST3410ValidationParameters.cL == this.cL && gOST3410ValidationParameters.x0L == this.x0L;
    }

    public int getC() {
        return this.f1325c;
    }

    public long getCL() {
        return this.cL;
    }

    public int getX0() {
        return this.f2347x0;
    }

    public long getX0L() {
        return this.x0L;
    }

    public int hashCode() {
        int i2 = this.f2347x0 ^ this.f1325c;
        long j2 = this.x0L;
        int i3 = (i2 ^ ((int) j2)) ^ ((int) (j2 >> 32));
        long j3 = this.cL;
        return (i3 ^ ((int) j3)) ^ ((int) (j3 >> 32));
    }

    public GOST3410ValidationParameters(long j2, long j3) {
        this.x0L = j2;
        this.cL = j3;
    }
}
