package com.storm.safe.rock.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import kotlin.Triple;
import kotlin.random.Random$Default;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.RunnableC0165ca;
import p000.RunnableC0941o6;
import p000.aq0;
import p000.km0;
import p000.t60;
import p000.v10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class ParticleView extends View {

    /* renamed from: a6 */
    public static final /* synthetic */ int f55231a6 = 0;

    /* renamed from: a0 */
    public final ArrayList f55232a0;

    /* renamed from: a1 */
    public final Paint f55233a1;

    /* renamed from: a2 */
    public final Handler f55234a2;

    /* renamed from: a3 */
    public boolean f55235a3;

    /* renamed from: a4 */
    public final List f55236a4;

    /* renamed from: a5 */
    public final RunnableC0165ca f55237a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ParticleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        t60.m214695b6(context, "context");
        this.f55232a0 = new ArrayList();
        this.f55233a1 = new Paint(1);
        this.f55234a2 = new Handler(Looper.getMainLooper());
        Integer numValueOf = Integer.valueOf(v10.MASK);
        this.f55236a4 = AbstractC0716jf.m213306g5(new Triple(220, 235, numValueOf), new Triple(180, 210, numValueOf), new Triple(numValueOf, numValueOf, numValueOf), new Triple(160, 200, 240));
        this.f55237a5 = new RunnableC0165ca(15, this);
    }

    /* renamed from: a0 */
    public final km0 m212472a0(int i, int i2, boolean z) {
        Random$Default random$Default = aq0.f45594a0;
        List list = this.f55236a4;
        int size = list.size();
        random$Default.getClass();
        Triple triple = (Triple) list.get(aq0.f45595a1.mo212994a3().nextInt(size));
        float fM213643a3 = random$Default.m213643a3() * i;
        float fM213643a32 = z ? random$Default.m213643a3() * i2 : i2 + (random$Default.m213643a3() * 50.0f);
        float fM213643a33 = (random$Default.m213643a3() * 3.5f) + 1.5f;
        float fM213643a34 = (random$Default.m213643a3() * 0.55f) + 0.1f;
        float fM213643a35 = (random$Default.m213643a3() * 1.2f) + 0.4f;
        float fM213643a36 = (random$Default.m213643a3() - 0.5f) * 0.6f;
        int iIntValue = ((Number) triple.f57564a0).intValue();
        int iIntValue2 = ((Number) triple.f57565a1).intValue();
        int iIntValue3 = ((Number) triple.f57566a2).intValue();
        km0 km0Var = new km0();
        km0Var.f57546a0 = fM213643a3;
        km0Var.f57547a1 = fM213643a32;
        km0Var.f57548a2 = fM213643a33;
        km0Var.f57549a3 = fM213643a34;
        km0Var.f57550a4 = fM213643a35;
        km0Var.f57551a5 = fM213643a36;
        km0Var.f57552a6 = iIntValue;
        km0Var.f57553a7 = iIntValue2;
        km0Var.f57554a8 = iIntValue3;
        return km0Var;
    }

    @Override // android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(new RunnableC0941o6(16, this));
        this.f55235a3 = true;
        this.f55234a2.postDelayed(this.f55237a5, 100L);
    }

    @Override // android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f55235a3 = false;
        this.f55234a2.removeCallbacks(this.f55237a5);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        t60.m214695b6(canvas, "canvas");
        ArrayList arrayList = this.f55232a0;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            km0 km0Var = (km0) obj;
            int iArgb = Color.argb(AbstractC1117qo.m214413a9((int) (km0Var.f57549a3 * 255.0f), 0, v10.MASK), km0Var.f57552a6, km0Var.f57553a7, km0Var.f57554a8);
            Paint paint = this.f55233a1;
            paint.setColor(iArgb);
            canvas.drawCircle(km0Var.f57546a0, km0Var.f57547a1, km0Var.f57548a2, paint);
        }
    }

    @Override // android.view.View
    public final void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        ArrayList arrayList = this.f55232a0;
        arrayList.clear();
        for (int i5 = 0; i5 < 30; i5++) {
            arrayList.add(m212472a0(i, i2, true));
        }
    }
}
