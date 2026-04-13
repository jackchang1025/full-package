package org.bouncycastle.jcajce.provider.asymmetric.dstu;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.ua.DSTU4145Params;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DSAExt;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.GOST3411Digest;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.signers.DSTU4145Signer;
import org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.bouncycastle.jce.interfaces.ECKey;

/* loaded from: classes.dex */
public class SignatureSpi extends java.security.SignatureSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers {
    private Digest digest;
    private DSAExt signer = new DSTU4145Signer();

    @Override // java.security.SignatureSpi
    public Object engineGetParameter(String str) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public AlgorithmParameters engineGetParameters() {
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0034  */
    @Override // java.security.SignatureSpi
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void engineInitSign(PrivateKey privateKey) {
        AsymmetricKeyParameter asymmetricKeyParameter;
        GOST3411Digest gOST3411Digest;
        SecureRandom secureRandom;
        if (privateKey instanceof BCDSTU4145PrivateKey) {
            asymmetricKeyParameter = ECUtil.generatePrivateKeyParameter(privateKey);
            gOST3411Digest = new GOST3411Digest(expandSbox(DSTU4145Params.getDefaultDKE()));
        } else {
            if (!(privateKey instanceof ECKey)) {
                asymmetricKeyParameter = null;
                secureRandom = ((java.security.SignatureSpi) this).appRandom;
                if (secureRandom == null) {
                    this.signer.init(true, new ParametersWithRandom(asymmetricKeyParameter, secureRandom));
                    return;
                } else {
                    this.signer.init(true, asymmetricKeyParameter);
                    return;
                }
            }
            asymmetricKeyParameter = ECUtil.generatePrivateKeyParameter(privateKey);
            gOST3411Digest = new GOST3411Digest(expandSbox(DSTU4145Params.getDefaultDKE()));
        }
        this.digest = gOST3411Digest;
        secureRandom = ((java.security.SignatureSpi) this).appRandom;
        if (secureRandom == null) {
        }
    }

    @Override // java.security.SignatureSpi
    public void engineInitVerify(PublicKey publicKey) {
        CipherParameters generatePublicKeyParameter;
        if (publicKey instanceof BCDSTU4145PublicKey) {
            BCDSTU4145PublicKey bCDSTU4145PublicKey = (BCDSTU4145PublicKey) publicKey;
            generatePublicKeyParameter = bCDSTU4145PublicKey.engineGetKeyParameters();
            this.digest = new GOST3411Digest(expandSbox(bCDSTU4145PublicKey.getSbox()));
        } else {
            generatePublicKeyParameter = ECUtil.generatePublicKeyParameter(publicKey);
            this.digest = new GOST3411Digest(expandSbox(DSTU4145Params.getDefaultDKE()));
        }
        this.signer.init(false, generatePublicKeyParameter);
    }

    @Override // java.security.SignatureSpi
    public void engineSetParameter(String str, Object obj) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public byte[] engineSign() {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        try {
            BigInteger[] generateSignature = this.signer.generateSignature(bArr);
            byte[] byteArray = generateSignature[0].toByteArray();
            byte[] byteArray2 = generateSignature[1].toByteArray();
            int length = (byteArray.length > byteArray2.length ? byteArray.length : byteArray2.length) * 2;
            byte[] bArr2 = new byte[length];
            System.arraycopy(byteArray2, 0, bArr2, (length / 2) - byteArray2.length, byteArray2.length);
            System.arraycopy(byteArray, 0, bArr2, length - byteArray.length, byteArray.length);
            return new DEROctetString(bArr2).getEncoded();
        } catch (Exception e2) {
            throw new SignatureException(e2.toString());
        }
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte b) {
        this.digest.update(b);
    }

    @Override // java.security.SignatureSpi
    public boolean engineVerify(byte[] bArr) {
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        try {
            byte[] octets = ((ASN1OctetString) ASN1Primitive.fromByteArray(bArr)).getOctets();
            byte[] bArr3 = new byte[octets.length / 2];
            byte[] bArr4 = new byte[octets.length / 2];
            System.arraycopy(octets, 0, bArr4, 0, octets.length / 2);
            System.arraycopy(octets, octets.length / 2, bArr3, 0, octets.length / 2);
            BigInteger bigInteger = new BigInteger(1, bArr4);
            return this.signer.verifySignature(bArr2, new BigInteger[]{new BigInteger(1, bArr3), bigInteger}[0], bigInteger);
        } catch (Exception unused) {
            throw new SignatureException("error decoding signature bytes.");
        }
    }

    public byte[] expandSbox(byte[] bArr) {
        byte[] bArr2 = new byte[128];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i2 * 2;
            bArr2[i3] = (byte) ((bArr[i2] >> 4) & 15);
            bArr2[i3 + 1] = (byte) (bArr[i2] & 15);
        }
        return bArr2;
    }

    @Override // java.security.SignatureSpi
    public void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }
}
