package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gl */
/* loaded from: classes.dex */
public final class RunnableC0540gl implements Runnable {

    /* renamed from: a0 */
    public final /* synthetic */ C0541gm f56512a0;

    /* renamed from: a1 */
    public final /* synthetic */ ff0 f56513a1;

    /* renamed from: a2 */
    public final /* synthetic */ bf0 f56514a2;

    /* renamed from: a3 */
    public final /* synthetic */ tg0 f56515a3;

    public RunnableC0540gl(tg0 tg0Var, C0541gm c0541gm, ff0 ff0Var, bf0 bf0Var) {
        this.f56515a3 = tg0Var;
        this.f56512a0 = c0541gm;
        this.f56513a1 = ff0Var;
        this.f56514a2 = bf0Var;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ViewOnKeyListenerC0542gn viewOnKeyListenerC0542gn = (ViewOnKeyListenerC0542gn) this.f56515a3.f60218a1;
        C0541gm c0541gm = this.f56512a0;
        if (c0541gm != null) {
            viewOnKeyListenerC0542gn.f56544c5 = true;
            c0541gm.f56517a1.m210690a2(false);
            viewOnKeyListenerC0542gn.f56544c5 = false;
        }
        ff0 ff0Var = this.f56513a1;
        if (ff0Var.isEnabled() && ff0Var.hasSubMenu()) {
            this.f56514a2.m210704b6(ff0Var, null, 4);
        }
    }
}
