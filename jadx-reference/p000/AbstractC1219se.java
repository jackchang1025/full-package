package p000;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: se */
/* loaded from: classes2.dex */
public abstract class AbstractC1219se {

    /* renamed from: a0 */
    public static final ThreadLocal f59968a0 = new ThreadLocal();

    /* renamed from: a1 */
    public static final ThreadLocal f59969a1 = new ThreadLocal();

    /* renamed from: a0 */
    public static void m214607a0(ViewGroup viewGroup, View view, Rect rect) {
        rect.set(0, 0, view.getWidth(), view.getHeight());
        m214609a2(viewGroup, view, rect);
    }

    /* renamed from: a1 */
    public static void m214608a1(ViewParent viewParent, View view, Matrix matrix) {
        Object parent = view.getParent();
        if ((parent instanceof View) && parent != viewParent) {
            m214608a1(viewParent, (View) parent, matrix);
            matrix.preTranslate(-r0.getScrollX(), -r0.getScrollY());
        }
        matrix.preTranslate(view.getLeft(), view.getTop());
        if (view.getMatrix().isIdentity()) {
            return;
        }
        matrix.preConcat(view.getMatrix());
    }

    /* renamed from: a2 */
    public static void m214609a2(ViewGroup viewGroup, View view, Rect rect) {
        ThreadLocal threadLocal = f59968a0;
        Matrix matrix = (Matrix) threadLocal.get();
        if (matrix == null) {
            matrix = new Matrix();
            threadLocal.set(matrix);
        } else {
            matrix.reset();
        }
        m214608a1(viewGroup, view, matrix);
        ThreadLocal threadLocal2 = f59969a1;
        RectF rectF = (RectF) threadLocal2.get();
        if (rectF == null) {
            rectF = new RectF();
            threadLocal2.set(rectF);
        }
        rectF.set(rect);
        matrix.mapRect(rectF);
        rect.set((int) (rectF.left + 0.5f), (int) (rectF.top + 0.5f), (int) (rectF.right + 0.5f), (int) (rectF.bottom + 0.5f));
    }
}
