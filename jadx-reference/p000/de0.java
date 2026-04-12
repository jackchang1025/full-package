package p000;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateSelector;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class de0<S> extends ln0 {

    /* renamed from: e5 */
    public int f55713e5;

    /* renamed from: e6 */
    public DateSelector f55714e6;

    /* renamed from: e7 */
    public CalendarConstraints f55715e7;

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b2 */
    public final void mo210139b2(Bundle bundle) {
        super.mo210139b2(bundle);
        if (bundle == null) {
            bundle = this.f45082a5;
        }
        this.f55713e5 = bundle.getInt("THEME_RES_ID_KEY");
        this.f55714e6 = (DateSelector) bundle.getParcelable("DATE_SELECTOR_KEY");
        this.f55715e7 = (CalendarConstraints) bundle.getParcelable("CALENDAR_CONSTRAINTS_KEY");
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b3 */
    public final View mo210140b3(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return this.f55714e6.mo211004a8(layoutInflater.cloneInContext(new ContextThemeWrapper(m210135a8(), this.f55713e5)), viewGroup, this.f55715e7, new zd0(1, this));
    }

    @Override // androidx.fragment.app.AbstractComponentCallbacksC0069a5
    /* renamed from: b7 */
    public final void mo210144b7(Bundle bundle) {
        bundle.putInt("THEME_RES_ID_KEY", this.f55713e5);
        bundle.putParcelable("DATE_SELECTOR_KEY", this.f55714e6);
        bundle.putParcelable("CALENDAR_CONSTRAINTS_KEY", this.f55715e7);
    }
}
