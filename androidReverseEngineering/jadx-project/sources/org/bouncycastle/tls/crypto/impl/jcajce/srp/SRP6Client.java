package org.bouncycastle.tls.crypto.impl.jcajce.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.tls.crypto.SRP6Group;
import org.bouncycastle.tls.crypto.TlsHash;

/* loaded from: classes.dex */
public class SRP6Client {

    /* renamed from: A */
    protected BigInteger f1642A;

    /* renamed from: B */
    protected BigInteger f1643B;
    protected BigInteger Key;
    protected BigInteger M1;
    protected BigInteger M2;

    /* renamed from: N */
    protected BigInteger f1644N;

    /* renamed from: S */
    protected BigInteger f1645S;

    /* renamed from: a */
    protected BigInteger f1646a;
    protected TlsHash digest;

    /* renamed from: g */
    protected BigInteger f1647g;
    protected SecureRandom random;

    /* renamed from: u */
    protected BigInteger f1648u;

    /* renamed from: x */
    protected BigInteger f1649x;

    private BigInteger calculateS() {
        BigInteger calculateK = SRP6Util.calculateK(this.digest, this.f1644N, this.f1647g);
        return this.f1643B.subtract(this.f1647g.modPow(this.f1649x, this.f1644N).multiply(calculateK).mod(this.f1644N)).mod(this.f1644N).modPow(this.f1648u.multiply(this.f1649x).add(this.f1646a), this.f1644N);
    }

    public BigInteger calculateClientEvidenceMessage() {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3 = this.f1642A;
        if (bigInteger3 == null || (bigInteger = this.f1643B) == null || (bigInteger2 = this.f1645S) == null) {
            throw new IllegalStateException("Impossible to compute M1: some data are missing from the previous operations (A,B,S)");
        }
        BigInteger calculateM1 = SRP6Util.calculateM1(this.digest, this.f1644N, bigInteger3, bigInteger, bigInteger2);
        this.M1 = calculateM1;
        return calculateM1;
    }

    public BigInteger calculateSecret(BigInteger bigInteger) {
        BigInteger validatePublicValue = SRP6Util.validatePublicValue(this.f1644N, bigInteger);
        this.f1643B = validatePublicValue;
        this.f1648u = SRP6Util.calculateU(this.digest, this.f1644N, this.f1642A, validatePublicValue);
        BigInteger calculateS = calculateS();
        this.f1645S = calculateS;
        return calculateS;
    }

    public BigInteger calculateSessionKey() {
        BigInteger bigInteger = this.f1645S;
        if (bigInteger == null || this.M1 == null || this.M2 == null) {
            throw new IllegalStateException("Impossible to compute Key: some data are missing from the previous operations (S,M1,M2)");
        }
        BigInteger calculateKey = SRP6Util.calculateKey(this.digest, this.f1644N, bigInteger);
        this.Key = calculateKey;
        return calculateKey;
    }

    public BigInteger generateClientCredentials(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.f1649x = SRP6Util.calculateX(this.digest, this.f1644N, bArr, bArr2, bArr3);
        BigInteger selectPrivateValue = selectPrivateValue();
        this.f1646a = selectPrivateValue;
        BigInteger modPow = this.f1647g.modPow(selectPrivateValue, this.f1644N);
        this.f1642A = modPow;
        return modPow;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, TlsHash tlsHash, SecureRandom secureRandom) {
        this.f1644N = bigInteger;
        this.f1647g = bigInteger2;
        this.digest = tlsHash;
        this.random = secureRandom;
    }

    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.f1644N, this.f1647g, this.random);
    }

    public boolean verifyServerEvidenceMessage(BigInteger bigInteger) {
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4 = this.f1642A;
        if (bigInteger4 == null || (bigInteger2 = this.M1) == null || (bigInteger3 = this.f1645S) == null) {
            throw new IllegalStateException("Impossible to compute and verify M2: some data are missing from the previous operations (A,M1,S)");
        }
        if (!SRP6Util.calculateM2(this.digest, this.f1644N, bigInteger4, bigInteger2, bigInteger3).equals(bigInteger)) {
            return false;
        }
        this.M2 = bigInteger;
        return true;
    }

    public void init(SRP6Group sRP6Group, TlsHash tlsHash, SecureRandom secureRandom) {
        init(sRP6Group.getN(), sRP6Group.getG(), tlsHash, secureRandom);
    }
}
