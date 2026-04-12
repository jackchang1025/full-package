package p000;

import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import okhttp3.internal.p032ws.WebSocketProtocol;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ew */
/* loaded from: classes2.dex */
public final class C0471ew implements fd1 {

    /* renamed from: a0 */
    public final boolean f56113a0;

    /* renamed from: a1 */
    public final Object f56114a1;

    public C0471ew(C1351vv c1351vv, boolean z) {
        this.f56114a1 = c1351vv;
        this.f56113a0 = z;
    }

    /* renamed from: a0 */
    public boolean m212724a0() {
        return this.f56113a0;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0039  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean m212725a1(CharSequence charSequence, int i) {
        if (charSequence == null || i < 0 || charSequence.length() - i < 0) {
            throw new IllegalArgumentException();
        }
        C1351vv c1351vv = (C1351vv) this.f56114a1;
        if (c1351vv == null) {
            return m212724a0();
        }
        c1351vv.getClass();
        char c = 2;
        for (int i2 = 0; i2 < i && c == 2; i2++) {
            byte directionality = Character.getDirectionality(charSequence.charAt(i2));
            C0471ew c0471ew = s51.f59866a0;
            if (directionality == 0) {
                c = 1;
            } else if (directionality != 1 && directionality != 2) {
                switch (directionality) {
                    case 14:
                    case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                        break;
                    case 16:
                    case 17:
                        break;
                    default:
                        c = 2;
                        break;
                }
            } else {
                c = 0;
            }
        }
        if (c == 0) {
            return true;
        }
        if (c != 1) {
            return m212724a0();
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x007b  */
    @Override // p000.fd1
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public xf1 mo212585b5(View view, xf1 xf1Var, gd1 gd1Var) {
        boolean z;
        vf1 vf1Var = xf1Var.f61102a0;
        f60 f60VarMo214391a5 = vf1Var.mo214391a5(7);
        f60 f60VarMo214391a52 = vf1Var.mo214391a5(32);
        BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) this.f56114a1;
        int i = f60VarMo214391a5.f56155a1;
        int i2 = f60VarMo214391a5.f56156a2;
        int i3 = f60VarMo214391a5.f56154a0;
        bottomSheetBehavior.f49201c2 = i;
        boolean zM214447e3 = AbstractC1117qo.m214447e3(view);
        int paddingBottom = view.getPaddingBottom();
        int paddingLeft = view.getPaddingLeft();
        int paddingRight = view.getPaddingRight();
        boolean z2 = bottomSheetBehavior.f49193b4;
        if (z2) {
            int iM215171a0 = xf1Var.m215171a0();
            bottomSheetBehavior.f49200c1 = iM215171a0;
            paddingBottom = iM215171a0 + gd1Var.f56448a3;
        }
        if (bottomSheetBehavior.f49194b5) {
            paddingLeft = (zM214447e3 ? gd1Var.f56447a2 : gd1Var.f56445a0) + i3;
        }
        if (bottomSheetBehavior.f49195b6) {
            paddingRight = (zM214447e3 ? gd1Var.f56445a0 : gd1Var.f56447a2) + i2;
        }
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        boolean z3 = true;
        if (!bottomSheetBehavior.f49197b8 || marginLayoutParams.leftMargin == i3) {
            z = false;
        } else {
            marginLayoutParams.leftMargin = i3;
            z = true;
        }
        if (bottomSheetBehavior.f49198b9 && marginLayoutParams.rightMargin != i2) {
            marginLayoutParams.rightMargin = i2;
            z = true;
        }
        if (bottomSheetBehavior.f49199c0) {
            int i4 = marginLayoutParams.topMargin;
            int i5 = f60VarMo214391a5.f56155a1;
            if (i4 != i5) {
                marginLayoutParams.topMargin = i5;
            } else {
                z3 = z;
            }
        }
        if (z3) {
            view.setLayoutParams(marginLayoutParams);
        }
        view.setPadding(paddingLeft, view.getPaddingTop(), paddingRight, paddingBottom);
        boolean z4 = this.f56113a0;
        if (z4) {
            bottomSheetBehavior.f49191b2 = f60VarMo214391a52.f56157a3;
        }
        if (!z2 && !z4) {
            return xf1Var;
        }
        bottomSheetBehavior.m210954d6();
        return xf1Var;
    }

    public C0471ew(boolean z, String str) {
        this.f56113a0 = z;
        this.f56114a1 = str;
    }

    public C0471ew(BottomSheetBehavior bottomSheetBehavior, boolean z) {
        this.f56114a1 = bottomSheetBehavior;
        this.f56113a0 = z;
    }
}
