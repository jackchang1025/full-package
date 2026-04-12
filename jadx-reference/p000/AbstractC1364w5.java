package p000;

import android.content.IntentFilter;
import androidx.fragment.app.C0073a9;
import java.util.HashSet;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: w5 */
/* loaded from: classes.dex */
public abstract class AbstractC1364w5 {

    /* renamed from: a0 */
    public Object f60773a0;

    /* renamed from: a1 */
    public final Object f60774a1;

    public AbstractC1364w5(C0073a9 c0073a9, C0533ge c0533ge) {
        this.f60773a0 = c0073a9;
        this.f60774a1 = c0533ge;
    }

    /* renamed from: a0 */
    public void m215006a0() {
        C1363w4 c1363w4 = (C1363w4) this.f60773a0;
        if (c1363w4 != null) {
            try {
                ((LayoutInflaterFactory2C1367w8) this.f60774a1).f60809b0.unregisterReceiver(c1363w4);
            } catch (IllegalArgumentException unused) {
            }
            this.f60773a0 = null;
        }
    }

    /* renamed from: a1 */
    public void m215007a1() {
        C0073a9 c0073a9 = (C0073a9) this.f60773a0;
        C0533ge c0533ge = (C0533ge) this.f60774a1;
        HashSet hashSet = c0073a9.f45164a4;
        if (hashSet.remove(c0533ge) && hashSet.isEmpty()) {
            c0073a9.m210222a1();
        }
    }

    /* renamed from: a2 */
    public abstract IntentFilter mo214999a2();

    /* renamed from: a3 */
    public abstract int mo215000a3();

    /* renamed from: a4 */
    public abstract void mo215001a4();

    /* renamed from: a5 */
    public void m215008a5() {
        m215006a0();
        IntentFilter intentFilterMo214999a2 = mo214999a2();
        if (intentFilterMo214999a2.countActions() == 0) {
            return;
        }
        if (((C1363w4) this.f60773a0) == null) {
            this.f60773a0 = new C1363w4(this);
        }
        ((LayoutInflaterFactory2C1367w8) this.f60774a1).f60809b0.registerReceiver((C1363w4) this.f60773a0, intentFilterMo214999a2);
    }

    public AbstractC1364w5(LayoutInflaterFactory2C1367w8 layoutInflaterFactory2C1367w8) {
        this.f60774a1 = layoutInflaterFactory2C1367w8;
    }
}
