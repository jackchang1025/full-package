package org.bouncycastle.pkix.jcajce;

import org.bouncycastle.asn1.x509.ReasonFlags;

/* loaded from: classes.dex */
class ReasonsMask {
    static final ReasonsMask allReasons = new ReasonsMask(33023);
    private int _reasons;

    public ReasonsMask() {
        this(0);
    }

    public void addReasons(ReasonsMask reasonsMask) {
        this._reasons = reasonsMask.getReasons() | this._reasons;
    }

    public int getReasons() {
        return this._reasons;
    }

    public boolean hasNewReasons(ReasonsMask reasonsMask) {
        return ((reasonsMask.getReasons() ^ this._reasons) | this._reasons) != 0;
    }

    public ReasonsMask intersect(ReasonsMask reasonsMask) {
        ReasonsMask reasonsMask2 = new ReasonsMask();
        reasonsMask2.addReasons(new ReasonsMask(reasonsMask.getReasons() & this._reasons));
        return reasonsMask2;
    }

    public boolean isAllReasons() {
        return this._reasons == allReasons._reasons;
    }

    private ReasonsMask(int i2) {
        this._reasons = i2;
    }

    public ReasonsMask(ReasonFlags reasonFlags) {
        this._reasons = reasonFlags.intValue();
    }
}
