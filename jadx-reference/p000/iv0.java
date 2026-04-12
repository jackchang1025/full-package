package p000;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class iv0 extends TouchDelegate {

    /* renamed from: a0 */
    public final View f57228a0;

    /* renamed from: a1 */
    public final Rect f57229a1;

    /* renamed from: a2 */
    public final Rect f57230a2;

    /* renamed from: a3 */
    public final Rect f57231a3;

    /* renamed from: a4 */
    public final int f57232a4;

    /* renamed from: a5 */
    public boolean f57233a5;

    public iv0(Rect rect, Rect rect2, View view) {
        super(rect, view);
        int scaledTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        this.f57232a4 = scaledTouchSlop;
        Rect rect3 = new Rect();
        this.f57229a1 = rect3;
        Rect rect4 = new Rect();
        this.f57231a3 = rect4;
        Rect rect5 = new Rect();
        this.f57230a2 = rect5;
        rect3.set(rect);
        rect4.set(rect);
        int i = -scaledTouchSlop;
        rect4.inset(i, i);
        rect5.set(rect2);
        this.f57228a0 = view;
    }

    @Override // android.view.TouchDelegate
    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int action = motionEvent.getAction();
        boolean z3 = true;
        if (action != 0) {
            if (action == 1 || action == 2) {
                z2 = this.f57233a5;
                if (z2 && !this.f57231a3.contains(x, y)) {
                    z3 = z2;
                    z = false;
                }
            } else {
                if (action == 3) {
                    z2 = this.f57233a5;
                    this.f57233a5 = false;
                }
                z = true;
                z3 = false;
            }
            z3 = z2;
            z = true;
        } else if (this.f57229a1.contains(x, y)) {
            this.f57233a5 = true;
            z = true;
        } else {
            z = true;
            z3 = false;
        }
        if (!z3) {
            return false;
        }
        Rect rect = this.f57230a2;
        View view = this.f57228a0;
        if (!z || rect.contains(x, y)) {
            motionEvent.setLocation(x - rect.left, y - rect.top);
        } else {
            motionEvent.setLocation(view.getWidth() / 2, view.getHeight() / 2);
        }
        return view.dispatchTouchEvent(motionEvent);
    }
}
