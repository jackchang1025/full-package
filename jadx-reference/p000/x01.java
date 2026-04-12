package p000;

import com.google.android.material.datepicker.AbstractC0196a6;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.SingleDateSelector;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class x01 extends AbstractC0196a6 {

    /* renamed from: a6 */
    public final /* synthetic */ zd0 f60985a6;

    /* renamed from: a7 */
    public final /* synthetic */ TextInputLayout f60986a7;

    /* renamed from: a8 */
    public final /* synthetic */ SingleDateSelector f60987a8;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public x01(SingleDateSelector singleDateSelector, String str, SimpleDateFormat simpleDateFormat, TextInputLayout textInputLayout, CalendarConstraints calendarConstraints, zd0 zd0Var, TextInputLayout textInputLayout2) {
        super(str, simpleDateFormat, textInputLayout, calendarConstraints);
        this.f60987a8 = singleDateSelector;
        this.f60985a6 = zd0Var;
        this.f60986a7 = textInputLayout2;
    }

    @Override // com.google.android.material.datepicker.AbstractC0196a6
    /* renamed from: a0 */
    public final void mo210742a0() {
        this.f60987a8.f49402a0 = this.f60986a7.getError();
        this.f60985a6.m215393a0();
    }

    @Override // com.google.android.material.datepicker.AbstractC0196a6
    /* renamed from: a1 */
    public final void mo210743a1(Long l) {
        SingleDateSelector singleDateSelector = this.f60987a8;
        if (l == null) {
            singleDateSelector.f49403a1 = null;
        } else {
            singleDateSelector.f49403a1 = l;
        }
        singleDateSelector.f49402a0 = null;
        this.f60985a6.m215394a1(singleDateSelector.f49403a1);
    }
}
