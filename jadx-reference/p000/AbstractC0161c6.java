package p000;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.Strings;

/* renamed from: c6 */
/* loaded from: classes2.dex */
public abstract class AbstractC0161c6 extends AbstractC0164c9 implements InterfaceC0162c7 {
    byte[] string;
    static final AbstractC0445e6 TYPE = new a0(AbstractC0161c6.class, 4);
    static final byte[] EMPTY_OCTETS = new byte[0];

    public AbstractC0161c6(byte[] bArr) {
        if (bArr == null) {
            throw new NullPointerException("'string' cannot be null");
        }
        this.string = bArr;
    }

    public static AbstractC0161c6 createPrimitive(byte[] bArr) {
        return new C1048oy(bArr);
    }

    public static AbstractC0161c6 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0161c6) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (abstractC0164c9 instanceof AbstractC0161c6) {
            return C0133bg.areEqual(this.string, ((AbstractC0161c6) abstractC0164c9).string);
        }
        return false;
    }

    @Override // p000.InterfaceC0162c7, p000.i50
    public AbstractC0164c9 getLoadedObject() {
        return toASN1Primitive();
    }

    @Override // p000.InterfaceC0162c7
    public InputStream getOctetStream() {
        return new ByteArrayInputStream(this.string);
    }

    public byte[] getOctets() {
        return this.string;
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        return C0133bg.hashCode(getOctets());
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return new C1048oy(this.string);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return new C1048oy(this.string);
    }

    public String toString() {
        return "#" + Strings.fromByteArray(c40.encode(this.string));
    }

    public static AbstractC0161c6 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0161c6)) {
            return (AbstractC0161c6) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0161c6) {
                return (AbstractC0161c6) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (AbstractC0161c6) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct OCTET STRING from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }

    public InterfaceC0162c7 parser() {
        return this;
    }

    /* renamed from: c6$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitConstructed(AbstractC0400d2 abstractC0400d2) {
            return abstractC0400d2.toASN1OctetString();
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
            return c1048oy;
        }
    }
}
