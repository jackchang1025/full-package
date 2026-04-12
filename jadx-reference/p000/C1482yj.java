package p000;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.transition.R$id;
import java.util.HashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: yj */
/* loaded from: classes.dex */
public final class C1482yj extends s71 {

    /* renamed from: c3 */
    public static final String[] f61325c3 = {"android:visibility:visibility", "android:visibility:parent"};

    /* renamed from: c2 */
    public final int f61326c2;

    public C1482yj(int i) {
        this();
        this.f61326c2 = i;
    }

    /* renamed from: d1 */
    public static void m215289d1(y71 y71Var) {
        View view = y71Var.f61263a1;
        int visibility = view.getVisibility();
        HashMap map = y71Var.f61262a0;
        map.put("android:visibility:visibility", Integer.valueOf(visibility));
        map.put("android:visibility:parent", view.getParent());
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        map.put("android:visibility:screenLocation", iArr);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x002f  */
    /* renamed from: d3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static pd1 m215290d3(y71 y71Var, y71 y71Var2) {
        pd1 pd1Var = new pd1();
        pd1Var.f59216a0 = false;
        pd1Var.f59217a1 = false;
        if (y71Var != null) {
            HashMap map = y71Var.f61262a0;
            if (map.containsKey("android:visibility:visibility")) {
                pd1Var.f59218a2 = ((Integer) map.get("android:visibility:visibility")).intValue();
                pd1Var.f59220a4 = (ViewGroup) map.get("android:visibility:parent");
            } else {
                pd1Var.f59218a2 = -1;
                pd1Var.f59220a4 = null;
            }
        }
        if (y71Var2 != null) {
            HashMap map2 = y71Var2.f61262a0;
            if (map2.containsKey("android:visibility:visibility")) {
                pd1Var.f59219a3 = ((Integer) map2.get("android:visibility:visibility")).intValue();
                pd1Var.f59221a5 = (ViewGroup) map2.get("android:visibility:parent");
            } else {
                pd1Var.f59219a3 = -1;
                pd1Var.f59221a5 = null;
            }
        }
        if (y71Var != null && y71Var2 != null) {
            int i = pd1Var.f59218a2;
            int i2 = pd1Var.f59219a3;
            if (i != i2 || pd1Var.f59220a4 != pd1Var.f59221a5) {
                if (i != i2) {
                    if (i == 0) {
                        pd1Var.f59217a1 = false;
                        pd1Var.f59216a0 = true;
                        return pd1Var;
                    }
                    if (i2 == 0) {
                        pd1Var.f59217a1 = true;
                        pd1Var.f59216a0 = true;
                        return pd1Var;
                    }
                } else {
                    if (pd1Var.f59221a5 == null) {
                        pd1Var.f59217a1 = false;
                        pd1Var.f59216a0 = true;
                        return pd1Var;
                    }
                    if (pd1Var.f59220a4 == null) {
                        pd1Var.f59217a1 = true;
                        pd1Var.f59216a0 = true;
                        return pd1Var;
                    }
                }
            }
        } else {
            if (y71Var == null && pd1Var.f59219a3 == 0) {
                pd1Var.f59217a1 = true;
                pd1Var.f59216a0 = true;
                return pd1Var;
            }
            if (y71Var2 == null && pd1Var.f59218a2 == 0) {
                pd1Var.f59217a1 = false;
                pd1Var.f59216a0 = true;
            }
        }
        return pd1Var;
    }

    @Override // p000.s71
    /* renamed from: a2 */
    public final void mo210780a2(y71 y71Var) {
        m215289d1(y71Var);
    }

    @Override // p000.s71
    /* renamed from: a5 */
    public final void mo210782a5(y71 y71Var) {
        m215289d1(y71Var);
        y71Var.f61262a0.put("android:fade:transitionAlpha", Float.valueOf(hd1.f56654a0.mo213494d5(y71Var.f61263a1)));
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
    
        if (m215290d3(m214577b2(r3, false), m214578b5(r3, false)).f59216a0 != false) goto L9;
     */
    /* JADX WARN: Removed duplicated region for block: B:58:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0242  */
    @Override // p000.s71
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Animator mo212988a9(ViewGroup viewGroup, y71 y71Var, y71 y71Var2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        boolean z;
        View view;
        int i;
        Object obj;
        char c;
        int i2;
        View view2;
        Animator animator;
        View view3;
        boolean z2;
        boolean zIsAttachedToWindow;
        boolean z3;
        ViewGroup viewGroup2;
        int i3;
        View view4;
        Bitmap bitmapCreateBitmap;
        Float f;
        pd1 pd1VarM215290d3 = m215290d3(y71Var, y71Var2);
        if (pd1VarM215290d3.f59216a0 && (pd1VarM215290d3.f59220a4 != null || pd1VarM215290d3.f59221a5 != null)) {
            boolean z4 = pd1VarM215290d3.f59217a1;
            int i4 = this.f61326c2;
            int i5 = 0;
            if (!z4) {
                int i6 = pd1VarM215290d3.f59219a3;
                if ((i4 & 2) == 2 && y71Var != null) {
                    HashMap map = y71Var.f61262a0;
                    View view5 = y71Var.f61263a1;
                    View view6 = y71Var2 != null ? y71Var2.f61263a1 : null;
                    View view7 = (View) view5.getTag(R$id.save_overlay_view);
                    if (view7 != null) {
                        i = i6;
                        obj = "android:fade:transitionAlpha";
                        c = 1;
                        i2 = 0;
                        view3 = null;
                        animator = null;
                        i5 = 1;
                    } else {
                        if (view6 == null || view6.getParent() == null) {
                            if (view6 != null) {
                                z = false;
                            }
                            view = null;
                            if (!z) {
                                i = i6;
                                obj = "android:fade:transitionAlpha";
                                c = 1;
                                i2 = 0;
                                view2 = view;
                                animator = null;
                                view3 = view2;
                                view7 = view6;
                                i5 = i2;
                            } else if (view5.getParent() == null) {
                                i = i6;
                                obj = "android:fade:transitionAlpha";
                                c = 1;
                                i2 = 0;
                                view3 = view;
                                animator = null;
                                view7 = view5;
                            } else {
                                if (view5.getParent() instanceof View) {
                                    View view8 = (View) view5.getParent();
                                    animator = null;
                                    if (m215290d3(m214578b5(view8, true), m214577b2(view8, true)).f59216a0) {
                                        i = i6;
                                        obj = "android:fade:transitionAlpha";
                                        c = 1;
                                        i2 = 0;
                                        view2 = view;
                                        int id = view8.getId();
                                        if (view8.getParent() == null && id != -1) {
                                            viewGroup.findViewById(id);
                                        }
                                    } else {
                                        boolean z5 = x71.f61032a0;
                                        Matrix matrix = new Matrix();
                                        matrix.setTranslate(-view8.getScrollX(), -view8.getScrollY());
                                        jd1 jd1Var = hd1.f56654a0;
                                        jd1Var.mo213286f6(view5, matrix);
                                        jd1Var.mo213287f7(viewGroup, matrix);
                                        RectF rectF = new RectF(0.0f, 0.0f, view5.getWidth(), view5.getHeight());
                                        matrix.mapRect(rectF);
                                        int iRound = Math.round(rectF.left);
                                        int iRound2 = Math.round(rectF.top);
                                        c = 1;
                                        int iRound3 = Math.round(rectF.right);
                                        i2 = 0;
                                        int iRound4 = Math.round(rectF.bottom);
                                        ImageView imageView = new ImageView(view5.getContext());
                                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                        if (x71.f61032a0) {
                                            z2 = !view5.isAttachedToWindow();
                                            zIsAttachedToWindow = viewGroup == null ? false : viewGroup.isAttachedToWindow();
                                        } else {
                                            z2 = false;
                                            zIsAttachedToWindow = false;
                                        }
                                        boolean z6 = x71.f61033a1;
                                        if (!z6 || !z2) {
                                            z3 = z2;
                                            viewGroup2 = null;
                                            i3 = 0;
                                        } else if (zIsAttachedToWindow) {
                                            viewGroup2 = (ViewGroup) view5.getParent();
                                            int iIndexOfChild = viewGroup2.indexOfChild(view5);
                                            z3 = z2;
                                            viewGroup.getOverlay().add(view5);
                                            i3 = iIndexOfChild;
                                        } else {
                                            i = i6;
                                            obj = "android:fade:transitionAlpha";
                                            view4 = view;
                                            bitmapCreateBitmap = null;
                                            if (bitmapCreateBitmap != null) {
                                                imageView.setImageBitmap(bitmapCreateBitmap);
                                            }
                                            imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound3 - iRound, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound4 - iRound2, 1073741824));
                                            imageView.layout(iRound, iRound2, iRound3, iRound4);
                                            view3 = view4;
                                            view7 = imageView;
                                            i5 = i2;
                                        }
                                        view4 = view;
                                        int iRound5 = Math.round(rectF.width());
                                        i = i6;
                                        int iRound6 = Math.round(rectF.height());
                                        if (iRound5 <= 0 || iRound6 <= 0) {
                                            obj = "android:fade:transitionAlpha";
                                            bitmapCreateBitmap = null;
                                        } else {
                                            obj = "android:fade:transitionAlpha";
                                            float fMin = Math.min(1.0f, 1048576.0f / (iRound5 * iRound6));
                                            int iRound7 = Math.round(iRound5 * fMin);
                                            int iRound8 = Math.round(iRound6 * fMin);
                                            matrix.postTranslate(-rectF.left, -rectF.top);
                                            matrix.postScale(fMin, fMin);
                                            if (x71.f61034a2) {
                                                Picture picture = new Picture();
                                                Canvas canvasBeginRecording = picture.beginRecording(iRound7, iRound8);
                                                canvasBeginRecording.concat(matrix);
                                                view5.draw(canvasBeginRecording);
                                                picture.endRecording();
                                                bitmapCreateBitmap = Bitmap.createBitmap(picture);
                                            } else {
                                                bitmapCreateBitmap = Bitmap.createBitmap(iRound7, iRound8, Bitmap.Config.ARGB_8888);
                                                Canvas canvas = new Canvas(bitmapCreateBitmap);
                                                canvas.concat(matrix);
                                                view5.draw(canvas);
                                            }
                                        }
                                        if (z6 && z3) {
                                            viewGroup.getOverlay().remove(view5);
                                            viewGroup2.addView(view5, i3);
                                        }
                                        if (bitmapCreateBitmap != null) {
                                        }
                                        imageView.measure(View.MeasureSpec.makeMeasureSpec(iRound3 - iRound, 1073741824), View.MeasureSpec.makeMeasureSpec(iRound4 - iRound2, 1073741824));
                                        imageView.layout(iRound, iRound2, iRound3, iRound4);
                                        view3 = view4;
                                        view7 = imageView;
                                        i5 = i2;
                                    }
                                }
                                view3 = view2;
                                view7 = view6;
                                i5 = i2;
                            }
                        } else if (i6 == 4 || view5 == view6) {
                            view = view6;
                            z = false;
                            view6 = null;
                            if (!z) {
                            }
                        }
                        z = true;
                        view6 = null;
                        view = null;
                        if (!z) {
                        }
                    }
                    if (view7 == null) {
                        Object obj2 = obj;
                        if (view3 == null) {
                            return animator;
                        }
                        int visibility = view3.getVisibility();
                        jd1 jd1Var2 = hd1.f56654a0;
                        jd1Var2.mo213284f0(view3, i2);
                        jd1Var2.getClass();
                        Float f2 = (Float) map.get(obj2);
                        ObjectAnimator objectAnimatorM215291d2 = m215291d2(view3, f2 != null ? f2.floatValue() : 1.0f, 0.0f);
                        if (objectAnimatorM215291d2 == null) {
                            jd1Var2.mo213284f0(view3, visibility);
                            return objectAnimatorM215291d2;
                        }
                        od1 od1Var = new od1(view3, i);
                        objectAnimatorM215291d2.addListener(od1Var);
                        objectAnimatorM215291d2.addPauseListener(od1Var);
                        m214572a0(od1Var);
                        return objectAnimatorM215291d2;
                    }
                    if (i5 == 0) {
                        int[] iArr = (int[]) map.get("android:visibility:screenLocation");
                        int i7 = iArr[i2];
                        int i8 = iArr[c];
                        int[] iArr2 = new int[2];
                        viewGroup.getLocationOnScreen(iArr2);
                        view7.offsetLeftAndRight((i7 - iArr2[i2]) - view7.getLeft());
                        view7.offsetTopAndBottom((i8 - iArr2[c]) - view7.getTop());
                        viewGroup.getOverlay().add(view7);
                    }
                    hd1.f56654a0.getClass();
                    Float f3 = (Float) map.get(obj);
                    ObjectAnimator objectAnimatorM215291d22 = m215291d2(view7, f3 != null ? f3.floatValue() : 1.0f, 0.0f);
                    if (i5 == 0) {
                        if (objectAnimatorM215291d22 == null) {
                            viewGroup.getOverlay().remove(view7);
                            return objectAnimatorM215291d22;
                        }
                        view5.setTag(R$id.save_overlay_view, view7);
                        m214572a0(new nd1(this, viewGroup, view7, view5));
                    }
                    return objectAnimatorM215291d22;
                }
            } else if ((i4 & 1) == 1 && y71Var2 != null) {
                View view9 = y71Var2.f61263a1;
                if (y71Var == null) {
                    View view10 = (View) view9.getParent();
                }
                float fFloatValue = (y71Var == null || (f = (Float) y71Var.f61262a0.get("android:fade:transitionAlpha")) == null) ? 0.0f : f.floatValue();
                return m215291d2(view9, fFloatValue != 1.0f ? fFloatValue : 0.0f, 1.0f);
            }
        }
        return null;
    }

