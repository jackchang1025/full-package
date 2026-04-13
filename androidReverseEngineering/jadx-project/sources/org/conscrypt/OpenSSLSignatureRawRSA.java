package org.conscrypt;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class OpenSSLSignatureRawRSA extends SignatureSpi {
    private byte[] inputBuffer;
    private boolean inputIsTooLong;
    private int inputOffset;
    private OpenSSLKey key;

    @Override // java.security.SignatureSpi
    public Object engineGetParameter(String str) {
        return null;
    }

    @Override // java.security.SignatureSpi
    public void engineInitSign(PrivateKey privateKey) {
        OpenSSLKey openSSLRSAPrivateKey;
        if (privateKey instanceof OpenSSLRSAPrivateKey) {
            openSSLRSAPrivateKey = ((OpenSSLRSAPrivateKey) privateKey).getOpenSSLKey();
        } else if (privateKey instanceof RSAPrivateCrtKey) {
            openSSLRSAPrivateKey = OpenSSLRSAPrivateCrtKey.getInstance((RSAPrivateCrtKey) privateKey);
        } else {
            if (!(privateKey instanceof RSAPrivateKey)) {
                throw new InvalidKeyException("Need RSA private key");
            }
            openSSLRSAPrivateKey = OpenSSLRSAPrivateKey.getInstance((RSAPrivateKey) privateKey);
        }
        this.key = openSSLRSAPrivateKey;
        this.inputBuffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
        this.inputOffset = 0;
    }

    @Override // java.security.SignatureSpi
    public void engineInitVerify(PublicKey publicKey) {
        OpenSSLKey openSSLRSAPublicKey;
        if (publicKey instanceof OpenSSLRSAPublicKey) {
            openSSLRSAPublicKey = ((OpenSSLRSAPublicKey) publicKey).getOpenSSLKey();
        } else {
            if (!(publicKey instanceof RSAPublicKey)) {
                throw new InvalidKeyException("Need RSA public key");
            }
            openSSLRSAPublicKey = OpenSSLRSAPublicKey.getInstance((RSAPublicKey) publicKey);
        }
        this.key = openSSLRSAPublicKey;
        this.inputBuffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
        this.inputOffset = 0;
    }

    @Override // java.security.SignatureSpi
    public void engineSetParameter(String str, Object obj) {
    }

    @Override // java.security.SignatureSpi
    public byte[] engineSign() {
        OpenSSLKey openSSLKey = this.key;
        if (openSSLKey == null) {
            throw new SignatureException("Need RSA private key");
        }
        if (this.inputIsTooLong) {
            StringBuilder sb = new StringBuilder("input length ");
            sb.append(this.inputOffset);
            sb.append(" != ");
            throw new SignatureException(AbstractC0000a.m17m(sb, this.inputBuffer.length, " (modulus size)"));
        }
        byte[] bArr = this.inputBuffer;
        byte[] bArr2 = new byte[bArr.length];
        try {
            try {
                NativeCrypto.RSA_private_encrypt(this.inputOffset, bArr, bArr2, openSSLKey.getNativeRef(), 1);
                return bArr2;
            } catch (Exception e2) {
                throw new SignatureException(e2);
            }
        } finally {
            this.inputOffset = 0;
        }
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte b) {
        int i2 = this.inputOffset;
        int i3 = i2 + 1;
        this.inputOffset = i3;
        byte[] bArr = this.inputBuffer;
        if (i3 > bArr.length) {
            this.inputIsTooLong = true;
        } else {
            bArr[i2] = b;
        }
    }

    @Override // java.security.SignatureSpi
    public boolean engineVerify(byte[] bArr) {
        OpenSSLKey openSSLKey = this.key;
        if (openSSLKey == null) {
            throw new SignatureException("Need RSA public key");
        }
        if (this.inputIsTooLong) {
            return false;
        }
        int length = bArr.length;
        byte[] bArr2 = this.inputBuffer;
        if (length > bArr2.length) {
            throw new SignatureException("Input signature length is too large: " + bArr.length + " > " + this.inputBuffer.length);
        }
        byte[] bArr3 = new byte[bArr2.length];
        try {
            try {
                try {
                    boolean z2 = true;
                    int RSA_public_decrypt = NativeCrypto.RSA_public_decrypt(bArr.length, bArr, bArr3, openSSLKey.getNativeRef(), 1);
                    if (RSA_public_decrypt != this.inputOffset) {
                        z2 = false;
                    }
                    for (int i2 = 0; i2 < RSA_public_decrypt; i2++) {
                        if (this.inputBuffer[i2] != bArr3[i2]) {
                            z2 = false;
                        }
                    }
                    return z2;
                } catch (Exception e2) {
                    throw new SignatureException(e2);
                }
            } catch (SignatureException e3) {
                throw e3;
            } catch (Exception unused) {
                return false;
            }
        } finally {
            this.inputOffset = 0;
        }
    }

    @Override // java.security.SignatureSpi
    public void engineUpdate(byte[] bArr, int i2, int i3) {
        int i4 = this.inputOffset;
        int i5 = i4 + i3;
        this.inputOffset = i5;
        byte[] bArr2 = this.inputBuffer;
        if (i5 > bArr2.length) {
            this.inputIsTooLong = true;
        } else {
            System.arraycopy(bArr, i2, bArr2, i4, i3);
        }
    }
}
