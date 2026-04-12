package p000;

import java.io.IOException;

/* renamed from: ow */
/* loaded from: classes2.dex */
public class C1046ow extends AbstractC0156c1 {
    public static final C1046ow INSTANCE = new C1046ow();
    private static final byte[] zeroBytes = new byte[0];

    private C1046ow() {
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 5, zeroBytes);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, 0);
    }
}
