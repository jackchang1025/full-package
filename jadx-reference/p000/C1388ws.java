package p000;

import android.widget.EditText;
import androidx.appcompat.widget.SwitchCompat;
import java.lang.ref.WeakReference;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ws */
/* loaded from: classes.dex */
public final class C1388ws extends AbstractC1373we {

    /* renamed from: a0 */
    public final /* synthetic */ int f60967a0 = 0;

    /* renamed from: a1 */
    public final WeakReference f60968a1;

    public C1388ws(EditText editText) {
        this.f60968a1 = new WeakReference(editText);
    }

    @Override // p000.AbstractC1373we
    /* renamed from: a0 */
    public void mo215047a0() {
        switch (this.f60967a0) {
            case 1:
                SwitchCompat switchCompat = (SwitchCompat) this.f60968a1.get();
                if (switchCompat != null) {
                    switchCompat.m209914a2();
                    break;
                }
                break;
        }
    }

    @Override // p000.AbstractC1373we
    /* renamed from: a1 */
    public final void mo215048a1() {
        switch (this.f60967a0) {
            case 0:
                C1389wt.m215089a0((EditText) this.f60968a1.get(), 1);
                break;
            default:
                SwitchCompat switchCompat = (SwitchCompat) this.f60968a1.get();
                if (switchCompat != null) {
                    switchCompat.m209914a2();
                    break;
                }
                break;
        }
    }

    public C1388ws(SwitchCompat switchCompat) {
        this.f60968a1 = new WeakReference(switchCompat);
    }
}
