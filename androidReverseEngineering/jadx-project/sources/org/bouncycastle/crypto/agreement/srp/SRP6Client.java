package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.SRP6GroupParameters;

/* loaded from: classes.dex */
public class SRP6Client {

    /* renamed from: A */
    protected BigInteger f1138A;

    /* renamed from: B */
    protected BigInteger f1139B;
    protected BigInteger Key;
    protected BigInteger M1;
    protected BigInteger M2;

    /* renamed from: N */
    protected BigInteger f1140N;

    /* renamed from: S */
    protected BigInteger f1141S;

    /* renamed from: a */
    protected BigInteger f1142a;
    protected Digest digest;

    /* renamed from: g */
    protected BigInteger f1143g;
    protected SecureRandom random;

    /* renamed from: u */
    protected BigInteger f1144u;

    /* renamed from: x */
    protected BigInteger f1145x;

    private BigInteger calculateS() {
        BigInteger calculateK = SRP6Util.calculateK(this.digest, this.f1140N, this.f1143g);
        return this.f1139B.subtract(this.f1143g.modPow(this.f1145x, this.f1140N).multiply(calculateK).mod(this.f1140N)).mod(this.f1140N).modPow(this.f1144u.multiply(this.f1145x).add(this.f1142a), this.f1140N);
    }

    public BigInteger calculateClientEvidenceMessage() {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3 = this.f1138A;
        if (bigInteger3 == null || (bigInteger = this.f1139B) == null || (bigInteger2 = this.f1141S) == null) {
            throw new CryptoException("Impossible to compute M1: some data are missing from the previous operations (A,B,S)");
        }
        BigInteger calculateM1 = SRP6Util.calculateM1(this.digest, this.f1140N, bigInteger3, bigInteger, bigInteger2);
        this.M1 = calculateM1;
        return calculateM1;
    }

    public BigInteger calculateSecret(BigInteger bigInteger) {
        BigInteger validatePublicValue = SRP6Util.validatePublicValue(this.f1140N, bigInteger);
        this.f1139B = validatePublicValue;
        this.f1144u = SRP6Util.calculateU(this.digest, this.f1140N, this.f1138A, validatePublicValue);
        BigInteger calculateS = calculateS();
        this.f1141S = calculateS;
        return calculateS;
    }

    public BigInteger calculateSessionKey() {
        BigInteger bigInteger = this.f1141S;
        if (bigInteger == null || this.M1 == null || this.M2 == null) {
            throw new CryptoException("Impossible to compute Key: some data are missing from the previous operations (S,M1,M2)");
        }
        BigInteger calculateKey = SRP6Util.calculateKey(this.digest, this.f1140N, bigInteger);
        this.Key = calculateKey;
        return calculateKey;
    }

    public BigInteger generateClientCredentials(byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.f1145x = SRP6Util.calculateX(this.digest, this.f1140N, bArr, bArr2, bArr3);
        BigInteger selectPrivateValue = selectPrivateValue();
        this.f1142a = selectPrivateValue;
        BigInteger modPow = this.f1143g.modPow(selectPrivateValue, this.f1140N);
        this.f1138A = modPow;
        return modPow;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, Digest digest, SecureRandom secureRandom) {
        this.f1140N = bigInteger;
        this.f1143g = bigInteger2;
        this.digest = digest;
        this.random = secureRandom;
    }

    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.digest, this.f1140N, this.f1143g, this.random);
    }

    public boolean verifyServerEvidenceMessage(BigInteger bigInteger) {
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4 = this.f1138A;
        if (bigInteger4 == null || (bigInteger2 = this.M1) == null || (bigInteger3 = this.f1141S) == null) {
            throw new CryptoException("Impossible to compute and verify M2: some data are missing from the previous operations (A,M1,S)");
        }
        if (!SRP6Util.calculateM2(this.digest, this.f1140N, bigInteger4, bigInteger2, bigInteger3).equals(bigInteger)) {
            return false;
        }
        this.M2 = bigInteger;
        return true;
    }

    public void init(SRP6GroupParameters sRP6GroupParameters, Digest digest, SecureRandom secureRandom) {
        init(sRP6GroupParameters.getN(), sRP6GroupParameters.getG(), digest, secureRandom);
    }
}
