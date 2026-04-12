package p000;

/* loaded from: classes2.dex */
public class cu0 implements InterfaceC1342vm {
    protected final AbstractC1330va scale;

    public cu0(AbstractC1330va abstractC1330va) {
        this.scale = abstractC1330va;
    }

    @Override // p000.InterfaceC1342vm
    public AbstractC1341vl map(AbstractC1341vl abstractC1341vl) {
        return abstractC1341vl.scaleX(this.scale);
    }
}
