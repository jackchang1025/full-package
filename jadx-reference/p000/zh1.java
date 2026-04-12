package p000;

/* loaded from: classes2.dex */
public class zh1 extends AbstractC0158c3 implements InterfaceC0010a9 {
    private AbstractC0164c9 params;

    public zh1(AbstractC0156c1 abstractC0156c1) {
        this.params = abstractC0156c1;
    }

    public static zh1 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(abstractC0439e0.getObject());
    }

    public AbstractC0164c9 getParameters() {
        return this.params;
    }

    public boolean isImplicitlyCA() {
        return this.params instanceof AbstractC0156c1;
    }

    public boolean isNamedCurve() {
        return this.params instanceof C0160c5;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.params;
    }

    public zh1(C0160c5 c0160c5) {
        this.params = c0160c5;
    }

    public static zh1 getInstance(Object obj) {
        if (obj == null || (obj instanceof zh1)) {
            return (zh1) obj;
        }
        if (obj instanceof AbstractC0164c9) {
            return new zh1((AbstractC0164c9) obj);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException("unknown object in getInstance()");
        }
        try {
            return new zh1(AbstractC0164c9.fromByteArray((byte[]) obj));
        } catch (Exception e) {
            throw new IllegalArgumentException("unable to parse encoded data: " + e.getMessage());
        }
    }

    private zh1(AbstractC0164c9 abstractC0164c9) {
        this.params = abstractC0164c9;
    }

    public zh1(bi1 bi1Var) {
        this.params = null;
        this.params = bi1Var.toASN1Primitive();
    }
}
