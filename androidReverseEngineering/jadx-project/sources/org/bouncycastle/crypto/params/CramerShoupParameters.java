package org.bouncycastle.crypto.params;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Memoable;

/* loaded from: classes.dex */
public class CramerShoupParameters implements CipherParameters {

    /* renamed from: H */
    private Digest f1289H;

    /* renamed from: g1, reason: collision with root package name */
    private BigInteger f2346g1;
    private BigInteger g2;

    /* renamed from: p */
    private BigInteger f1290p;

    public CramerShoupParameters(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, Digest digest) {
        this.f1290p = bigInteger;
        this.f2346g1 = bigInteger2;
        this.g2 = bigInteger3;
        Digest digest2 = (Digest) ((Memoable) digest).copy();
        this.f1289H = digest2;
        digest2.reset();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CramerShoupParameters)) {
            return false;
        }
        CramerShoupParameters cramerShoupParameters = (CramerShoupParameters) obj;
        return cramerShoupParameters.getP().equals(this.f1290p) && cramerShoupParameters.getG1().equals(this.f2346g1) && cramerShoupParameters.getG2().equals(this.g2);
    }

    public BigInteger getG1() {
        return this.f2346g1;
    }

    public BigInteger getG2() {
        return this.g2;
    }

    public Digest getH() {
        return (Digest) ((Memoable) this.f1289H).copy();
    }

    public BigInteger getP() {
        return this.f1290p;
    }

    public int hashCode() {
        return (getP().hashCode() ^ getG1().hashCode()) ^ getG2().hashCode();
    }
}
