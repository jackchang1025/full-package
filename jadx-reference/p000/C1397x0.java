package p000;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import androidx.appcompat.R$attr;
import androidx.appcompat.R$color;
import androidx.appcompat.R$drawable;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: x0 */
/* loaded from: classes.dex */
public final class C1397x0 {

    /* renamed from: a0 */
    public final int[] f60979a0 = {R$drawable.abc_textfield_search_default_mtrl_alpha, R$drawable.abc_textfield_default_mtrl_alpha, R$drawable.abc_ab_share_pack_mtrl_alpha};

    /* renamed from: a1 */
    public final int[] f60980a1 = {R$drawable.abc_ic_commit_search_api_mtrl_alpha, R$drawable.abc_seekbar_tick_mark_material, R$drawable.abc_ic_menu_share_mtrl_alpha, R$drawable.abc_ic_menu_copy_mtrl_am_alpha, R$drawable.abc_ic_menu_cut_mtrl_alpha, R$drawable.abc_ic_menu_selectall_mtrl_alpha, R$drawable.abc_ic_menu_paste_mtrl_am_alpha};

    /* renamed from: a2 */
    public final int[] f60981a2 = {R$drawable.abc_textfield_activated_mtrl_alpha, R$drawable.abc_textfield_search_activated_mtrl_alpha, R$drawable.abc_cab_background_top_mtrl_alpha, R$drawable.abc_text_cursor_material, R$drawable.abc_text_select_handle_left_mtrl, R$drawable.abc_text_select_handle_middle_mtrl, R$drawable.abc_text_select_handle_right_mtrl};

    /* renamed from: a3 */
    public final int[] f60982a3 = {R$drawable.abc_popup_background_mtrl_mult, R$drawable.abc_cab_background_internal_bg, R$drawable.abc_menu_hardkey_panel_mtrl_mult};

    /* renamed from: a4 */
    public final int[] f60983a4 = {R$drawable.abc_tab_indicator_material, R$drawable.abc_textfield_search_material};

    /* renamed from: a5 */
    public final int[] f60984a5 = {R$drawable.abc_btn_check_material, R$drawable.abc_btn_radio_material, R$drawable.abc_btn_check_material_anim, R$drawable.abc_btn_radio_material_anim};

