package p000;

import android.os.Build;
import android.view.View;
import java.nio.ByteBuffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class z41 {

    /* renamed from: a0 */
    public int f61455a0;

    /* renamed from: a1 */
    public int f61456a1;

    /* renamed from: a2 */
    public int f61457a2;

    /* renamed from: a3 */
    public Object f61458a3;

    public z41() {
        if (fh0.f56255a1 == null) {
            fh0.f56255a1 = new fh0(18);
        }
    }

    /* renamed from: a0 */
    public int m215362a0(int i) {
        if (i < this.f61457a2) {
            return ((ByteBuffer) this.f61458a3).getShort(this.f61456a1 + i);
        }
        return 0;
    }

    /* renamed from: a1 */
    public abstract Object mo210777a1(View view);

    /* renamed from: a2 */
    public abstract void mo210778a2(View view, Object obj);

    /* renamed from: a3 */
    public void m215363a3(View view, Object obj) {
        Object tag;
        if (Build.VERSION.SDK_INT >= this.f61456a1) {
            mo210778a2(view, obj);
            return;
        }
        if (Build.VERSION.SDK_INT >= this.f61456a1) {
            tag = mo210777a1(view);
        } else {
            tag = view.getTag(this.f61455a0);
            if (!((Class) this.f61458a3).isInstance(tag)) {
                tag = null;
            }
        }
        if (mo210779a4(tag, obj)) {
            View.AccessibilityDelegate accessibilityDelegateM215141a3 = xa1.m215141a3(view);
            C0608i4 c0608i4 = accessibilityDelegateM215141a3 == null ? null : accessibilityDelegateM215141a3 instanceof C0606i2 ? ((C0606i2) accessibilityDelegateM215141a3).f56784a0 : new C0608i4(accessibilityDelegateM215141a3);
            if (c0608i4 == null) {
                c0608i4 = new C0608i4();
            }
            xa1.m215152b4(view, c0608i4);
            view.setTag(this.f61455a0, obj);
            xa1.m215146a8(view, this.f61457a2);
        }
    }

    /* renamed from: a4 */
    public abstract boolean mo210779a4(Object obj, Object obj2);
}
