package p000;

import java.io.IOException;
import org.bouncycastle.util.Strings;

/* renamed from: b8 */
/* loaded from: classes2.dex */
public abstract class AbstractC0125b8 extends AbstractC0164c9 implements InterfaceC0405d7 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0125b8.class, 22);
    final byte[] contents;

    /* renamed from: b8$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return AbstractC0125b8.createPrimitive(c1048oy.getOctets());
        }
    }

    public AbstractC0125b8(String str, boolean z) {
        if (str == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        if (z && !isIA5String(str)) {
            throw new IllegalArgumentException("'string' contains illegal characters");
        }
        this.contents = Strings.toByteArray(str);
    }

    public static AbstractC0125b8 createPrimitive(byte[] bArr) {
        return new C1045ov(bArr, false);
    }

    public static AbstractC0125b8 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0125b8) TYPE.getContextInstance(abstractC0439e0, z);
    }

    public static boolean isIA5String(String str) {
        for (int length = str.length() - 1; length >= 0; length--) {
            if (str.charAt(length) > 127) {
                return false;
            }
        }
        return true;
    }

    @Override // p000.AbstractC0164c9
    public final boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof AbstractC0125b8) {
            return C0133bg.areEqual(this.contents, ((AbstractC0125b8) abstractC0164c9).contents);
        }
        return false;
    }

    @Override // p000.AbstractC0164c9
    public final void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingDL(z, 22, this.contents);
    }

    @Override // p000.AbstractC0164c9
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // p000.AbstractC0164c9
    public final int encodedLength(boolean z) {
        return C0163c8.getLengthOfEncodingDL(z, this.contents.length);
    }

    public final byte[] getOctets() {
        return C0133bg.clone(this.contents);
    }

    @Override // p000.InterfaceC0405d7
    public final String getString() {
        return Strings.fromByteArray(this.contents);
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public final int hashCode() {
        return C0133bg.hashCode(this.contents);
    }

    public String toString() {
        return getString();
    }

    public AbstractC0125b8(byte[] bArr, boolean z) {
        this.contents = z ? C0133bg.clone(bArr) : bArr;
    }

    public static AbstractC0125b8 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0125b8)) {
            return (AbstractC0125b8) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0125b8) {
                return (AbstractC0125b8) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
        }
        try {
            return (AbstractC0125b8) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e) {
            throw new IllegalArgumentException(AbstractC0003a2.m27a8(e, new StringBuilder("encoding error in getInstance: ")));
        }
    }
}
