package p000;

import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class bc1 extends ec1 {

    /* renamed from: a0 */
    public final /* synthetic */ int f45808a0;

    /* renamed from: a1 */
    public final Object f45809a1;

    public /* synthetic */ bc1(ViewPager2 viewPager2, int i) {
        this.f45808a0 = i;
        this.f45809a1 = viewPager2;
    }

    @Override // p000.ec1
    /* renamed from: a0 */
    public void mo210661a0(int i) {
        switch (this.f45808a0) {
            case 0:
                if (i == 0) {
                    ((ViewPager2) this.f45809a1).m210451a3();
                    return;
                }
                return;
            case 1:
            default:
                return;
            case 2:
                try {
                    ArrayList arrayList = (ArrayList) this.f45809a1;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((ec1) obj).mo210661a0(i);
                    }
                    return;
                } catch (ConcurrentModificationException e) {
                    throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", e);
                }
        }
    }

    @Override // p000.ec1
    /* renamed from: a1 */
    public void mo210662a1(int i, float f, int i2) {
        switch (this.f45808a0) {
            case 2:
                try {
                    ArrayList arrayList = (ArrayList) this.f45809a1;
                    int size = arrayList.size();
                    int i3 = 0;
                    while (i3 < size) {
                        Object obj = arrayList.get(i3);
                        i3++;
                        ((ec1) obj).mo210662a1(i, f, i2);
                    }
                    return;
                } catch (ConcurrentModificationException e) {
                    throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", e);
                }
            default:
                return;
        }
    }

    @Override // p000.ec1
    /* renamed from: a2 */
    public final void mo210663a2(int i) {
        switch (this.f45808a0) {
            case 0:
                ViewPager2 viewPager2 = (ViewPager2) this.f45809a1;
                if (viewPager2.f45475a3 != i) {
                    viewPager2.f45475a3 = i;
                    viewPager2.f45491b9.m215114a6();
                    return;
                }
                return;
            case 1:
                ViewPager2 viewPager22 = (ViewPager2) this.f45809a1;
                viewPager22.clearFocus();
                if (viewPager22.hasFocus()) {
                    viewPager22.f45481a9.requestFocus(2);
                    return;
                }
                return;
            default:
                try {
                    ArrayList arrayList = (ArrayList) this.f45809a1;
                    int size = arrayList.size();
                    int i2 = 0;
                    while (i2 < size) {
                        Object obj = arrayList.get(i2);
                        i2++;
                        ((ec1) obj).mo210663a2(i);
                    }
                    return;
                } catch (ConcurrentModificationException e) {
                    throw new IllegalStateException("Adding and removing callbacks during dispatch to callbacks is not supported", e);
                }
        }
    }

    public bc1() {
        this.f45808a0 = 2;
        this.f45809a1 = new ArrayList(3);
    }
}
