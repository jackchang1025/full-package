package org.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.prng.DigestRandomGenerator;
import org.bouncycastle.pqc.crypto.MessageEncryptor;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;
import org.bouncycastle.pqc.math.linearalgebra.GF2Vector;

/* loaded from: classes.dex */
public class McEliecePointchevalCipher implements MessageEncryptor {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.2.2";
    private boolean forEncryption;

    /* renamed from: k */
    private int f1595k;
    McElieceCCA2KeyParameters key;
    private Digest messDigest;

    /* renamed from: n */
    private int f1596n;
    private SecureRandom sr;

    /* renamed from: t */
    private int f1597t;

    private void initCipherDecrypt(McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters) {
        this.messDigest = Utils.getDigest(mcElieceCCA2PrivateKeyParameters.getDigest());
        this.f1596n = mcElieceCCA2PrivateKeyParameters.getN();
        this.f1595k = mcElieceCCA2PrivateKeyParameters.getK();
        this.f1597t = mcElieceCCA2PrivateKeyParameters.getT();
    }

    private void initCipherEncrypt(McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters) {
        this.messDigest = Utils.getDigest(mcElieceCCA2PublicKeyParameters.getDigest());
        this.f1596n = mcElieceCCA2PublicKeyParameters.getN();
        this.f1595k = mcElieceCCA2PublicKeyParameters.getK();
        this.f1597t = mcElieceCCA2PublicKeyParameters.getT();
    }

    public int decryptOutputSize(int i2) {
        return 0;
    }

    public int encryptOutputSize(int i2) {
        return 0;
    }

    public int getKeySize(McElieceCCA2KeyParameters mcElieceCCA2KeyParameters) {
        if (mcElieceCCA2KeyParameters instanceof McElieceCCA2PublicKeyParameters) {
            return ((McElieceCCA2PublicKeyParameters) mcElieceCCA2KeyParameters).getN();
        }
        if (mcElieceCCA2KeyParameters instanceof McElieceCCA2PrivateKeyParameters) {
            return ((McElieceCCA2PrivateKeyParameters) mcElieceCCA2KeyParameters).getN();
        }
        throw new IllegalArgumentException("unsupported type");
    }

    @Override // org.bouncycastle.pqc.crypto.MessageEncryptor
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forEncryption = z2;
        if (!z2) {
            McElieceCCA2PrivateKeyParameters mcElieceCCA2PrivateKeyParameters = (McElieceCCA2PrivateKeyParameters) cipherParameters;
            this.key = mcElieceCCA2PrivateKeyParameters;
            initCipherDecrypt(mcElieceCCA2PrivateKeyParameters);
        } else {
            if (!(cipherParameters instanceof ParametersWithRandom)) {
                this.sr = CryptoServicesRegistrar.getSecureRandom();
                McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters = (McElieceCCA2PublicKeyParameters) cipherParameters;
                this.key = mcElieceCCA2PublicKeyParameters;
                initCipherEncrypt(mcElieceCCA2PublicKeyParameters);
                return;
            }
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.sr = parametersWithRandom.getRandom();
            McElieceCCA2PublicKeyParameters mcElieceCCA2PublicKeyParameters2 = (McElieceCCA2PublicKeyParameters) parametersWithRandom.getParameters();
            this.key = mcElieceCCA2PublicKeyParameters2;
            initCipherEncrypt(mcElieceCCA2PublicKeyParameters2);
        }
    }

    @Override // org.bouncycastle.pqc.crypto.MessageEncryptor
    public byte[] messageDecrypt(byte[] bArr) {
        if (this.forEncryption) {
            throw new IllegalStateException("cipher initialised for decryption");
        }
        int i2 = (this.f1596n + 7) >> 3;
        int length = bArr.length - i2;
        byte[][] split = ByteUtils.split(bArr, i2);
        byte[] bArr2 = split[0];
        byte[] bArr3 = split[1];
        GF2Vector[] decryptionPrimitive = McElieceCCA2Primitives.decryptionPrimitive((McElieceCCA2PrivateKeyParameters) this.key, GF2Vector.OS2VP(this.f1596n, bArr2));
        byte[] encoded = decryptionPrimitive[0].getEncoded();
        GF2Vector gF2Vector = decryptionPrimitive[1];
        DigestRandomGenerator digestRandomGenerator = new DigestRandomGenerator(new SHA1Digest());
        digestRandomGenerator.addSeedMaterial(encoded);
        byte[] bArr4 = new byte[length];
        digestRandomGenerator.nextBytes(bArr4);
        for (int i3 = 0; i3 < length; i3++) {
            bArr4[i3] = (byte) (bArr4[i3] ^ bArr3[i3]);
        }
        this.messDigest.update(bArr4, 0, length);
        byte[] bArr5 = new byte[this.messDigest.getDigestSize()];
        this.messDigest.doFinal(bArr5, 0);
        if (Conversions.encode(this.f1596n, this.f1597t, bArr5).equals(gF2Vector)) {
            return ByteUtils.split(bArr4, length - (this.f1595k >> 3))[0];
        }
        throw new InvalidCipherTextException("Bad Padding: Invalid ciphertext.");
    }

    @Override // org.bouncycastle.pqc.crypto.MessageEncryptor
    public byte[] messageEncrypt(byte[] bArr) {
        if (!this.forEncryption) {
            throw new IllegalStateException("cipher initialised for decryption");
        }
        int i2 = this.f1595k >> 3;
        byte[] bArr2 = new byte[i2];
        this.sr.nextBytes(bArr2);
        GF2Vector gF2Vector = new GF2Vector(this.f1595k, this.sr);
        byte[] encoded = gF2Vector.getEncoded();
        byte[] concatenate = ByteUtils.concatenate(bArr, bArr2);
        this.messDigest.update(concatenate, 0, concatenate.length);
        byte[] bArr3 = new byte[this.messDigest.getDigestSize()];
        this.messDigest.doFinal(bArr3, 0);
        byte[] encoded2 = McElieceCCA2Primitives.encryptionPrimitive((McElieceCCA2PublicKeyParameters) this.key, gF2Vector, Conversions.encode(this.f1596n, this.f1597t, bArr3)).getEncoded();
        DigestRandomGenerator digestRandomGenerator = new DigestRandomGenerator(new SHA1Digest());
        digestRandomGenerator.addSeedMaterial(encoded);
        byte[] bArr4 = new byte[bArr.length + i2];
        digestRandomGenerator.nextBytes(bArr4);
        for (int i3 = 0; i3 < bArr.length; i3++) {
            bArr4[i3] = (byte) (bArr4[i3] ^ bArr[i3]);
        }
        for (int i4 = 0; i4 < i2; i4++) {
            int length = bArr.length + i4;
            bArr4[length] = (byte) (bArr4[length] ^ bArr2[i4]);
        }
        return ByteUtils.concatenate(encoded2, bArr4);
    }
}