    /* renamed from: a0 */
    public static boolean m215090a0(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a1 */
    public static ColorStateList m215091a1(Context context, int i) {
        int iM213455a2 = k61.m213455a2(context, R$attr.colorControlHighlight);
        int iM213454a1 = k61.m213454a1(context, R$attr.colorButtonNormal);
        int[] iArr = k61.f57465a1;
        int[] iArr2 = k61.f57467a3;
        int iM213332a2 = AbstractC0724jn.m213332a2(iM213455a2, i);
        return new ColorStateList(new int[][]{iArr, iArr2, k61.f57466a2, k61.f57469a5}, new int[]{iM213454a1, iM213332a2, AbstractC0724jn.m213332a2(iM213455a2, i), i});
    }

    /* renamed from: a2 */
    public static LayerDrawable m215092a2(sr0 sr0Var, Context context, int i) {
        BitmapDrawable bitmapDrawable;
        BitmapDrawable bitmapDrawable2;
        BitmapDrawable bitmapDrawable3;
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(i);
        Drawable drawableM214661a2 = sr0Var.m214661a2(context, R$drawable.abc_star_black_48dp);
        Drawable drawableM214661a22 = sr0Var.m214661a2(context, R$drawable.abc_star_half_black_48dp);
        if ((drawableM214661a2 instanceof BitmapDrawable) && drawableM214661a2.getIntrinsicWidth() == dimensionPixelSize && drawableM214661a2.getIntrinsicHeight() == dimensionPixelSize) {
            bitmapDrawable = (BitmapDrawable) drawableM214661a2;
            bitmapDrawable2 = new BitmapDrawable(bitmapDrawable.getBitmap());
        } else {
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            drawableM214661a2.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            drawableM214661a2.draw(canvas);
            bitmapDrawable = new BitmapDrawable(bitmapCreateBitmap);
            bitmapDrawable2 = new BitmapDrawable(bitmapCreateBitmap);
        }
        bitmapDrawable2.setTileModeX(Shader.TileMode.REPEAT);
        if ((drawableM214661a22 instanceof BitmapDrawable) && drawableM214661a22.getIntrinsicWidth() == dimensionPixelSize && drawableM214661a22.getIntrinsicHeight() == dimensionPixelSize) {
            bitmapDrawable3 = (BitmapDrawable) drawableM214661a22;
        } else {
            Bitmap bitmapCreateBitmap2 = Bitmap.createBitmap(dimensionPixelSize, dimensionPixelSize, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(bitmapCreateBitmap2);
            drawableM214661a22.setBounds(0, 0, dimensionPixelSize, dimensionPixelSize);
            drawableM214661a22.draw(canvas2);
            bitmapDrawable3 = new BitmapDrawable(bitmapCreateBitmap2);
        }
        LayerDrawable layerDrawable = new LayerDrawable(new Drawable[]{bitmapDrawable, bitmapDrawable3, bitmapDrawable2});
        layerDrawable.setId(0, R.id.background);
        layerDrawable.setId(1, R.id.secondaryProgress);
        layerDrawable.setId(2, R.id.progress);
        return layerDrawable;
    }

    /* renamed from: a4 */
    public static void m215093a4(Drawable drawable, int i, PorterDuff.Mode mode) {
        int[] iArr = AbstractC1274tv.f60282a0;
        Drawable drawableMutate = drawable.mutate();
        if (mode == null) {
            mode = C1398x1.f60988a1;
        }
        drawableMutate.setColorFilter(C1398x1.m215096a2(i, mode));
    }

    /* renamed from: a3 */
    public final ColorStateList m215094a3(Context context, int i) {
        if (i == R$drawable.abc_edit_text_material) {
            return AbstractC1117qo.m214426c2(context, R$color.abc_tint_edittext);
        }
        if (i == R$drawable.abc_switch_track_mtrl_alpha) {
            return AbstractC1117qo.m214426c2(context, R$color.abc_tint_switch_track);
        }
        if (i != R$drawable.abc_switch_thumb_material) {
            if (i == R$drawable.abc_btn_default_mtrl_shape) {
                return m215091a1(context, k61.m213455a2(context, R$attr.colorButtonNormal));
            }
            if (i == R$drawable.abc_btn_borderless_material) {
                return m215091a1(context, 0);
            }
            if (i == R$drawable.abc_btn_colored_material) {
                return m215091a1(context, k61.m213455a2(context, R$attr.colorAccent));
            }
            if (i == R$drawable.abc_spinner_mtrl_am_alpha || i == R$drawable.abc_spinner_textfield_background_material) {
                return AbstractC1117qo.m214426c2(context, R$color.abc_tint_spinner);
            }
            if (m215090a0(this.f60980a1, i)) {
                return k61.m213456a3(context, R$attr.colorControlNormal);
            }
            if (m215090a0(this.f60983a4, i)) {
                return AbstractC1117qo.m214426c2(context, R$color.abc_tint_default);
            }
            if (m215090a0(this.f60984a5, i)) {
                return AbstractC1117qo.m214426c2(context, R$color.abc_tint_btn_checkable);
            }
            if (i == R$drawable.abc_seekbar_thumb_material) {
                return AbstractC1117qo.m214426c2(context, R$color.abc_tint_seek_thumb);
            }
            return null;
        }
        int[][] iArr = new int[3][];
        int[] iArr2 = new int[3];
        ColorStateList colorStateListM213456a3 = k61.m213456a3(context, R$attr.colorSwitchThumbNormal);
        if (colorStateListM213456a3 == null || !colorStateListM213456a3.isStateful()) {
            iArr[0] = k61.f57465a1;
            iArr2[0] = k61.m213454a1(context, R$attr.colorSwitchThumbNormal);
            iArr[1] = k61.f57468a4;
            iArr2[1] = k61.m213455a2(context, R$attr.colorControlActivated);
            iArr[2] = k61.f57469a5;
            iArr2[2] = k61.m213455a2(context, R$attr.colorSwitchThumbNormal);
        } else {
            int[] iArr3 = k61.f57465a1;
            iArr[0] = iArr3;
            iArr2[0] = colorStateListM213456a3.getColorForState(iArr3, 0);
            iArr[1] = k61.f57468a4;
            iArr2[1] = k61.m213455a2(context, R$attr.colorControlActivated);
            iArr[2] = k61.f57469a5;
            iArr2[2] = colorStateListM213456a3.getDefaultColor();
        }
        return new ColorStateList(iArr, iArr2);
    }
}
