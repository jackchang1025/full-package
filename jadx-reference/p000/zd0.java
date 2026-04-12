package p000;

import androidx.fragment.app.AbstractComponentCallbacksC0069a5;
import com.google.android.material.datepicker.C0202b2;
import java.util.Iterator;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class zd0 {

    /* renamed from: a0 */
    public final /* synthetic */ int f61510a0;

    /* renamed from: a1 */
    public final /* synthetic */ AbstractComponentCallbacksC0069a5 f61511a1;

    public /* synthetic */ zd0(int i, AbstractComponentCallbacksC0069a5 abstractComponentCallbacksC0069a5) {
        this.f61510a0 = i;
        this.f61511a1 = abstractComponentCallbacksC0069a5;
    }

    /* renamed from: a0 */
    public final void m215393a0() {
        switch (this.f61510a0) {
            case 0:
                ((C0202b2) this.f61511a1).f49446i1.setEnabled(false);
                break;
            default:
                Iterator it = ((de0) this.f61511a1).f58058e4.iterator();
                while (it.hasNext()) {
                    ((zd0) it.next()).m215393a0();
                }
                break;
        }
    }

    /* renamed from: a1 */
    public final void m215394a1(Object obj) {
        switch (this.f61510a0) {
            case 0:
                C0202b2 c0202b2 = (C0202b2) this.f61511a1;
                String strMo210998a1 = c0202b2.m211024d2().mo210998a1(c0202b2.m210135a8());
                c0202b2.f49443h8.setContentDescription(c0202b2.m211024d2().mo210997a0(c0202b2.m210152c5()));
                c0202b2.f49443h8.setText(strMo210998a1);
                c0202b2.f49446i1.setEnabled(c0202b2.m211024d2().mo211001a5());
                break;
            default:
                Iterator it = ((de0) this.f61511a1).f58058e4.iterator();
                while (it.hasNext()) {
                    ((zd0) it.next()).m215394a1(obj);
                }
                break;
        }
    }
}
