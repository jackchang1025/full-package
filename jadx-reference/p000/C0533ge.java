package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ge */
/* loaded from: classes.dex */
public final class C0533ge {

    /* renamed from: a0 */
    public boolean f56449a0;

    /* renamed from: a1 */
    public InterfaceC0532gd f56450a1;

    /* renamed from: a2 */
    public boolean f56451a2;

    /* renamed from: a0 */
    public final void m212940a0(InterfaceC0532gd interfaceC0532gd) {
        synchronized (this) {
            while (this.f56451a2) {
                try {
                    try {
                        wait();
                    } catch (InterruptedException unused) {
                    }
                } finally {
                }
            }
            if (this.f56450a1 == interfaceC0532gd) {
                return;
            }
            this.f56450a1 = interfaceC0532gd;
            if (this.f56449a0) {
                interfaceC0532gd.onCancel();
            }
        }
    }
}
