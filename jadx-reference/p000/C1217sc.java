package p000;

import android.R;
import android.animation.Animator;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsSeekBar;
import androidx.work.impl.WorkDatabase_Impl;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import java.util.ArrayList;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: sc */
/* loaded from: classes2.dex */
public class C1217sc implements InterfaceC1451yb {

    /* renamed from: a3 */
    public static final int[] f59949a3 = {R.attr.indeterminateDrawable, R.attr.progressDrawable};

    /* renamed from: a0 */
    public final /* synthetic */ int f59950a0;

    /* renamed from: a1 */
    public Object f59951a1;

    /* renamed from: a2 */
    public Object f59952a2;

    public /* synthetic */ C1217sc(int i, boolean z) {
        this.f59950a0 = i;
    }

    /* renamed from: a4 */
    public static int m214591a4(int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < i; i5++) {
            i3++;
            if (i3 == i2) {
                i4++;
                i3 = 0;
            } else if (i3 > i2) {
                i4++;
                i3 = 1;
            }
        }
        return i3 + 1 > i2 ? i4 + 1 : i4;
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a0 */
    public int mo214256a0() {
        return ((ExtendedFloatingActionButton) this.f59952a2).f49488c7;
    }

    /* renamed from: a1 */
    public View m214592a1(int i, int i2, int i3, int i4) {
        View viewM214310c0;
        y91 y91Var = (y91) this.f59952a2;
        nq0 nq0Var = (nq0) this.f59951a1;
        int iM214139a3 = nq0Var.m214139a3();
        int iM214138a2 = nq0Var.m214138a2();
        int i5 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            switch (nq0Var.f58686a0) {
                case 0:
                    viewM214310c0 = nq0Var.f58687a1.m214310c0(i);
                    break;
                default:
                    viewM214310c0 = nq0Var.f58687a1.m214310c0(i);
                    break;
            }
            int iM214137a1 = nq0Var.m214137a1(viewM214310c0);
            int iM214136a0 = nq0Var.m214136a0(viewM214310c0);
            y91Var.f61270a1 = iM214139a3;
            y91Var.f61271a2 = iM214138a2;
            y91Var.f61272a3 = iM214137a1;
            y91Var.f61273a4 = iM214136a0;
            if (i3 != 0) {
                y91Var.f61269a0 = i3;
                if (y91Var.m215271a0()) {
                    return viewM214310c0;
                }
            }
            if (i4 != 0) {
                y91Var.f61269a0 = i4;
                if (y91Var.m215271a0()) {
                    view = viewM214310c0;
                }
            }
            i += i5;
        }
        return view;
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a2 */
    public int mo214258a2() {
        return ((ExtendedFloatingActionButton) this.f59952a2).f49487c6;
    }

