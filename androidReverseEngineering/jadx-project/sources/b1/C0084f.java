package b1;

import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import p000a.AbstractC0000a;

/* renamed from: b1.f */
/* loaded from: classes.dex */
public final class C0084f {

    /* renamed from: a */
    public final int f122a;

    /* renamed from: b */
    public final int f123b;

    /* renamed from: c */
    public final int f124c;

    /* renamed from: d */
    public final int f125d;

    /* renamed from: e */
    public final int f126e;

    /* renamed from: f */
    public final int f127f;

    /* renamed from: g */
    public byte[] f128g;

    public C0084f(ByteBuffer byteBuffer) {
        this.f122a = byteBuffer.getInt();
        this.f123b = byteBuffer.getInt();
        this.f124c = byteBuffer.getInt();
        this.f125d = byteBuffer.getInt();
        this.f126e = byteBuffer.getInt();
        this.f127f = byteBuffer.getInt();
    }

    /* renamed from: a */
    public static C0084f m313a(InputStream inputStream, int i2, int i3) {
        ByteBuffer order = ByteBuffer.allocate(24).order(ByteOrder.LITTLE_ENDIAN);
        int i4 = 0;
        do {
            int read = inputStream.read(order.array(), i4, 24 - i4);
            if (read < 0) {
                throw new IOException("Stream closed");
            }
            i4 += read;
        } while (i4 < 24);
        C0084f c0084f = new C0084f(order);
        int i5 = c0084f.f127f;
        int i6 = ~i5;
        int i7 = c0084f.f122a;
        if (i7 != i6) {
            throw new StreamCorruptedException(String.format("Invalid header: Invalid magic 0x%x.", Integer.valueOf(i5)));
        }
        if (i7 != 1129208147 && i7 != 1314410051 && i7 != 1313165391 && i7 != 1497451343 && i7 != 1163086915 && i7 != 1163154007 && i7 != 1213486401 && i7 != 1397511251) {
            throw new StreamCorruptedException(String.format("Invalid header: Invalid command 0x%x.", Integer.valueOf(i7)));
        }
        int i8 = c0084f.f125d;
        if (i8 < 0 || i8 > i3) {
            throw new StreamCorruptedException(String.format("Invalid header: Invalid data length %d", Integer.valueOf(i8)));
        }
        if (i8 == 0) {
            return c0084f;
        }
        c0084f.f128g = new byte[i8];
        int i9 = 0;
        do {
            int read2 = inputStream.read(c0084f.f128g, i9, i8 - i9);
            if (read2 < 0) {
                throw new IOException("Stream closed");
            }
            i9 += read2;
        } while (i9 < i8);
        if (i2 <= 16777216 || (i7 == 1314410051 && c0084f.f123b <= 16777216)) {
            byte[] bArr = c0084f.f128g;
            byte[] bArr2 = AbstractC0085g.f129a;
            int length = bArr.length;
            int i10 = 0;
            for (int i11 = 0; i11 < 0 + length; i11++) {
                i10 += bArr[i11] & 255;
            }
            if (i10 != c0084f.f126e) {
                throw new StreamCorruptedException("Invalid header: Checksum mismatched.");
            }
        }
        return c0084f;
    }

    public final String toString() {
        String str;
        switch (this.f122a) {
            case 1129208147:
                str = "SYNC";
                break;
            case 1163086915:
                str = "CLSE";
                break;
            case 1163154007:
                str = "WRTE";
                break;
            case 1213486401:
                str = "AUTH";
                break;
            case 1313165391:
                str = "OPEN";
                break;
            case 1314410051:
                str = "CNXN";
                break;
            case 1397511251:
                str = "STLS";
                break;
            case 1497451343:
                str = "OKAY";
                break;
            default:
                str = "????";
                break;
        }
        StringBuilder m23s = AbstractC0000a.m23s("Message{command=", str, ", arg0=0x");
        m23s.append(Integer.toHexString(this.f123b));
        m23s.append(", arg1=0x");
        m23s.append(Integer.toHexString(this.f124c));
        m23s.append(", payloadLength=");
        m23s.append(this.f125d);
        m23s.append(", checksum=");
        m23s.append(this.f126e);
        m23s.append(", magic=0x");
        m23s.append(Integer.toHexString(this.f127f));
        m23s.append(", payload=");
        m23s.append(Arrays.toString(this.f128g));
        m23s.append('}');
        return m23s.toString();
    }
}
