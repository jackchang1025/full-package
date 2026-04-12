package p000;

import com.storm.safe.rock.service.modules.yw5xud.C0372a9;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sj */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC1224sj implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59994a0;

    /* renamed from: a1 */
    public final /* synthetic */ int f59995a1;

    /* renamed from: a2 */
    public final /* synthetic */ Object f59996a2;

    /* renamed from: a3 */
    public final /* synthetic */ Object f59997a3;

    public /* synthetic */ RunnableC1224sj(int i, int i2, Object obj, Object obj2) {
        this.f59994a0 = i2;
        this.f59996a2 = obj;
        this.f59995a1 = i;
        this.f59997a3 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f59994a0) {
            case 0:
                C1225sk c1225sk = (C1225sk) this.f59996a2;
                c1225sk.f60008a1.mo212810a0(this.f59995a1, this.f59997a3);
                break;
            default:
                C0372a9 c0372a9 = (C0372a9) this.f59996a2;
                int i = this.f59995a1;
                String str = (String) this.f59997a3;
                t60.m214695b6(c0372a9, "this$0");
                try {
                    c0372a9.m212458b8(str);
                    c0372a9.m212457b7(i, str);
                    break;
                } catch (Exception e) {
                    tz0.m214810b0("handleAccessibilityEvent bg 异常: ", e.getMessage(), c0372a9.f55148a5);
                }
        }
    }
}
