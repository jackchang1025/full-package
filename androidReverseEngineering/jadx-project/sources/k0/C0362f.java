package k0;

import f0.C0292m;
import f0.C0300u;
import f0.C0304y;
import f0.InterfaceC0294o;
import java.nio.ByteOrder;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

/* renamed from: k0.f */
/* loaded from: classes.dex */
public final class C0362f extends C0363g {

    /* renamed from: k */
    public boolean f716k;

    /* renamed from: l */
    public final CRC32 f717l;

    public C0362f() {
        super(new Inflater(true));
        this.f716k = true;
        this.f717l = new CRC32();
    }

    /* renamed from: l */
    public static short m940l(byte[] bArr, ByteOrder byteOrder) {
        int i2;
        byte b;
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            i2 = bArr[0] << 8;
            b = bArr[1];
        } else {
            i2 = bArr[1] << 8;
            b = bArr[0];
        }
        return (short) ((b & 255) | i2);
    }

    @Override // k0.C0363g, f0.AbstractC0296q, g0.InterfaceC0310b
    /* renamed from: b */
    public final void mo294b(InterfaceC0294o interfaceC0294o, C0292m c0292m) {
        if (!this.f716k) {
            super.mo294b(interfaceC0294o, c0292m);
            return;
        }
        C0304y c0304y = new C0304y(interfaceC0294o);
        c0304y.f560d.add(new C0300u(10, new C0361e(this, interfaceC0294o, c0304y)));
    }
}
