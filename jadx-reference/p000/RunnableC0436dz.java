package p000;

import android.widget.RelativeLayout;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: dz */
/* loaded from: classes2.dex */
public final /* synthetic */ class RunnableC0436dz implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ int f55891a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0454ef f55892a1;

    public /* synthetic */ RunnableC0436dz(C0454ef c0454ef, int i) {
        this.f55891a0 = i;
        this.f55892a1 = c0454ef;
    }

    @Override // java.lang.Runnable
    public final void run() {
        RelativeLayout relativeLayout;
        switch (this.f55891a0) {
            case 0:
                C0454ef c0454ef = this.f55892a1;
                t60.m214695b6(c0454ef, "this$0");
                if (c0454ef.f55983a5) {
                    RelativeLayout relativeLayout2 = c0454ef.f55980a2;
                    if (relativeLayout2 == null || relativeLayout2.getWindowToken() == null) {
                        t60.m214726f4("BlackScreenOverlay", "⚠️ 遮罩应在显示但视图丢失，自动恢复");
                        c0454ef.f55983a5 = false;
                        int i = c0454ef.f55986a8;
                        c0454ef.f55985a7 = true;
                        c0454ef.f55996b8.post(new RunnableC0027ag(c0454ef, i, 1));
                        break;
                    }
                }
                break;
            case 1:
                C0454ef c0454ef2 = this.f55892a1;
                if (c0454ef2.f55985a7 && !c0454ef2.f55983a5) {
                    c0454ef2.m212667a0();
                    c0454ef2.m212669a2();
                    break;
                }
                break;
            case 2:
                C0454ef c0454ef3 = this.f55892a1;
                t60.m214695b6(c0454ef3, "this$0");
                if (c0454ef3.f55984a6 && c0454ef3.f55983a5 && (relativeLayout = c0454ef3.f55981a3) != null) {
                    relativeLayout.setVisibility(0);
                    break;
                }
                break;
            default:
                C0454ef c0454ef4 = this.f55892a1;
                t60.m214695b6(c0454ef4, "this$0");
                try {
                    RunnableC0165ca runnableC0165ca = c0454ef4.f55995b7;
                    if (runnableC0165ca != null) {
                        c0454ef4.f55996b8.removeCallbacks(runnableC0165ca);
                    }
                    c0454ef4.f55995b7 = null;
                    c0454ef4.f55994b6 = 0;
                    c0454ef4.f55992b4 = null;
                    c0454ef4.f55993b5 = null;
                    c0454ef4.m212667a0();
                    c0454ef4.f55983a5 = false;
                    c0454ef4.f55984a6 = false;
                    c0454ef4.f55987a9 = "";
                    break;
                } catch (Exception e) {
                    tz0.m214808a8("❌ 隐藏遮罩失败: ", e.getMessage(), "BlackScreenOverlay", e);
                }
        }
    }
}
