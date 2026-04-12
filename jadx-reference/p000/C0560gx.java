package p000;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeConverter;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import java.util.HashMap;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gx */
/* loaded from: classes.dex */
public final class C0560gx extends s71 {

    /* renamed from: c2 */
    public static final String[] f56582c2 = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};

    /* renamed from: c3 */
    public static final C0556gt f56583c3;

    /* renamed from: c4 */
    public static final C0556gt f56584c4;

    /* renamed from: c5 */
    public static final C0556gt f56585c5;

    /* renamed from: c6 */
    public static final C0556gt f56586c6;

    /* renamed from: c7 */
    public static final C0556gt f56587c7;

    static {
        new C0396cz(PointF.class, "boundsOrigin").f55542a1 = new Rect();
        f56583c3 = new C0556gt(PointF.class, "topLeft", 0);
        f56584c4 = new C0556gt(PointF.class, "bottomRight", 1);
        f56585c5 = new C0556gt(PointF.class, "bottomRight", 2);
        f56586c6 = new C0556gt(PointF.class, "topLeft", 3);
        f56587c7 = new C0556gt(PointF.class, "position", 4);
    }

    /* renamed from: d1 */
    public static void m212987d1(y71 y71Var) {
        View view = y71Var.f61263a1;
        HashMap map = y71Var.f61262a0;
        WeakHashMap weakHashMap = xa1.f61054a0;
        if (!ia1.m213142a2(view) && view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        map.put("android:changeBounds:bounds", new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        map.put("android:changeBounds:parent", view.getParent());
    }

    @Override // p000.s71
    /* renamed from: a2 */
    public final void mo210780a2(y71 y71Var) {
        m212987d1(y71Var);
    }

    @Override // p000.s71
    /* renamed from: a5 */
    public final void mo210782a5(y71 y71Var) {
        m212987d1(y71Var);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // p000.s71
    /* renamed from: a9 */
    public final Animator mo212988a9(ViewGroup viewGroup, y71 y71Var, y71 y71Var2) {
        int i;
        C0560gx c0560gx;
        ObjectAnimator objectAnimatorOfObject;
        if (y71Var != null) {
            HashMap map = y71Var.f61262a0;
            if (y71Var2 != null) {
                HashMap map2 = y71Var2.f61262a0;
                ViewGroup viewGroup2 = (ViewGroup) map.get("android:changeBounds:parent");
                ViewGroup viewGroup3 = (ViewGroup) map2.get("android:changeBounds:parent");
                if (viewGroup2 != null && viewGroup3 != null) {
                    View view = y71Var2.f61263a1;
                    Rect rect = (Rect) map.get("android:changeBounds:bounds");
                    Rect rect2 = (Rect) map2.get("android:changeBounds:bounds");
                    int i2 = rect.left;
                    int i3 = rect2.left;
                    int i4 = rect.top;
                    int i5 = rect2.top;
                    int i6 = rect.right;
                    int i7 = rect2.right;
                    int i8 = rect.bottom;
                    int i9 = rect2.bottom;
                    int i10 = i6 - i2;
                    int i11 = i8 - i4;
                    int i12 = i7 - i3;
                    int i13 = i9 - i5;
                    Rect rect3 = (Rect) map.get("android:changeBounds:clip");
                    Rect rect4 = (Rect) map2.get("android:changeBounds:clip");
                    if ((i10 == 0 || i11 == 0) && (i12 == 0 || i13 == 0)) {
                        i = 0;
                    } else {
                        i = (i2 == i3 && i4 == i5) ? 0 : 1;
                        if (i6 != i7 || i8 != i9) {
                            i++;
                        }
                    }
                    if ((rect3 != null && !rect3.equals(rect4)) || (rect3 == null && rect4 != null)) {
                        i++;
                    }
                    int i14 = i;
                    if (i14 > 0) {
                        hd1.m213026a0(view, i2, i4, i6, i8);
                        if (i14 != 2) {
                            c0560gx = this;
                            if (i2 == i3 && i4 == i5) {
                                c0560gx.f59917b8.getClass();
                                objectAnimatorOfObject = ObjectAnimator.ofObject(view, f56585c5, (TypeConverter) null, fh0.m212808a3(i6, i8, i7, i9));
                            } else {
                                c0560gx.f59917b8.getClass();
                                objectAnimatorOfObject = ObjectAnimator.ofObject(view, f56586c6, (TypeConverter) null, fh0.m212808a3(i2, i4, i3, i5));
                            }
                        } else if (i10 == i12 && i11 == i13) {
                            c0560gx = this;
                            c0560gx.f59917b8.getClass();
                            objectAnimatorOfObject = ObjectAnimator.ofObject(view, f56587c7, (TypeConverter) null, fh0.m212808a3(i2, i4, i3, i5));
                        } else {
                            c0560gx = this;
                            C0559gw c0559gw = new C0559gw();
                            c0559gw.f56579a4 = view;
                            c0560gx.f59917b8.getClass();
                            ObjectAnimator objectAnimatorOfObject2 = ObjectAnimator.ofObject(c0559gw, f56583c3, (TypeConverter) null, fh0.m212808a3(i2, i4, i3, i5));
                            c0560gx.f59917b8.getClass();
                            ObjectAnimator objectAnimatorOfObject3 = ObjectAnimator.ofObject(c0559gw, f56584c4, (TypeConverter) null, fh0.m212808a3(i6, i8, i7, i9));
                            AnimatorSet animatorSet = new AnimatorSet();
                            animatorSet.playTogether(objectAnimatorOfObject2, objectAnimatorOfObject3);
                            animatorSet.addListener(new C0557gu(c0559gw));
                            objectAnimatorOfObject = animatorSet;
                        }
                        if (view.getParent() instanceof ViewGroup) {
                            ViewGroup viewGroup4 = (ViewGroup) view.getParent();
                            b81.m210599f2(viewGroup4, true);
                            c0560gx.m214572a0(new C0558gv(viewGroup4));
                        }
                        return objectAnimatorOfObject;
                    }
                }
            }
        }
        return null;
    }

    @Override // p000.s71
    /* renamed from: b4 */
    public final String[] mo212989b4() {
        return f56582c2;
    }
}
