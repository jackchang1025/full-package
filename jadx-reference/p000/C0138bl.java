package p000;

/* renamed from: bl */
/* loaded from: classes2.dex */
public class C0138bl extends AbstractC0158c3 implements InterfaceC0010a9 {
    AbstractC0164c9 choiceObj;
    InterfaceC0117b0 obj;

    public C0138bl(r20 r20Var) {
        this.obj = r20Var;
        this.choiceObj = r20Var.toASN1Primitive();
    }

    public static C0138bl getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(abstractC0439e0.getObject());
    }

    public InterfaceC0117b0 getIssuer() {
        return this.obj;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.choiceObj;
    }

    public C0138bl(f91 f91Var) {
        this.obj = f91Var;
        this.choiceObj = new C1067pf(false, 0, (InterfaceC0117b0) f91Var);
    }

    public static C0138bl getInstance(Object obj) {
        if (obj == null || (obj instanceof C0138bl)) {
            return (C0138bl) obj;
        }
        if (obj instanceof f91) {
            return new C0138bl(f91.getInstance(obj));
        }
        if (obj instanceof r20) {
            return new C0138bl((r20) obj);
        }
        if (obj instanceof AbstractC0439e0) {
            return new C0138bl(f91.getInstance((AbstractC0439e0) obj, false));
        }
        if (obj instanceof AbstractC0400d2) {
            return new C0138bl(r20.getInstance(obj));
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in factory: "));
    }
}
