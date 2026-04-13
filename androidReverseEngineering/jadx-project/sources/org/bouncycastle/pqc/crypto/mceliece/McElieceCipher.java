package org.bouncycastle.pqc.crypto.mceliece;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.pqc.crypto.MessageEncryptor;
import org.bouncycastle.pqc.math.linearalgebra.GF2Matrix;
import org.bouncycastle.pqc.math.linearalgebra.GF2Vector;
import org.bouncycastle.pqc.math.linearalgebra.GF2mField;
import org.bouncycastle.pqc.math.linearalgebra.GoppaCode;
import org.bouncycastle.pqc.math.linearalgebra.Permutation;
import org.bouncycastle.pqc.math.linearalgebra.PolynomialGF2mSmallM;

/* loaded from: classes.dex */
public class McElieceCipher implements MessageEncryptor {
    public static final String OID = "1.3.6.1.4.1.8301.3.1.3.4.1";
    public int cipherTextSize;
    private boolean forEncryption;

    /* renamed from: k */
    private int f1580k;
    private McElieceKeyParameters key;
    public int maxPlainTextSize;

    /* renamed from: n */
    private int f1581n;
    private SecureRandom sr;

    /* renamed from: t */
    private int f1582t;

    private byte[] computeMessage(GF2Vector gF2Vector) {
        byte[] encoded = gF2Vector.getEncoded();
        int length = encoded.length - 1;
        while (length >= 0 && encoded[length] == 0) {
            length--;
        }
        if (length < 0 || encoded[length] != 1) {
            throw new InvalidCipherTextException("Bad Padding: invalid ciphertext");
        }
        byte[] bArr = new byte[length];
        System.arraycopy(encoded, 0, bArr, 0, length);
        return bArr;
    }

    private GF2Vector computeMessageRepresentative(byte[] bArr) {
        byte[] bArr2 = new byte[this.maxPlainTextSize + ((this.f1580k & 7) != 0 ? 1 : 0)];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        bArr2[bArr.length] = 1;
        return GF2Vector.OS2VP(this.f1580k, bArr2);
    }

    private void initCipherDecrypt(McEliecePrivateKeyParameters mcEliecePrivateKeyParameters) {
        this.f1581n = mcEliecePrivateKeyParameters.getN();
        int k2 = mcEliecePrivateKeyParameters.getK();
        this.f1580k = k2;
        this.maxPlainTextSize = k2 >> 3;
        this.cipherTextSize = this.f1581n >> 3;
    }

    private void initCipherEncrypt(McEliecePublicKeyParameters mcEliecePublicKeyParameters) {
        this.f1581n = mcEliecePublicKeyParameters.getN();
        this.f1580k = mcEliecePublicKeyParameters.getK();
        this.f1582t = mcEliecePublicKeyParameters.getT();
        this.cipherTextSize = this.f1581n >> 3;
        this.maxPlainTextSize = this.f1580k >> 3;
    }

    public int getKeySize(McElieceKeyParameters mcElieceKeyParameters) {
        if (mcElieceKeyParameters instanceof McEliecePublicKeyParameters) {
            return ((McEliecePublicKeyParameters) mcElieceKeyParameters).getN();
        }
        if (mcElieceKeyParameters instanceof McEliecePrivateKeyParameters) {
            return ((McEliecePrivateKeyParameters) mcElieceKeyParameters).getN();
        }
        throw new IllegalArgumentException("unsupported type");
    }

    @Override // org.bouncycastle.pqc.crypto.MessageEncryptor
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forEncryption = z2;
        if (!z2) {
            McEliecePrivateKeyParameters mcEliecePrivateKeyParameters = (McEliecePrivateKeyParameters) cipherParameters;
            this.key = mcEliecePrivateKeyParameters;
            initCipherDecrypt(mcEliecePrivateKeyParameters);
        } else {
            if (!(cipherParameters instanceof ParametersWithRandom)) {
                this.sr = CryptoServicesRegistrar.getSecureRandom();
                McEliecePublicKeyParameters mcEliecePublicKeyParameters = (McEliecePublicKeyParameters) cipherParameters;
                this.key = mcEliecePublicKeyParameters;
                initCipherEncrypt(mcEliecePublicKeyParameters);
                return;
            }
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.sr = parametersWithRandom.getRandom();
            McEliecePublicKeyParameters mcEliecePublicKeyParameters2 = (McEliecePublicKeyParameters) parametersWithRandom.getParameters();
            this.key = mcEliecePublicKeyParameters2;
            initCipherEncrypt(mcEliecePublicKeyParameters2);
        }
    }

    @Override // org.bouncycastle.pqc.crypto.MessageEncryptor
    public byte[] messageDecrypt(byte[] bArr) {
        if (this.forEncryption) {
            throw new IllegalStateException("cipher initialised for decryption");
        }
        GF2Vector OS2VP = GF2Vector.OS2VP(this.f1581n, bArr);
        McEliecePrivateKeyParameters mcEliecePrivateKeyParameters = (McEliecePrivateKeyParameters) this.key;
        GF2mField field = mcEliecePrivateKeyParameters.getField();
        PolynomialGF2mSmallM goppaPoly = mcEliecePrivateKeyParameters.getGoppaPoly();
        GF2Matrix sInv = mcEliecePrivateKeyParameters.getSInv();
        Permutation p1 = mcEliecePrivateKeyParameters.getP1();
        Permutation p2 = mcEliecePrivateKeyParameters.getP2();
        GF2Matrix h2 = mcEliecePrivateKeyParameters.getH();
        PolynomialGF2mSmallM[] qInv = mcEliecePrivateKeyParameters.getQInv();
        Permutation rightMultiply = p1.rightMultiply(p2);
        GF2Vector gF2Vector = (GF2Vector) OS2VP.multiply(rightMultiply.computeInverse());
        GF2Vector syndromeDecode = GoppaCode.syndromeDecode((GF2Vector) h2.rightMultiply(gF2Vector), field, goppaPoly, qInv);
        GF2Vector gF2Vector2 = (GF2Vector) ((GF2Vector) gF2Vector.add(syndromeDecode)).multiply(p1);
        return computeMessage((GF2Vector) sInv.leftMultiply(gF2Vector2.extractRightVector(this.f1580k)));
    }

    @Override // org.bouncycastle.pqc.crypto.MessageEncryptor
    public byte[] messageEncrypt(byte[] bArr) {
        if (!this.forEncryption) {
            throw new IllegalStateException("cipher initialised for decryption");
        }
        GF2Vector computeMessageRepresentative = computeMessageRepresentative(bArr);
        return ((GF2Vector) ((McEliecePublicKeyParameters) this.key).getG().leftMultiply(computeMessageRepresentative).add(new GF2Vector(this.f1581n, this.f1582t, this.sr))).getEncoded();
    }
}