    @Override // p000.s71
    /* renamed from: b4 */
    public final String[] mo212989b4() {
        return f61325c3;
    }

    @Override // p000.s71
    /* renamed from: b6 */
    public final boolean mo214579b6(y71 y71Var, y71 y71Var2) {
        if (y71Var == null && y71Var2 == null) {
            return false;
        }
        if (y71Var != null && y71Var2 != null && y71Var2.f61262a0.containsKey("android:visibility:visibility") != y71Var.f61262a0.containsKey("android:visibility:visibility")) {
            return false;
        }
        pd1 pd1VarM215290d3 = m215290d3(y71Var, y71Var2);
        if (pd1VarM215290d3.f59216a0) {
            return pd1VarM215290d3.f59218a2 == 0 || pd1VarM215290d3.f59219a3 == 0;
        }
        return false;
    }

    /* renamed from: d2 */
    public final ObjectAnimator m215291d2(View view, float f, float f2) {
        if (f == f2) {
            return null;
        }
        hd1.f56654a0.mo213495e9(view, f);
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(view, hd1.f56655a1, f2);
        objectAnimatorOfFloat.addListener(new C1457yh(view));
        m214572a0(new C1481yi(0, view));
        return objectAnimatorOfFloat;
    }

    public C1482yj() {
        this.f61326c2 = 3;
    }
}
