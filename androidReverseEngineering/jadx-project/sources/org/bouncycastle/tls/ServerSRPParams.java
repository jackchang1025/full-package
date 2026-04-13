package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class ServerSRPParams {

    /* renamed from: B */
    protected BigInteger f1632B;

    /* renamed from: N */
    protected BigInteger f1633N;

    /* renamed from: g */
    protected BigInteger f1634g;

    /* renamed from: s */
    protected byte[] f1635s;

    public ServerSRPParams(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, BigInteger bigInteger3) {
        this.f1633N = bigInteger;
        this.f1634g = bigInteger2;
        this.f1635s = Arrays.clone(bArr);
        this.f1632B = bigInteger3;
    }

    public static ServerSRPParams parse(InputStream inputStream) {
        return new ServerSRPParams(TlsSRPUtils.readSRPParameter(inputStream), TlsSRPUtils.readSRPParameter(inputStream), TlsUtils.readOpaque8(inputStream, 1), TlsSRPUtils.readSRPParameter(inputStream));
    }

    public void encode(OutputStream outputStream) {
        TlsSRPUtils.writeSRPParameter(this.f1633N, outputStream);
        TlsSRPUtils.writeSRPParameter(this.f1634g, outputStream);
        TlsUtils.writeOpaque8(this.f1635s, outputStream);
        TlsSRPUtils.writeSRPParameter(this.f1632B, outputStream);
    }

    public BigInteger getB() {
        return this.f1632B;
    }

    public BigInteger getG() {
        return this.f1634g;
    }

    public BigInteger getN() {
        return this.f1633N;
    }

    public byte[] getS() {
        return this.f1635s;
    }
}
