package p000;

import androidx.lifecycle.C0077a1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class wb0 {

    /* renamed from: a0 */
    public final tg0 f60882a0;

    /* renamed from: a1 */
    public boolean f60883a1;

    /* renamed from: a2 */
    public int f60884a2 = -1;

    /* renamed from: a3 */
    public final /* synthetic */ C0077a1 f60885a3;

    public wb0(C0077a1 c0077a1, tg0 tg0Var) {
        this.f60885a3 = c0077a1;
        this.f60882a0 = tg0Var;
    }

    /* renamed from: a1 */
    public final void m215044a1(boolean z) {
        if (z == this.f60883a1) {
            return;
        }
        this.f60883a1 = z;
        int i = z ? 1 : -1;
        C0077a1 c0077a1 = this.f60885a3;
        int i2 = c0077a1.f45200a2;
        c0077a1.f45200a2 = i + i2;
        if (!c0077a1.f45201a3) {
            c0077a1.f45201a3 = true;
            while (true) {
                try {
                    int i3 = c0077a1.f45200a2;
                    if (i2 == i3) {
                        break;
                    } else {
                        i2 = i3;
                    }
                } finally {
                    c0077a1.f45201a3 = false;
                }
            }
        }
        if (this.f60883a1) {
            c0077a1.m210240a2(this);
        }
    }

    /* renamed from: a4 */
    public abstract boolean mo210228a4();

    /* renamed from: a3 */
    public void mo210227a3() {
    }
}
