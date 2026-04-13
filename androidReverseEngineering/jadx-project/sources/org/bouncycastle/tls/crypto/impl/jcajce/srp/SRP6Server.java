package org.bouncycastle.tls.crypto.impl.jcajce.srp;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.tls.crypto.SRP6Group;
import org.bouncycastle.tls.crypto.TlsHash;

/* loaded from: classes.dex */
public class SRP6Server {

    /* renamed from: A */
    protected BigInteger f1650A;

    /* renamed from: B */
    protected BigInteger f1651B;
    protected BigInteger Key;
    protected BigInteger M1;
    protected BigInteger M2;

    /* renamed from: N */
    protected BigInteger f1652N;

    /* renamed from: S */
    protected BigInteger f1653S;

    /* renamed from: b */
    protected BigInteger f1654b;
    protected TlsHash digest;

    /* renamed from: g */
    protected BigInteger f1655g;
    protected SecureRandom random;

    /* renamed from: u */
    protected BigInteger f1656u;

    /* renamed from: v */
    protected BigInteger f1657v;

    private BigInteger calculateS() {
        return this.f1657v.modPow(this.f1656u, this.f1652N).multiply(this.f1650A).mod(this.f1652N).modPow(this.f1654b, this.f1652N);
    }

    public BigInteger calculateSecret(BigInteger bigInteger) {
        BigInteger validatePublicValue = SRP6Util.validatePublicValue(this.f1652N, bigInteger);
        this.f1650A = validatePublicValue;
        this.f1656u = SRP6Util.calculateU(this.digest, this.f1652N, validatePublicValue, this.f1651B);
        BigInteger calculateS = calculateS();
        this.f1653S = calculateS;
        return calculateS;
    }

    public BigInteger calculateServerEvidenceMessage() {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3 = this.f1650A;
        if (bigInteger3 == null || (bigInteger = this.M1) == null || (bigInteger2 = this.f1653S) == null) {
            throw new IllegalStateException("Impossible to compute M2: some data are missing from the previous operations (A,M1,S)");
        }
        BigInteger calculateM2 = SRP6Util.calculateM2(this.digest, this.f1652N, bigInteger3, bigInteger, bigInteger2);
        this.M2 = calculateM2;
        return calculateM2;
    }

    public BigInteger calculateSessionKey() {
        BigInteger bigInteger = this.f1653S;
        if (bigInteger == null || this.M1 == null || this.M2 == null) {
            throw new IllegalStateException("Impossible to compute Key: some data are missing from the previous operations (S,M1,M2)");
        }
        BigInteger calculateKey = SRP6Util.calculateKey(this.digest, this.f1652N, bigInteger);
        this.Key = calculateKey;
        return calculateKey;
    }

    public BigInteger generateServerCredentials() {
        BigInteger calculateK = SRP6Util.calculateK(this.digest, this.f1652N, this.f1655g);
        this.f1654b = selectPrivateValue();
        BigInteger mod = calculateK.multiply(this.f1657v).mod(this.f1652N).add(this.f1655g.modPow(this.f1654b, this.f1652N)).mod(this.f1652N);
        this.f1651B = mod;
        return mod;
    }

    public void init(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3, TlsHash tlsHash, SecureRandom secureRandom) {
        this.f1652N = bigInteger;
        this.f1655g = bigInteger2;
        this.f1657v = bigInteger3;
        this.random = secureRandom;
        this.digest = tlsHash;
    }

    public BigInteger selectPrivateValue() {
        return SRP6Util.generatePrivateValue(this.f1652N, this.f1655g, this.random);
    }

    public boolean verifyClientEvidenceMessage(BigInteger bigInteger) {
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4 = this.f1650A;
        if (bigInteger4 == null || (bigInteger2 = this.f1651B) == null || (bigInteger3 = this.f1653S) == null) {
            throw new IllegalStateException("Impossible to compute and verify M1: some data are missing from the previous operations (A,B,S)");
        }
        if (!SRP6Util.calculateM1(this.digest, this.f1652N, bigInteger4, bigInteger2, bigInteger3).equals(bigInteger)) {
            return false;
        }
        this.M1 = bigInteger;
        return true;
    }

    public void init(SRP6Group sRP6Group, BigInteger bigInteger, TlsHash tlsHash, SecureRandom secureRandom) {
        init(sRP6Group.getN(), sRP6Group.getG(), bigInteger, tlsHash, secureRandom);
    }
}
