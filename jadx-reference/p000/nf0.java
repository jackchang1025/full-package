package p000;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import androidx.appcompat.R$dimen;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public class nf0 {

    /* renamed from: a0 */
    public final Context f58616a0;

    /* renamed from: a1 */
    public final bf0 f58617a1;

    /* renamed from: a2 */
    public final boolean f58618a2;

    /* renamed from: a3 */
    public final int f58619a3;

    /* renamed from: a4 */
    public View f58620a4;

    /* renamed from: a6 */
    public boolean f58622a6;

    /* renamed from: a7 */
    public sf0 f58623a7;

    /* renamed from: a8 */
    public kf0 f58624a8;

    /* renamed from: a9 */
    public PopupWindow.OnDismissListener f58625a9;

    /* renamed from: a5 */
    public int f58621a5 = 8388611;

    /* renamed from: b0 */
    public final lf0 f58626b0 = new lf0(this);

    public nf0(Context context, bf0 bf0Var, View view, boolean z, int i, int i2) {
        this.f58616a0 = context;
        this.f58617a1 = bf0Var;
        this.f58620a4 = view;
        this.f58618a2 = z;
        this.f58619a3 = i;
    }

    /* renamed from: a0 */
    public final kf0 m214074a0() {
        kf0 v11Var;
        if (this.f58624a8 == null) {
            Context context = this.f58616a0;
            Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
            Point point = new Point();
            mf0.m213991a0(defaultDisplay, point);
            if (Math.min(point.x, point.y) >= context.getResources().getDimensionPixelSize(R$dimen.abc_cascading_menus_min_smallest_width)) {
                v11Var = new ViewOnKeyListenerC0542gn(context, this.f58620a4, this.f58619a3, this.f58618a2);
            } else {
                v11Var = new v11(this.f58616a0, this.f58617a1, this.f58620a4, this.f58619a3, this.f58618a2);
            }
            v11Var.mo212967b3(this.f58617a1);
            v11Var.mo212972b9(this.f58626b0);
            v11Var.mo212968b5(this.f58620a4);
            v11Var.mo209940a5(this.f58623a7);
            v11Var.mo212969b6(this.f58622a6);
            v11Var.mo212970b7(this.f58621a5);
            this.f58624a8 = v11Var;
        }
        return this.f58624a8;
    }

    /* renamed from: a1 */
    public final boolean m214075a1() {
        kf0 kf0Var = this.f58624a8;
        return kf0Var != null && kf0Var.mo209886a1();
    }

    /* renamed from: a2 */
    public void mo214025a2() {
        this.f58624a8 = null;
        PopupWindow.OnDismissListener onDismissListener = this.f58625a9;
        if (onDismissListener != null) {
            onDismissListener.onDismiss();
        }
    }

    /* renamed from: a3 */
    public final void m214076a3(int i, int i2, boolean z, boolean z2) {
        kf0 kf0VarM214074a0 = m214074a0();
        kf0VarM214074a0.mo212973c0(z2);
        if (z) {
            int i3 = this.f58621a5;
            View view = this.f58620a4;
            WeakHashMap weakHashMap = xa1.f61054a0;
            if ((Gravity.getAbsoluteGravity(i3, ga1.m212904a3(view)) & 7) == 5) {
                i -= this.f58620a4.getWidth();
            }
            kf0VarM214074a0.mo212971b8(i);
            kf0VarM214074a0.mo212974c1(i2);
            int i4 = (int) ((this.f58616a0.getResources().getDisplayMetrics().density * 48.0f) / 2.0f);
            kf0VarM214074a0.f57514a0 = new Rect(i - i4, i2 - i4, i + i4, i2 + i4);
        }
        kf0VarM214074a0.mo209888a3();
    }
}
