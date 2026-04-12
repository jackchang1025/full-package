package p000;

import com.storm.safe.rock.service.modules.C0325b0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final /* synthetic */ class q81 implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f59427a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0325b0 f59428a1;

    public /* synthetic */ q81(C0325b0 c0325b0, int i) {
        this.f59427a0 = i;
        this.f59428a1 = c0325b0;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.f59427a0) {
            case 0:
                C0325b0 c0325b0 = this.f59428a1;
                if (c0325b0.f53155a8) {
                    c0325b0.m211689a0("idle_timeout");
                    break;
                }
                break;
            default:
                C0325b0 c0325b02 = this.f59428a1;
                t60.m214695b6(c0325b02, "this$0");
                c0325b02.m211689a0("focus_lost");
                break;
        }
    }
}
