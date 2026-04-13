package org.bouncycastle.tls.crypto.impl;

import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SecurityParameters;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsCipher;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsDecodeResult;
import org.bouncycastle.tls.crypto.TlsEncodeResult;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class TlsBlockCipher implements TlsCipher {
    protected final boolean acceptExtraPadding;
    protected final TlsCryptoParameters cryptoParams;
    protected final TlsBlockCipherImpl decryptCipher;
    protected final TlsBlockCipherImpl encryptCipher;
    protected final boolean encryptThenMAC;
    protected final byte[] randomData;
    protected final TlsSuiteMac readMac;
    protected final boolean useExplicitIV;
    protected final boolean useExtraPadding;
    protected final TlsSuiteMac writeMac;

    public TlsBlockCipher(TlsCryptoParameters tlsCryptoParameters, TlsBlockCipherImpl tlsBlockCipherImpl, TlsBlockCipherImpl tlsBlockCipherImpl2, TlsHMAC tlsHMAC, TlsHMAC tlsHMAC2, int i2) {
        TlsSuiteHMac tlsSuiteHMac;
        SecurityParameters securityParametersHandshake = tlsCryptoParameters.getSecurityParametersHandshake();
        ProtocolVersion negotiatedVersion = securityParametersHandshake.getNegotiatedVersion();
        if (TlsImplUtils.isTLSv13(negotiatedVersion)) {
            throw new TlsFatalAlert((short) 80);
        }
        this.cryptoParams = tlsCryptoParameters;
        this.randomData = tlsCryptoParameters.getNonceGenerator().generateNonce(256);
        boolean isEncryptThenMAC = securityParametersHandshake.isEncryptThenMAC();
        this.encryptThenMAC = isEncryptThenMAC;
        boolean isTLSv11 = TlsImplUtils.isTLSv11(negotiatedVersion);
        this.useExplicitIV = isTLSv11;
        boolean z2 = true;
        this.acceptExtraPadding = !negotiatedVersion.isSSL();
        if (!securityParametersHandshake.isExtendedPadding() || !ProtocolVersion.TLSv10.isEqualOrEarlierVersionOf(negotiatedVersion) || (!isEncryptThenMAC && securityParametersHandshake.isTruncatedHMac())) {
            z2 = false;
        }
        this.useExtraPadding = z2;
        this.encryptCipher = tlsBlockCipherImpl;
        this.decryptCipher = tlsBlockCipherImpl2;
        if (tlsCryptoParameters.isServer()) {
            tlsBlockCipherImpl2 = tlsBlockCipherImpl;
            tlsBlockCipherImpl = tlsBlockCipherImpl2;
        }
        int macLength = tlsHMAC2.getMacLength() + tlsHMAC.getMacLength() + (i2 * 2);
        if (!isTLSv11) {
            macLength += tlsBlockCipherImpl2.getBlockSize() + tlsBlockCipherImpl.getBlockSize();
        }
        byte[] calculateKeyBlock = TlsImplUtils.calculateKeyBlock(tlsCryptoParameters, macLength);
        tlsHMAC.setKey(calculateKeyBlock, 0, tlsHMAC.getMacLength());
        int macLength2 = tlsHMAC.getMacLength() + 0;
        tlsHMAC2.setKey(calculateKeyBlock, macLength2, tlsHMAC2.getMacLength());
        int macLength3 = tlsHMAC2.getMacLength() + macLength2;
        tlsBlockCipherImpl.setKey(calculateKeyBlock, macLength3, i2);
        int i3 = macLength3 + i2;
        tlsBlockCipherImpl2.setKey(calculateKeyBlock, i3, i2);
        int i4 = i3 + i2;
        int blockSize = tlsBlockCipherImpl.getBlockSize();
        int blockSize2 = tlsBlockCipherImpl2.getBlockSize();
        if (isTLSv11) {
            tlsBlockCipherImpl.init(new byte[blockSize], 0, blockSize);
            tlsBlockCipherImpl2.init(new byte[blockSize2], 0, blockSize2);
        } else {
            tlsBlockCipherImpl.init(calculateKeyBlock, i4, blockSize);
            int i5 = i4 + blockSize;
            tlsBlockCipherImpl2.init(calculateKeyBlock, i5, blockSize2);
            i4 = i5 + blockSize2;
        }
        if (i4 != macLength) {
            throw new TlsFatalAlert((short) 80);
        }
        if (tlsCryptoParameters.isServer()) {
            this.writeMac = new TlsSuiteHMac(tlsCryptoParameters, tlsHMAC2);
            tlsSuiteHMac = new TlsSuiteHMac(tlsCryptoParameters, tlsHMAC);
        } else {
            this.writeMac = new TlsSuiteHMac(tlsCryptoParameters, tlsHMAC);
            tlsSuiteHMac = new TlsSuiteHMac(tlsCryptoParameters, tlsHMAC2);
        }
        this.readMac = tlsSuiteHMac;
    }

    public int checkPaddingConstantTime(byte[] bArr, int i2, int i3, int i4, int i5) {
        byte b;
        int i6;
        int i7 = i2 + i3;
        byte b2 = bArr[i7 - 1];
        int i8 = (b2 & 255) + 1;
        if (this.acceptExtraPadding) {
            i4 = 256;
        }
        if (i8 > Math.min(i4, i3 - i5)) {
            i6 = 0;
            b = 0;
            i8 = 0;
        } else {
            int i9 = i7 - i8;
            b = 0;
            while (true) {
                int i10 = i9 + 1;
                b = (byte) ((bArr[i9] ^ b2) | b);
                if (i10 >= i7) {
                    break;
                }
                i9 = i10;
            }
            i6 = i8;
            if (b != 0) {
                i8 = 0;
            }
        }
        byte[] bArr2 = this.randomData;
        while (i6 < 256) {
            b = (byte) ((bArr2[i6] ^ b2) | b);
            i6++;
        }
        bArr2[0] = (byte) (bArr2[0] ^ b);
        return i8;
    }

    public int chooseExtraPadBlocks(int i2) {
        return Math.min(lowestBitSet(Pack.littleEndianToInt(this.cryptoParams.getNonceGenerator().generateNonce(4), 0)), i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public TlsDecodeResult decodeCiphertext(long j2, short s2, ProtocolVersion protocolVersion, byte[] bArr, int i2, int i3) {
        int i4;
        int i5;
        byte[] bArr2;
        int blockSize = this.decryptCipher.getBlockSize();
        int size = this.readMac.getSize();
        int max = this.encryptThenMAC ? blockSize + size : Math.max(blockSize, size + 1);
        if (this.useExplicitIV) {
            max += blockSize;
        }
        if (i3 < max) {
            throw new TlsFatalAlert((short) 50);
        }
        boolean z2 = this.encryptThenMAC;
        int i6 = z2 ? i3 - size : i3;
        if (i6 % blockSize != 0) {
            throw new TlsFatalAlert((short) 21);
        }
        if (z2 && (!TlsUtils.constantTimeAreEqual(size, this.readMac.calculateMac(j2, s2, bArr, i2, i3 - size), 0, bArr, (i2 + i3) - size))) {
            throw new TlsFatalAlert((short) 20);
        }
        this.decryptCipher.doFinal(bArr, i2, i6, bArr, i2);
        if (this.useExplicitIV) {
            i6 -= blockSize;
            i4 = i2 + blockSize;
        } else {
            i4 = i2;
        }
        int checkPaddingConstantTime = checkPaddingConstantTime(bArr, i4, i6, blockSize, this.encryptThenMAC ? 0 : size);
        boolean z3 = checkPaddingConstantTime == 0;
        int i7 = i6 - checkPaddingConstantTime;
        if (this.encryptThenMAC) {
            i5 = i4;
            bArr2 = bArr;
        } else {
            i7 -= size;
            i5 = i4;
            bArr2 = bArr;
            z3 |= !TlsUtils.constantTimeAreEqual(size, this.readMac.calculateMacConstantTime(j2, s2, bArr, i5, i7, i6 - size, this.randomData), 0, bArr2, i5 + i7);
        }
        if (z3) {
            throw new TlsFatalAlert((short) 20);
        }
        return new TlsDecodeResult(bArr2, i5, i7, s2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public TlsEncodeResult encodePlaintext(long j2, short s2, ProtocolVersion protocolVersion, int i2, byte[] bArr, int i3, int i4) {
        byte[] bArr2;
        int i5;
        int i6;
        int blockSize = this.encryptCipher.getBlockSize();
        int size = this.writeMac.getSize();
        int i7 = blockSize - ((!this.encryptThenMAC ? i4 + size : i4) % blockSize);
        if (this.useExtraPadding) {
            i7 += chooseExtraPadBlocks((256 - i7) / blockSize) * blockSize;
        }
        int i8 = size + i4 + i7;
        boolean z2 = this.useExplicitIV;
        if (z2) {
            i8 += blockSize;
        }
        int i9 = i2 + i8;
        byte[] bArr3 = new byte[i9];
        if (z2) {
            System.arraycopy(this.cryptoParams.getNonceGenerator().generateNonce(blockSize), 0, bArr3, i2, blockSize);
            i6 = blockSize + i2;
            bArr2 = bArr;
            i5 = i3;
        } else {
            bArr2 = bArr;
            i5 = i3;
            i6 = i2;
        }
        System.arraycopy(bArr2, i5, bArr3, i6, i4);
        int i10 = i6 + i4;
        if (!this.encryptThenMAC) {
            byte[] calculateMac = this.writeMac.calculateMac(j2, s2, bArr, i3, i4);
            System.arraycopy(calculateMac, 0, bArr3, i10, calculateMac.length);
            i10 += calculateMac.length;
        }
        byte b = (byte) (i7 - 1);
        int i11 = i10;
        int i12 = 0;
        while (i12 < i7) {
            bArr3[i11] = b;
            i12++;
            i11++;
        }
        int i13 = i11 - i2;
        this.encryptCipher.doFinal(bArr3, i2, i13, bArr3, i2);
        if (this.encryptThenMAC) {
            byte[] calculateMac2 = this.writeMac.calculateMac(j2, s2, bArr3, i2, i13);
            System.arraycopy(calculateMac2, 0, bArr3, i11, calculateMac2.length);
            i11 += calculateMac2.length;
        }
        if (i11 == i9) {
            return new TlsEncodeResult(bArr3, 0, i9, s2);
        }
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getCiphertextDecodeLimit(int i2) {
        return getCiphertextLength(this.decryptCipher.getBlockSize(), this.readMac.getSize(), 256, i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getCiphertextEncodeLimit(int i2, int i3) {
        int blockSize = this.encryptCipher.getBlockSize();
        return getCiphertextLength(blockSize, this.writeMac.getSize(), this.useExtraPadding ? 256 : blockSize, i2);
    }

    public int getCiphertextLength(int i2, int i3, int i4, int i5) {
        if (this.useExplicitIV) {
            i5 += i2;
        }
        int i6 = i5 + i4;
        if (this.encryptThenMAC) {
            return (i6 - (i6 % i2)) + i3;
        }
        int i7 = i6 + i3;
        return i7 - (i7 % i2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public int getPlaintextLimit(int i2) {
        int i3;
        int blockSize = this.encryptCipher.getBlockSize();
        int size = this.writeMac.getSize();
        if (this.encryptThenMAC) {
            i3 = i2 - size;
            size = i3 % blockSize;
        } else {
            i3 = i2 - (i2 % blockSize);
        }
        int i4 = (i3 - size) - 1;
        return this.useExplicitIV ? i4 - blockSize : i4;
    }

    public int lowestBitSet(int i2) {
        if (i2 == 0) {
            return 32;
        }
        int i3 = 0;
        while ((i2 & 1) == 0) {
            i3++;
            i2 >>= 1;
        }
        return i3;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public void rekeyDecoder() {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public void rekeyEncoder() {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCipher
    public boolean usesOpaqueRecordType() {
        return false;
    }
}