    /* renamed from: a3 */
    public ArrayList m214593a3(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f59951a1;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT work_spec_id FROM dependency WHERE prerequisite_id=?", 1);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            ArrayList arrayList = new ArrayList(cursorM213580c7.getCount());
            while (cursorM213580c7.moveToNext()) {
                arrayList.add(cursorM213580c7.isNull(0) ? null : cursorM213580c7.getString(0));
            }
            return arrayList;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: a5 */
    public boolean m214594a5(String str) {
        WorkDatabase_Impl workDatabase_Impl = (WorkDatabase_Impl) this.f59951a1;
        js0 js0VarAcquire = js0.f57367a8.acquire("SELECT COUNT(*)=0 FROM dependency WHERE work_spec_id=? AND prerequisite_id IN (SELECT id FROM workspec WHERE state!=2)", 1);
        if (str == null) {
            js0VarAcquire.mo213343a9(1);
        } else {
            js0VarAcquire.mo213341a6(1, str);
        }
        workDatabase_Impl.m212857a1();
        Cursor cursorM213580c7 = kj1.m213580c7(workDatabase_Impl, js0VarAcquire);
        try {
            boolean z = false;
            if (cursorM213580c7.moveToFirst()) {
                z = cursorM213580c7.getInt(0) != 0;
            }
            return z;
        } finally {
            cursorM213580c7.close();
            js0VarAcquire.m213344b0();
        }
    }

    /* renamed from: a6 */
    public void m214595a6() {
        ((SparseIntArray) this.f59951a1).clear();
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a7 */
    public int mo214263a7() {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        C1434xx c1434xx = (C1434xx) this.f59951a1;
        ExtendedFloatingActionButton extendedFloatingActionButton = (ExtendedFloatingActionButton) this.f59952a2;
        if (!(extendedFloatingActionButton.getParent() instanceof View)) {
            return c1434xx.mo214263a7();
        }
        View view = (View) extendedFloatingActionButton.getParent();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null || layoutParams.width != -2) {
            return (view.getWidth() - ((!(extendedFloatingActionButton.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) || (marginLayoutParams = (ViewGroup.MarginLayoutParams) extendedFloatingActionButton.getLayoutParams()) == null) ? 0 : marginLayoutParams.leftMargin + marginLayoutParams.rightMargin)) - (view.getPaddingRight() + view.getPaddingLeft());
        }
        return c1434xx.mo214263a7();
    }

    @Override // p000.InterfaceC1451yb
    /* renamed from: a8 */
    public ViewGroup.LayoutParams mo214264a8() {
        int i = ((ExtendedFloatingActionButton) this.f59952a2).f49495d4;
        if (i == 0) {
            i = -2;
        }
        return new ViewGroup.LayoutParams(-1, i);
    }

    /* renamed from: a9 */
    public boolean m214596a9(View view) {
        y91 y91Var = (y91) this.f59952a2;
        nq0 nq0Var = (nq0) this.f59951a1;
        int iM214139a3 = nq0Var.m214139a3();
        int iM214138a2 = nq0Var.m214138a2();
        int iM214137a1 = nq0Var.m214137a1(view);
        int iM214136a0 = nq0Var.m214136a0(view);
        y91Var.f61270a1 = iM214139a3;
        y91Var.f61271a2 = iM214138a2;
        y91Var.f61272a3 = iM214137a1;
        y91Var.f61273a4 = iM214136a0;
        y91Var.f61269a0 = 24579;
        return y91Var.m215271a0();
    }

    /* renamed from: b0 */
    public void mo214597b0(AttributeSet attributeSet, int i) {
        AbsSeekBar absSeekBar = (AbsSeekBar) this.f59951a1;
        pg1 pg1VarM214255d2 = pg1.m214255d2(absSeekBar.getContext(), attributeSet, f59949a3, i);
        Drawable drawableM214278c2 = pg1VarM214255d2.m214278c2(0);
        if (drawableM214278c2 != null) {
            if (drawableM214278c2 instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) drawableM214278c2;
                int numberOfFrames = animationDrawable.getNumberOfFrames();
                AnimationDrawable animationDrawable2 = new AnimationDrawable();
                animationDrawable2.setOneShot(animationDrawable.isOneShot());
                for (int i2 = 0; i2 < numberOfFrames; i2++) {
                    Drawable drawableM214599b2 = m214599b2(animationDrawable.getFrame(i2), true);
                    drawableM214599b2.setLevel(10000);
                    animationDrawable2.addFrame(drawableM214599b2, animationDrawable.getDuration(i2));
                }
                animationDrawable2.setLevel(10000);
                drawableM214278c2 = animationDrawable2;
            }
            absSeekBar.setIndeterminateDrawable(drawableM214278c2);
        }
        Drawable drawableM214278c22 = pg1VarM214255d2.m214278c2(1);
        if (drawableM214278c22 != null) {
            absSeekBar.setProgressDrawable(m214599b2(drawableM214278c22, false));
        }
        pg1VarM214255d2.m214288d4();
    }

    /* renamed from: b1 */
    public void m214598b1(ak0 ak0Var) {
        if (((ak0) this.f59951a1) != ak0Var) {
            this.f59951a1 = ak0Var;
            if (ak0Var.f43682b1 != this) {
                ak0Var.f43682b1 = this;
                m214598b1(ak0Var);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* renamed from: b2 */
    public Drawable m214599b2(Drawable drawable, boolean z) {
        if (drawable instanceof gh1) {
            ((hh1) ((gh1) drawable)).getClass();
        } else {
            if (drawable instanceof LayerDrawable) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                int numberOfLayers = layerDrawable.getNumberOfLayers();
                Drawable[] drawableArr = new Drawable[numberOfLayers];
                for (int i = 0; i < numberOfLayers; i++) {
                    int id = layerDrawable.getId(i);
                    drawableArr[i] = m214599b2(layerDrawable.getDrawable(i), id == 16908301 || id == 16908303);
                }
                LayerDrawable layerDrawable2 = new LayerDrawable(drawableArr);
                for (int i2 = 0; i2 < numberOfLayers; i2++) {
                    layerDrawable2.setId(i2, layerDrawable.getId(i2));
                    layerDrawable2.setLayerGravity(i2, layerDrawable.getLayerGravity(i2));
                    layerDrawable2.setLayerWidth(i2, layerDrawable.getLayerWidth(i2));
                    layerDrawable2.setLayerHeight(i2, layerDrawable.getLayerHeight(i2));
                    layerDrawable2.setLayerInsetLeft(i2, layerDrawable.getLayerInsetLeft(i2));
                    layerDrawable2.setLayerInsetRight(i2, layerDrawable.getLayerInsetRight(i2));
                    layerDrawable2.setLayerInsetTop(i2, layerDrawable.getLayerInsetTop(i2));
                    layerDrawable2.setLayerInsetBottom(i2, layerDrawable.getLayerInsetBottom(i2));
                    layerDrawable2.setLayerInsetStart(i2, layerDrawable.getLayerInsetStart(i2));
                    layerDrawable2.setLayerInsetEnd(i2, layerDrawable.getLayerInsetEnd(i2));
                }
                return layerDrawable2;
            }
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                Bitmap bitmap = bitmapDrawable.getBitmap();
                if (((Bitmap) this.f59952a2) == null) {
                    this.f59952a2 = bitmap;
                }
                ShapeDrawable shapeDrawable = new ShapeDrawable(new RoundRectShape(new float[]{5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f, 5.0f}, null, null));
                shapeDrawable.getPaint().setShader(new BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP));
                shapeDrawable.getPaint().setColorFilter(bitmapDrawable.getPaint().getColorFilter());
                return z ? new ClipDrawable(shapeDrawable, 3, 1) : shapeDrawable;
            }
        }
        return drawable;
    }

