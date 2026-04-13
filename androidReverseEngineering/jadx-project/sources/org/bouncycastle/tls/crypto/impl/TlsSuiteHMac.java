package org.bouncycastle.tls.crypto.impl;

import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsCryptoParameters;
import org.bouncycastle.tls.crypto.TlsHMAC;
import org.bouncycastle.tls.crypto.TlsMAC;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class TlsSuiteHMac implements TlsSuiteMac {
    protected final TlsCryptoParameters cryptoParams;
    protected final int digestBlockSize;
    protected final int digestOverhead;
    protected final TlsHMAC mac;
    protected final int macSize;

    public TlsSuiteHMac(TlsCryptoParameters tlsCryptoParameters, TlsHMAC tlsHMAC) {
        this.cryptoParams = tlsCryptoParameters;
        this.mac = tlsHMAC;
        this.macSize = getMacSize(tlsCryptoParameters, tlsHMAC);
        int internalBlockSize = tlsHMAC.getInternalBlockSize();
        this.digestBlockSize = internalBlockSize;
        if (TlsImplUtils.isSSL(tlsCryptoParameters) && tlsHMAC.getMacLength() == 20) {
            this.digestOverhead = 4;
        } else {
            this.digestOverhead = internalBlockSize / 8;
        }
    }

    public static int getMacSize(TlsCryptoParameters tlsCryptoParameters, TlsMAC tlsMAC) {
        int macLength = tlsMAC.getMacLength();
        return tlsCryptoParameters.getSecurityParametersHandshake().isTruncatedHMac() ? Math.min(macLength, 10) : macLength;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsSuiteMac
    public byte[] calculateMac(long j2, short s2, byte[] bArr, int i2, int i3) {
        ProtocolVersion serverVersion = this.cryptoParams.getServerVersion();
        boolean isSSL = serverVersion.isSSL();
        int i4 = isSSL ? 11 : 13;
        byte[] bArr2 = new byte[i4];
        TlsUtils.writeUint64(j2, bArr2, 0);
        TlsUtils.writeUint8(s2, bArr2, 8);
        if (!isSSL) {
            TlsUtils.writeVersion(serverVersion, bArr2, 9);
        }
        TlsUtils.writeUint16(i3, bArr2, i4 - 2);
        this.mac.update(bArr2, 0, i4);
        this.mac.update(bArr, i2, i3);
        return truncate(this.mac.calculateMAC());
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsSuiteMac
    public byte[] calculateMacConstantTime(long j2, short s2, byte[] bArr, int i2, int i3, int i4, byte[] bArr2) {
        byte[] calculateMac = calculateMac(j2, s2, bArr, i2, i3);
        int i5 = TlsImplUtils.isSSL(this.cryptoParams) ? 11 : 13;
        int digestBlockCount = getDigestBlockCount(i4 + i5) - getDigestBlockCount(i5 + i3);
        while (true) {
            digestBlockCount--;
            if (digestBlockCount < 0) {
                this.mac.update(bArr2, 0, 1);
                this.mac.reset();
                return calculateMac;
            }
            this.mac.update(bArr2, 0, this.digestBlockSize);
        }
    }

    public int getDigestBlockCount(int i2) {
        return (i2 + this.digestOverhead) / this.digestBlockSize;
    }

    @Override // org.bouncycastle.tls.crypto.impl.TlsSuiteMac
    public int getSize() {
        return this.macSize;
    }

    public byte[] truncate(byte[] bArr) {
        int length = bArr.length;
        int i2 = this.macSize;
        return length <= i2 ? bArr : Arrays.copyOf(bArr, i2);
    }
}
