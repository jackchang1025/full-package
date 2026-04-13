package org.bouncycastle.crypto.agreement.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.SRP6GroupParameters;

/* loaded from: classes.dex */
public class SRP6Server {

    /* renamed from: A */
    protected BigInteger f1146A;

    /* renamed from: B */
    protected BigInteger f1147B;
    protected BigInteger Key;
    protected BigInteger M1;
    protected BigInteger M2;

    /* renamed from: N */
    protected BigInteger f1148N;

    /* renamed from: S */
    protected BigInteger f1149S;

    /* renamed from: b */
    protected BigInteger f1150b;
    protected Digest digest;

    /* renamed from: g */
    protected BigInteger f1151g;
    protected SecureRandom random;

    /* renamed from: u */
    protected BigInteger f1152u;

    /* renamed from: v */
    protected BigInteger f1153v;

    private BigInteger calculateS() {
        return this.f1153v.modPow(this.f1152u, this.f1148N).multiply(this.f1146A).mod(this.f1148N).modPow(this.f1150b, this.f1148N);
    }

    public BigInteger calculateSecret(BigInteger bigInteger) {
        BigInteger validatePublicValue = SRP6Util.validatePublicValue(this.f1148N, bigInteger);
        this.f1146A = validatePublicValue;
        this.f1152u = SRP6Util.calculateU(this.digest, this.f1148N, validatePublicValue, this.f1147B);
        BigInteger calculateS = calculateS();
        this.f1149S = calculateS;
        return calculateS;
    }

    public BigInteger calculateServerEvidenceMessage() {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3 = this.f1146A;
        if (bigInteger3 == null || (bigInteger = this.M1) == null || (bigInteger2 = this.f1149S) == null) {
            throw new CryptoException("Impossible to compute M2: some data are missing from the previous operations (A,M1,S)");
        }
        BigInteger calculateM2 = SRP6Util.calculateM2(this.digest, this.f1148N, bigInteger3, bigInteger, bigInteger2);
        this.M2 = calculateM2;
        return calculateM2;
    }

    public BigInteger calculateSessionKey() {
        BigInteger bigInteger = this.f1149S;
        if (bigInteger == null || this.M1 == null || this.M2 == null) {
            throw new CryptoException("Impossible to compute Key: some data are missing from the previous operations (S,M1,M2)");
        }
        BigInteger calculateKey = SRP6Util.calculateKey(this.digest, this.f1148N, bigInteger);
        this.Key = calculateKey;
        return calculateKey;
    }

    public BigInteger generateServerCredentials() {
        BigInteger calculateK = SRP6Util.calculateK(this.digest, this.f1148N, this.f1151g);
        this.f1150b = selectPrivateValue();
        BigInteger mod = calculateK.multiply(this.f1153v).mod(this.f1148N).add(this.f1151g.modPow(this.f1150b, this.f1148N)).mod(this.f1148N);
        this.f1147B = mod;
        return mod;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, Digest digest, SecureRandom secureRandom) {
        this.f1148N = bigInteger;
        this.f1151g = bigInteger2;
        this.f1153v = bigInteger3;
        this.random = secureRandom;
        this.digest = digest;
    }

    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.digest, this.f1148N, this.f1151g, this.random);
    }

    public boolean verifyClientEvidenceMessage(BigInteger bigInteger) {
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4 = this.f1146A;
        if (bigInteger4 == null || (bigInteger2 = this.f1147B) == null || (bigInteger3 = this.f1149S) == null) {
            throw new CryptoException("Impossible to compute and verify M1: some data are missing from the previous operations (A,B,S)");
        }
        if (!SRP6Util.calculateM1(this.digest, this.f1148N, bigInteger4, bigInteger2, bigInteger3).equals(bigInteger)) {
            return false;
        }
        this.M1 = bigInteger;
        return true;
    }

    public void init(SRP6GroupParameters sRP6GroupParameters, BigInteger bigInteger, Digest digest, SecureRandom secureRandom) {
        init(sRP6GroupParameters.getN(), sRP6GroupParameters.getG(), bigInteger, digest, secureRandom);
    }
}
