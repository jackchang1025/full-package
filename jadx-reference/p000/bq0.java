package p000;

import com.google.android.material.datepicker.AbstractC0196a6;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.RangeDateSelector;
import com.google.android.material.textfield.TextInputLayout;
import java.text.SimpleDateFormat;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class bq0 extends AbstractC0196a6 {

    /* renamed from: a6 */
    public final /* synthetic */ int f45991a6;

    /* renamed from: a7 */
    public final /* synthetic */ TextInputLayout f45992a7;

    /* renamed from: a8 */
    public final /* synthetic */ TextInputLayout f45993a8;

    /* renamed from: a9 */
    public final /* synthetic */ zd0 f45994a9;

    /* renamed from: b0 */
    public final /* synthetic */ RangeDateSelector f45995b0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public /* synthetic */ bq0(RangeDateSelector rangeDateSelector, String str, SimpleDateFormat simpleDateFormat, TextInputLayout textInputLayout, CalendarConstraints calendarConstraints, TextInputLayout textInputLayout2, TextInputLayout textInputLayout3, zd0 zd0Var, int i) {
        super(str, simpleDateFormat, textInputLayout, calendarConstraints);
        this.f45991a6 = i;
        this.f45995b0 = rangeDateSelector;
        this.f45992a7 = textInputLayout2;
        this.f45993a8 = textInputLayout3;
        this.f45994a9 = zd0Var;
    }

    @Override // com.google.android.material.datepicker.AbstractC0196a6
    /* renamed from: a0 */
    public final void mo210742a0() {
        switch (this.f45991a6) {
            case 0:
                RangeDateSelector rangeDateSelector = this.f45995b0;
                rangeDateSelector.f49400a4 = null;
                RangeDateSelector.m211016b2(rangeDateSelector, this.f45992a7, this.f45993a8, this.f45994a9);
                break;
            default:
                RangeDateSelector rangeDateSelector2 = this.f45995b0;
                rangeDateSelector2.f49401a5 = null;
                RangeDateSelector.m211016b2(rangeDateSelector2, this.f45992a7, this.f45993a8, this.f45994a9);
                break;
        }
    }

    @Override // com.google.android.material.datepicker.AbstractC0196a6
    /* renamed from: a1 */
    public final void mo210743a1(Long l) {
        switch (this.f45991a6) {
            case 0:
                RangeDateSelector rangeDateSelector = this.f45995b0;
                rangeDateSelector.f49400a4 = l;
                RangeDateSelector.m211016b2(rangeDateSelector, this.f45992a7, this.f45993a8, this.f45994a9);
                break;
            default:
                RangeDateSelector rangeDateSelector2 = this.f45995b0;
                rangeDateSelector2.f49401a5 = l;
                RangeDateSelector.m211016b2(rangeDateSelector2, this.f45992a7, this.f45993a8, this.f45994a9);
                break;
        }
    }
}