    @Override // p000.InterfaceC1451yb
    public int getHeight() {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        ExtendedFloatingActionButton extendedFloatingActionButton = ((C1434xx) this.f59951a1).f61200a1;
        ExtendedFloatingActionButton extendedFloatingActionButton2 = (ExtendedFloatingActionButton) this.f59952a2;
        int i = extendedFloatingActionButton2.f49495d4;
        if (i != -1) {
            return (i == 0 || i == -2) ? extendedFloatingActionButton.getMeasuredHeight() : i;
        }
        if (!(extendedFloatingActionButton2.getParent() instanceof View)) {
            return extendedFloatingActionButton.getMeasuredHeight();
        }
        View view = (View) extendedFloatingActionButton2.getParent();
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null || layoutParams.height != -2) {
            return (view.getHeight() - ((!(extendedFloatingActionButton2.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) || (marginLayoutParams = (ViewGroup.MarginLayoutParams) extendedFloatingActionButton2.getLayoutParams()) == null) ? 0 : marginLayoutParams.topMargin + marginLayoutParams.bottomMargin)) - (view.getPaddingBottom() + view.getPaddingTop());
        }
        return extendedFloatingActionButton.getMeasuredHeight();
    }

    public String toString() {
        switch (this.f59950a0) {
            case 10:
                return "Bounds{lower=" + ((f60) this.f59951a1) + " upper=" + ((f60) this.f59952a2) + "}";
            default:
                return super.toString();
        }
    }

    public C1217sc(WorkDatabase_Impl workDatabase_Impl) {
        this.f59950a0 = 0;
        this.f59951a1 = workDatabase_Impl;
        this.f59952a2 = new C1216sb(workDatabase_Impl, 0);
    }

    public C1217sc(AbsSeekBar absSeekBar) {
        this.f59950a0 = 1;
        this.f59951a1 = absSeekBar;
    }

    public C1217sc(nq0 nq0Var) {
        this.f59950a0 = 9;
        this.f59951a1 = nq0Var;
        y91 y91Var = new y91();
        y91Var.f61269a0 = 0;
        this.f59952a2 = y91Var;
    }

    public C1217sc(f60 f60Var, f60 f60Var2) {
        this.f59950a0 = 10;
        this.f59951a1 = f60Var;
        this.f59952a2 = f60Var2;
    }

    public C1217sc(Animation animation) {
        this.f59950a0 = 6;
        this.f59951a1 = animation;
        this.f59952a2 = null;
    }

    public C1217sc(Animator animator) {
        this.f59950a0 = 6;
        this.f59951a1 = null;
        this.f59952a2 = animator;
    }

    public C1217sc(ExtendedFloatingActionButton extendedFloatingActionButton, C1434xx c1434xx) {
        this.f59950a0 = 5;
        this.f59952a2 = extendedFloatingActionButton;
        this.f59951a1 = c1434xx;
    }

    public C1217sc(b90 b90Var, b90 b90Var2) {
        this.f59950a0 = 4;
        if (b90Var.f45751a0 <= b90Var2.f45751a0) {
            this.f59951a1 = b90Var;
            this.f59952a2 = b90Var2;
            return;
        }
        throw new IllegalArgumentException();
    }

    public C1217sc(int i) {
        this.f59950a0 = i;
        switch (i) {
            case 7:
                this.f59951a1 = new SparseIntArray();
                this.f59952a2 = new SparseIntArray();
                break;
            default:
                this.f59951a1 = new Rect();
                this.f59952a2 = new Rect();
                break;
        }
    }
}
