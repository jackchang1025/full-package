package org.bouncycastle.tls.crypto.impl;

import android.sun.security.util.DerValue;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsCipher;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import org.bouncycastle.tls.crypto.TlsDecodeResult;
import org.bouncycastle.tls.crypto.TlsEncodeResult;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class TlsAEADCipher implements TlsCipher {
    public static final int AEAD_CCM = 1;
    public static final int AEAD_CHACHA20_POLY1305 = 2;
    public static final int AEAD_GCM = 3;
    private static final int NONCE_RFC5288 = 1;
    private static final int NONCE_RFC7905 = 2;
    protected final TlsCryptoParameters cryptoParams;
    protected final TlsAEADCipherImpl decryptCipher;
    protected final byte[] decryptNonce;
    protected final TlsAEADCipherImpl encryptCipher;
    protected final byte[] encryptNonce;
    protected final int fixed_iv_length;
    protected final boolean isTLSv13;
    protected final int keySize;
    protected final int macSize;
    protected final int nonceMode;
    protected final int record_iv_length;

    public TlsAEADCipher(TlsCryptoParameters tlsCryptoParameters, TlsAEADCipherImpl tlsAEADCipherImpl, TlsAEADCipherImpl tlsAEADCipherImpl2, int i2, int i3, int i4) {
        int i5;
        SecurityParameters securityParametersHandshake = tlsCryptoParameters.getSecurityParametersHandshake();
        ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
        if (!TlsImplUtils.isTLSv12(negotiatedVersion)) {
            throw new TlsFatalAlert((short) 80);
        }
        boolean isTLSv13 = TlsImplUtils.isTLSv13(negotiatedVersion);
        this.isTLSv13 = isTLSv13;
        int nonceMode = getNonceMode(isTLSv13, i4);
        this.nonceMode = nonceMode;
        if (nonceMode == 1) {
            this.fixed_iv_length = 4;
            this.record_iv_length = 8;
        } else {
            if (nonceMode != 2) {
                throw new TlsFatalAlert((short) 80);
            }
            this.fixed_iv_length = 12;
            this.record_iv_length = 0;
        }
        this.cryptoParams = tlsCryptoParameters;
        this.keySize = i2;
        this.macSize = i3;
        this.decryptCipher = tlsAEADCipherImpl2;
        this.encryptCipher = tlsAEADCipherImpl;
        int i6 = this.fixed_iv_length;
        byte[] bArr = new byte[i6];
        this.decryptNonce = bArr;
        byte[] bArr2 = new byte[i6];
        this.encryptNonce = bArr2;
        boolean isServer = tlsCryptoParameters.isServer();
        if (isTLSv13) {
            rekeyCipher(securityParametersHandshake, tlsAEADCipherImpl2, bArr, !isServer);
            rekeyCipher(securityParametersHandshake, tlsAEADCipherImpl, bArr2, isServer);
            return;
        }
        int i7 = (this.fixed_iv_length * 2) + (i2 * 2);
        byte[] calculateKeyBlock = TlsImplUtils.calculateKeyBlock(tlsCryptoParameters, i7);
        if (isServer) {
            tlsAEADCipherImpl2.setKey(calculateKeyBlock, 0, i2);
            int i8 = i2 + 0;
            tlsAEADCipherImpl.setKey(calculateKeyBlock, i8, i2);
            int i9 = i8 + i2;
            System.arraycopy(calculateKeyBlock, i9, bArr, 0, this.fixed_iv_length);
            int i10 = this.fixed_iv_length;
            i5 = i9 + i10;
            System.arraycopy(calculateKeyBlock, i5, bArr2, 0, i10);
        } else {
            tlsAEADCipherImpl.setKey(calculateKeyBlock, 0, i2);
            int i11 = i2 + 0;
            tlsAEADCipherImpl2.setKey(calculateKeyBlock, i11, i2);
            int i12 = i11 + i2;
            System.arraycopy(calculateKeyBlock, i12, bArr2, 0, this.fixed_iv_length);
            int i13 = this.fixed_iv_length;
            i5 = i12 + i13;
            System.arraycopy(calculateKeyBlock, i5, bArr, 0, i13);
        }
        if (i7 != i5 + this.fixed_iv_length) {
            throw new TlsFatalAlert((short) 80);
        }
        byte[] bArr3 = new byte[this.fixed_iv_length + this.record_iv_length];
        bArr3[0] = (byte) (~bArr2[0]);
        bArr3[1] = (byte) (~bArr[1]);
        tlsAEADCipherImpl.init(bArr3, i3, null);
        tlsAEADCipherImpl2.init(bArr3, i3, null);
    }

    private static int getNonceMode(boolean z2, int i2) {
        if (i2 != 1) {
            if (i2 == 2) {
                return 2;
            }
            if (i2 != 3) {
                throw new TlsFatalAlert((short) 80);
            }
        }
        return z2 ? 2 : 1;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public TlsDecodeResult decodeCiphertext(long j2, short s2, ProtocolVersion protocolVersion, byte[] bArr, int i2, int i3) {
        short s3;
        byte b;
        if (getPlaintextLimit(i3) < 0) {
            throw new TlsFatalAlert((short) 50);
        }
        byte[] bArr2 = this.decryptNonce;
        int length = bArr2.length + this.record_iv_length;
        byte[] bArr3 = new byte[length];
        int i4 = this.nonceMode;
        int i5 = 0;
        if (i4 == 1) {
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
            int i6 = this.record_iv_length;
            System.arraycopy(bArr, i2, bArr3, length - i6, i6);
        } else {
            if (i4 != 2) {
                throw new TlsFatalAlert((short) 80);
            }
            TlsUtils.writeUint64(j2, bArr3, length - 8);
            while (true) {
                byte[] bArr4 = this.decryptNonce;
                if (i5 >= bArr4.length) {
                    break;
                }
                bArr3[i5] = (byte) (bArr4[i5] ^ bArr3[i5]);
                i5++;
            }
        }
        int i7 = this.record_iv_length;
        int i8 = i2 + i7;
        int i9 = i3 - i7;
        int outputSize = this.decryptCipher.getOutputSize(i9);
        try {
            this.decryptCipher.init(bArr3, this.macSize, getAdditionalData(j2, s2, protocolVersion, i3, outputSize));
            if (this.decryptCipher.doFinal(bArr, i8, i9, bArr, i8) != outputSize) {
                throw new TlsFatalAlert((short) 80);
            }
            if (this.isTLSv13) {
                do {
                    outputSize--;
                    if (outputSize < 0) {
                        throw new TlsFatalAlert((short) 10);
                    }
                    b = bArr[i8 + outputSize];
                } while (b == 0);
                s3 = (short) (b & 255);
            } else {
                s3 = s2;
            }
            return new TlsDecodeResult(bArr, i8, outputSize, s3);
        } catch (RuntimeException e2) {
            throw new TlsFatalAlert((short) 20, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public TlsEncodeResult encodePlaintext(long j2, short s2, ProtocolVersion protocolVersion, int i2, byte[] bArr, int i3, int i4) {
        int i5 = i2;
        byte[] bArr2 = this.encryptNonce;
        int length = bArr2.length + this.record_iv_length;
        byte[] bArr3 = new byte[length];
        int i6 = this.nonceMode;
        if (i6 == 1) {
            System.arraycopy(bArr2, 0, bArr3, 0, bArr2.length);
            TlsUtils.writeUint64(j2, bArr3, this.encryptNonce.length);
        } else {
            if (i6 != 2) {
                throw new TlsFatalAlert((short) 80);
            }
            TlsUtils.writeUint64(j2, bArr3, length - 8);
            int i7 = 0;
            while (true) {
                byte[] bArr4 = this.encryptNonce;
                if (i7 >= bArr4.length) {
                    break;
                }
                bArr3[i7] = (byte) (bArr4[i7] ^ bArr3[i7]);
                i7++;
            }
        }
        boolean z2 = this.isTLSv13;
        TlsAEADCipherImpl tlsAEADCipherImpl = this.encryptCipher;
        int i8 = i4 + (z2 ? 1 : 0);
        int outputSize = tlsAEADCipherImpl.getOutputSize(i8);
        int i9 = this.record_iv_length;
        int i10 = i9 + outputSize;
        int i11 = i5 + i10;
        byte[] bArr5 = new byte[i11];
        if (i9 != 0) {
            System.arraycopy(bArr3, length - i9, bArr5, i5, i9);
            i5 += this.record_iv_length;
        }
        short s3 = this.isTLSv13 ? (short) 23 : s2;
        short s4 = s3;
        try {
            this.encryptCipher.init(bArr3, this.macSize, getAdditionalData(j2, s3, protocolVersion, i10, i4));
            System.arraycopy(bArr, i3, bArr5, i5, i4);
            if (this.isTLSv13) {
                bArr5[i5 + i4] = (byte) s2;
            }
            if (i5 + this.encryptCipher.doFinal(bArr5, i5, i8, bArr5, i5) == i11) {
                return new TlsEncodeResult(bArr5, 0, i11, s4);
            }
            throw new TlsFatalAlert((short) 80);
        } catch (RuntimeException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }

    public byte[] getAdditionalData(long j2, short s2, ProtocolVersion protocolVersion, int i2, int i3) {
        if (this.isTLSv13) {
            byte[] bArr = new byte[5];
            TlsUtils.writeUint8(s2, bArr, 0);
            TlsUtils.writeVersion(protocolVersion, bArr, 1);
            TlsUtils.writeUint16(i2, bArr, 3);
            return bArr;
        }
        byte[] bArr2 = new byte[13];
        TlsUtils.writeUint64(j2, bArr2, 0);
        TlsUtils.writeUint8(s2, bArr2, 8);
        TlsUtils.writeVersion(protocolVersion, bArr2, 9);
        TlsUtils.writeUint16(i3, bArr2, 11);
        return bArr2;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getCiphertextDecodeLimit(int i2) {
        return i2 + this.macSize + this.record_iv_length + (this.isTLSv13 ? 1 : 0);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getCiphertextEncodeLimit(int i2, int i3) {
        if (this.isTLSv13) {
            i2 = Math.min(i3, i2 + 0) + 1;
        }
        return i2 + this.macSize + this.record_iv_length;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getPlaintextLimit(int i2) {
        return ((i2 - this.macSize) - this.record_iv_length) - (this.isTLSv13 ? 1 : 0);
    }

    public void rekeyCipher(SecurityParameters securityParameters, TlsAEADCipherImpl tlsAEADCipherImpl, byte[] bArr, boolean z2) {
        if (!this.isTLSv13) {
            throw new TlsFatalAlert((short) 80);
        }
        TlsSecret trafficSecretServer = z2 ? securityParameters.getTrafficSecretServer() : securityParameters.getTrafficSecretClient();
        if (trafficSecretServer == null) {
            throw new TlsFatalAlert((short) 80);
        }
        setup13Cipher(tlsAEADCipherImpl, bArr, trafficSecretServer, securityParameters.getPRFCryptoHashAlgorithm());
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public void rekeyDecoder() {
        rekeyCipher(this.cryptoParams.getSecurityParametersConnection(), this.decryptCipher, this.decryptNonce, !this.cryptoParams.isServer());
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public void rekeyEncoder() {
        rekeyCipher(this.cryptoParams.getSecurityParametersConnection(), this.encryptCipher, this.encryptNonce, this.cryptoParams.isServer());
    }

    public void setup13Cipher(TlsAEADCipherImpl tlsAEADCipherImpl, byte[] bArr, TlsSecret tlsSecret, int i2) {
        byte[] bArr2 = TlsUtils.EMPTY_BYTES;
        byte[] extract = TlsCryptoUtils.hkdfExpandLabel(tlsSecret, i2, "key", bArr2, this.keySize).extract();
        byte[] extract2 = TlsCryptoUtils.hkdfExpandLabel(tlsSecret, i2, "iv", bArr2, this.fixed_iv_length).extract();
        tlsAEADCipherImpl.setKey(extract, 0, this.keySize);
        System.arraycopy(extract2, 0, bArr, 0, this.fixed_iv_length);
        extract2[0] = (byte) (extract2[0] ^ DerValue.TAG_CONTEXT);
        tlsAEADCipherImpl.init(extract2, this.macSize, null);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public boolean usesOpaqueRecordType() {
        return this.isTLSv13;
    }
}
