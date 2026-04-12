package p000;

import java.io.IOException;

/* renamed from: e6 */
/* loaded from: classes2.dex */
public abstract class AbstractC0445e6 extends AbstractC0441e2 {
    final C0407d9 tag;

    public AbstractC0445e6(Class cls, int i) {
        super(cls);
        this.tag = C0407d9.create(0, i);
    }

    public final AbstractC0164c9 checkedCast(AbstractC0164c9 abstractC0164c9) {
        if (this.javaClass.isInstance(abstractC0164c9)) {
            return abstractC0164c9;
        }
        throw new IllegalStateException("unexpected object: ".concat(abstractC0164c9.getClass().getName()));
    }

    public final AbstractC0164c9 fromByteArray(byte[] bArr) throws IOException {
        return checkedCast(AbstractC0164c9.fromByteArray(bArr));
    }

    public AbstractC0164c9 fromImplicitConstructed(AbstractC0400d2 abstractC0400d2) {
        throw new IllegalStateException("unexpected implicit constructed encoding");
    }

    public AbstractC0164c9 fromImplicitPrimitive(C1048oy c1048oy) {
        throw new IllegalStateException("unexpected implicit primitive encoding");
    }

    public final AbstractC0164c9 getContextInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        if (128 == abstractC0439e0.getTagClass()) {
            return checkedCast(abstractC0439e0.getBaseUniversal(z, this));
        }
        throw new IllegalStateException("this method only valid for CONTEXT_SPECIFIC tags");
    }

    public final C0407d9 getTag() {
        return this.tag;
    }
}
