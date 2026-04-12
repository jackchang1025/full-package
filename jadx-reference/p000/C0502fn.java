package p000;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import androidx.work.impl.WorkDatabase;
import com.google.android.material.R$attr;
import com.google.android.material.R$styleable;
import com.google.android.material.datepicker.MaterialCalendar;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: fn */
/* loaded from: classes2.dex */
public final class C0502fn {

    /* renamed from: a0 */
    public final Object f56291a0;

    /* renamed from: a1 */
    public final Object f56292a1;

    /* renamed from: a2 */
    public final Object f56293a2;

    /* renamed from: a3 */
    public final Object f56294a3;

    /* renamed from: a4 */
    public final Object f56295a4;

    /* renamed from: a5 */
    public final Object f56296a5;

    /* renamed from: a6 */
    public Object f56297a6;

    /* renamed from: a7 */
    public final Object f56298a7;

    public C0502fn(Context context) throws Resources.NotFoundException {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(kg1.m213538e5(context, R$attr.materialCalendarStyle, MaterialCalendar.class.getCanonicalName()).data, R$styleable.MaterialCalendar);
        this.f56291a0 = C1292u9.m214819a1(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendar_dayStyle, 0));
        this.f56297a6 = C1292u9.m214819a1(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendar_dayInvalidStyle, 0));
        this.f56292a1 = C1292u9.m214819a1(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendar_daySelectedStyle, 0));
        this.f56293a2 = C1292u9.m214819a1(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendar_dayTodayStyle, 0));
        ColorStateList colorStateListM214428c4 = AbstractC1117qo.m214428c4(context, typedArrayObtainStyledAttributes, R$styleable.MaterialCalendar_rangeFillColor);
        this.f56294a3 = C1292u9.m214819a1(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendar_yearStyle, 0));
        this.f56295a4 = C1292u9.m214819a1(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendar_yearSelectedStyle, 0));
        this.f56296a5 = C1292u9.m214819a1(context, typedArrayObtainStyledAttributes.getResourceId(R$styleable.MaterialCalendar_yearTodayStyle, 0));
        Paint paint = new Paint();
        this.f56298a7 = paint;
        paint.setColor(colorStateListM214428c4.getDefaultColor());
        typedArrayObtainStyledAttributes.recycle();
    }

    public C0502fn(Context context, C0793kr c0793kr, pg1 pg1Var, so0 so0Var, WorkDatabase workDatabase, wg1 wg1Var, ArrayList arrayList) {
        new fh0();
        this.f56291a0 = context.getApplicationContext();
        this.f56293a2 = pg1Var;
        this.f56292a1 = so0Var;
        this.f56294a3 = c0793kr;
        this.f56295a4 = workDatabase;
        this.f56296a5 = wg1Var;
        this.f56298a7 = arrayList;
    }
}
