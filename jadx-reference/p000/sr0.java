package p000;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$dimen;
import androidx.appcompat.R$drawable;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class sr0 {

    /* renamed from: a6 */
    public static sr0 f60065a6;

    /* renamed from: a0 */
    public WeakHashMap f60067a0;

    /* renamed from: a1 */
    public final WeakHashMap f60068a1 = new WeakHashMap(0);

    /* renamed from: a2 */
    public TypedValue f60069a2;

    /* renamed from: a3 */
    public boolean f60070a3;

    /* renamed from: a4 */
    public C1397x0 f60071a4;

    /* renamed from: a5 */
    public static final PorterDuff.Mode f60064a5 = PorterDuff.Mode.SRC_IN;

    /* renamed from: a7 */
    public static final rr0 f60066a7 = new rr0(6);

    /* renamed from: a1 */
    public static synchronized sr0 m214658a1() {
        try {
            if (f60065a6 == null) {
                f60065a6 = new sr0();
            }
        } catch (Throwable th) {
            throw th;
        }
        return f60065a6;
    }

    /* renamed from: a4 */
    public static synchronized PorterDuffColorFilter m214659a4(int i, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        rr0 rr0Var = f60066a7;
        rr0Var.getClass();
        int i2 = (31 + i) * 31;
        porterDuffColorFilter = (PorterDuffColorFilter) rr0Var.m214243a0(Integer.valueOf(mode.hashCode() + i2));
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter = new PorterDuffColorFilter(i, mode);
        }
        return porterDuffColorFilter;
    }

    /* renamed from: a0 */
    public final Drawable m214660a0(Context context, int i) throws Resources.NotFoundException {
        Drawable drawableNewDrawable;
        WeakReference weakReference;
        if (this.f60069a2 == null) {
            this.f60069a2 = new TypedValue();
        }
        TypedValue typedValue = this.f60069a2;
        context.getResources().getValue(i, typedValue, true);
        long j = (typedValue.assetCookie << 32) | typedValue.data;
        synchronized (this) {
            nc0 nc0Var = (nc0) this.f60068a1.get(context);
            drawableNewDrawable = null;
            if (nc0Var != null && (weakReference = (WeakReference) nc0Var.m214066a2(j, null)) != null) {
                Drawable.ConstantState constantState = (Drawable.ConstantState) weakReference.get();
                if (constantState != null) {
                    drawableNewDrawable = constantState.newDrawable(context.getResources());
                } else {
                    int iM214688a6 = t60.m214688a6(j, nc0Var.f58494a1, nc0Var.f58496a3);
                    if (iM214688a6 >= 0) {
                        Object[] objArr = nc0Var.f58495a2;
                        Object obj = objArr[iM214688a6];
                        Object obj2 = nc0.f58492a4;
                        if (obj != obj2) {
                            objArr[iM214688a6] = obj2;
                            nc0Var.f58493a0 = true;
                        }
                    }
                }
            }
        }
        if (drawableNewDrawable != null) {
            return drawableNewDrawable;
        }
        LayerDrawable layerDrawableM215092a2 = null;
        if (this.f60071a4 != null) {
            if (i == R$drawable.abc_cab_background_top_material) {
                layerDrawableM215092a2 = new LayerDrawable(new Drawable[]{m214661a2(context, R$drawable.abc_cab_background_internal_bg), m214661a2(context, R$drawable.abc_cab_background_top_mtrl_alpha)});
            } else if (i == R$drawable.abc_ratingbar_material) {
                layerDrawableM215092a2 = C1397x0.m215092a2(this, context, R$dimen.abc_star_big);
            } else if (i == R$drawable.abc_ratingbar_indicator_material) {
                layerDrawableM215092a2 = C1397x0.m215092a2(this, context, R$dimen.abc_star_medium);
            } else if (i == R$drawable.abc_ratingbar_small_material) {
                layerDrawableM215092a2 = C1397x0.m215092a2(this, context, R$dimen.abc_star_small);
            }
        }
        if (layerDrawableM215092a2 == null) {
            return layerDrawableM215092a2;
        }
        layerDrawableM215092a2.setChangingConfigurations(typedValue.changingConfigurations);
        synchronized (this) {
            try {
                Drawable.ConstantState constantState2 = layerDrawableM215092a2.getConstantState();
                if (constantState2 != null) {
                    nc0 nc0Var2 = (nc0) this.f60068a1.get(context);
                    if (nc0Var2 == null) {
                        nc0Var2 = new nc0();
                        this.f60068a1.put(context, nc0Var2);
                    }
                    nc0Var2.m214067a3(j, new WeakReference(constantState2));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return layerDrawableM215092a2;
    }

    /* renamed from: a2 */
    public final synchronized Drawable m214661a2(Context context, int i) {
        return m214662a3(context, i, false);
    }

    /* renamed from: a3 */
    public final synchronized Drawable m214662a3(Context context, int i, boolean z) {
        Drawable drawableM214660a0;
        try {
            if (!this.f60070a3) {
                this.f60070a3 = true;
                Drawable drawableM214661a2 = m214661a2(context, androidx.appcompat.resources.R$drawable.abc_vector_test);
                if (drawableM214661a2 == null || (!(drawableM214661a2 instanceof s91) && !"android.graphics.drawable.VectorDrawable".equals(drawableM214661a2.getClass().getName()))) {
                    this.f60070a3 = false;
                    throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
                }
            }
            drawableM214660a0 = m214660a0(context, i);
            if (drawableM214660a0 == null) {
                drawableM214660a0 = AbstractC0870mp.m214013a1(context, i);
            }
            if (drawableM214660a0 != null) {
                drawableM214660a0 = m214664a6(context, i, z, drawableM214660a0);
            }
            if (drawableM214660a0 != null) {
                AbstractC1274tv.m214790a0(drawableM214660a0);
            }
        } catch (Throwable th) {
            throw th;
        }
        return drawableM214660a0;
    }

    /* renamed from: a5 */
    public final synchronized ColorStateList m214663a5(Context context, int i) {
        ColorStateList colorStateList;
        h11 h11Var;
        WeakHashMap weakHashMap = this.f60067a0;
        ColorStateList colorStateListM215094a3 = null;
        colorStateList = (weakHashMap == null || (h11Var = (h11) weakHashMap.get(context)) == null) ? null : (ColorStateList) h11Var.m212992a1(i, null);
        if (colorStateList == null) {
            C1397x0 c1397x0 = this.f60071a4;
            if (c1397x0 != null) {
                colorStateListM215094a3 = c1397x0.m215094a3(context, i);
            }
            if (colorStateListM215094a3 != null) {
                if (this.f60067a0 == null) {
                    this.f60067a0 = new WeakHashMap();
                }
                h11 h11Var2 = (h11) this.f60067a0.get(context);
                if (h11Var2 == null) {
                    h11Var2 = new h11();
                    this.f60067a0.put(context, h11Var2);
                }
                h11Var2.m212991a0(i, colorStateListM215094a3);
            }
            colorStateList = colorStateListM215094a3;
        }
        return colorStateList;
    }

    /* JADX WARN: Removed duplicated region for block: B:49:0x00e4  */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Drawable m214664a6(Context context, int i, boolean z, Drawable drawable) {
        int i2;
        boolean z2;
        int iRound;
        ColorStateList colorStateListM214663a5 = m214663a5(context, i);
        PorterDuff.Mode mode = null;
        if (colorStateListM214663a5 != null) {
            int[] iArr = AbstractC1274tv.f60282a0;
            Drawable drawableMutate = drawable.mutate();
            AbstractC1270tr.m214774a7(drawableMutate, colorStateListM214663a5);
            if (this.f60071a4 != null && i == R$drawable.abc_switch_thumb_material) {
                mode = PorterDuff.Mode.MULTIPLY;
            }
            if (mode != null) {
                AbstractC1270tr.m214775a8(drawableMutate, mode);
            }
            return drawableMutate;
        }
        if (this.f60071a4 != null) {
            if (i == R$drawable.abc_seekbar_track_material) {
                LayerDrawable layerDrawable = (LayerDrawable) drawable;
                Drawable drawableFindDrawableByLayerId = layerDrawable.findDrawableByLayerId(R.id.background);
                int iM213455a2 = k61.m213455a2(context, R$attr.colorControlNormal);
                PorterDuff.Mode mode2 = C1398x1.f60988a1;
                C1397x0.m215093a4(drawableFindDrawableByLayerId, iM213455a2, mode2);
                C1397x0.m215093a4(layerDrawable.findDrawableByLayerId(R.id.secondaryProgress), k61.m213455a2(context, R$attr.colorControlNormal), mode2);
                C1397x0.m215093a4(layerDrawable.findDrawableByLayerId(R.id.progress), k61.m213455a2(context, R$attr.colorControlActivated), mode2);
                return drawable;
            }
            if (i == R$drawable.abc_ratingbar_material || i == R$drawable.abc_ratingbar_indicator_material || i == R$drawable.abc_ratingbar_small_material) {
                LayerDrawable layerDrawable2 = (LayerDrawable) drawable;
                Drawable drawableFindDrawableByLayerId2 = layerDrawable2.findDrawableByLayerId(R.id.background);
                int iM213454a1 = k61.m213454a1(context, R$attr.colorControlNormal);
                PorterDuff.Mode mode3 = C1398x1.f60988a1;
                C1397x0.m215093a4(drawableFindDrawableByLayerId2, iM213454a1, mode3);
                C1397x0.m215093a4(layerDrawable2.findDrawableByLayerId(R.id.secondaryProgress), k61.m213455a2(context, R$attr.colorControlActivated), mode3);
                C1397x0.m215093a4(layerDrawable2.findDrawableByLayerId(R.id.progress), k61.m213455a2(context, R$attr.colorControlActivated), mode3);
                return drawable;
            }
        }
        C1397x0 c1397x0 = this.f60071a4;
        boolean z3 = false;
        if (c1397x0 != null) {
            PorterDuff.Mode mode4 = C1398x1.f60988a1;
            if (C1397x0.m215090a0(c1397x0.f60979a0, i)) {
                i2 = R$attr.colorControlNormal;
            } else if (C1397x0.m215090a0(c1397x0.f60981a2, i)) {
                i2 = R$attr.colorControlActivated;
            } else {
                if (C1397x0.m215090a0(c1397x0.f60982a3, i)) {
                    mode4 = PorterDuff.Mode.MULTIPLY;
                } else if (i == R$drawable.abc_list_divider_mtrl_alpha) {
                    iRound = Math.round(40.8f);
                    i2 = 16842800;
                    z2 = true;
                    if (z2) {
                        int[] iArr2 = AbstractC1274tv.f60282a0;
                        Drawable drawableMutate2 = drawable.mutate();
                        drawableMutate2.setColorFilter(C1398x1.m215096a2(k61.m213455a2(context, i2), mode4));
                        if (iRound != -1) {
                            drawableMutate2.setAlpha(iRound);
                        }
                        z3 = true;
                    }
                } else if (i != R$drawable.abc_dialog_material_background) {
                    i2 = 0;
                    z2 = false;
                    iRound = -1;
                    if (z2) {
                    }
                }
                i2 = 16842801;
            }
            z2 = true;
            iRound = -1;
            if (z2) {
            }
        }
        if (z3 || !z) {
            return drawable;
        }
        return null;
    }
}
