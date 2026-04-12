package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: zw */
/* loaded from: classes2.dex */
public final class C1533zw extends AbstractC1534zx {

    /* renamed from: a4 */
    public final /* synthetic */ int f61592a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0000a f61593a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ C1533zw(C0000a c0000a, int i) {
        super(c0000a);
        this.f61592a4 = i;
        this.f61593a5 = c0000a;
    }

    @Override // p000.AbstractC1534zx
    /* renamed from: a0 */
    public final float mo215434a0() {
        float f;
        float f2;
        switch (this.f61592a4) {
            case 0:
                C0000a c0000a = this.f61593a5;
                f = c0000a.f61618a7;
                f2 = c0000a.f61619a8;
                break;
            case 1:
                C0000a c0000a2 = this.f61593a5;
                f = c0000a2.f61618a7;
                f2 = c0000a2.f61620a9;
                break;
            default:
                return this.f61593a5.f61618a7;
        }
        return f + f2;
    }
}
