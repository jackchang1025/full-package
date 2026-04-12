package p000;

import java.io.IOException;

/* loaded from: classes2.dex */
public abstract class uh1 {
    public boolean canBePrintable(String str) {
        return AbstractC0398d0.isPrintableString(str);
    }

    public AbstractC0164c9 convertHexEncoded(String str, int i) throws IOException {
        return AbstractC0164c9.fromByteArray(c40.decodeStrict(str, i, str.length() - i));
    }

    public abstract AbstractC0164c9 getConvertedValue(C0160c5 c0160c5, String str);
}
