package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class PskIdentity {
    protected byte[] identity;
    protected long obfuscatedTicketAge;

    public PskIdentity(byte[] bArr, long j2) {
        if (bArr == null) {
            throw new IllegalArgumentException("'identity' cannot be null");
        }
        if (bArr.length < 1 || !TlsUtils.isValidUint16(bArr.length)) {
            throw new IllegalArgumentException("'identity' should have length from 1 to 65535");
        }
        if (!TlsUtils.isValidUint32(j2)) {
            throw new IllegalArgumentException("'obfuscatedTicketAge' should be a uint32");
        }
        this.identity = bArr;
        this.obfuscatedTicketAge = j2;
    }

    public static PskIdentity parse(InputStream inputStream) {
        return new PskIdentity(TlsUtils.readOpaque16(inputStream, 1), TlsUtils.readUint32(inputStream));
    }

    public void encode(OutputStream outputStream) {
        TlsUtils.writeOpaque16(this.identity, outputStream);
        TlsUtils.writeUint32(this.obfuscatedTicketAge, outputStream);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PskIdentity)) {
            return false;
        }
        PskIdentity pskIdentity = (PskIdentity) obj;
        return this.obfuscatedTicketAge == pskIdentity.obfuscatedTicketAge && Arrays.constantTimeAreEqual(this.identity, pskIdentity.identity);
    }

    public int getEncodedLength() {
        return this.identity.length + 6;
    }

    public byte[] getIdentity() {
        return this.identity;
    }

    public long getObfuscatedTicketAge() {
        return this.obfuscatedTicketAge;
    }

    public int hashCode() {
        int hashCode = Arrays.hashCode(this.identity);
        long j2 = this.obfuscatedTicketAge;
        return (hashCode ^ ((int) j2)) ^ ((int) (j2 >>> 32));
    }
}
