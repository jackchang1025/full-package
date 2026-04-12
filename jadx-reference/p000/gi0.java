package p000;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.google.android.material.R$attr;
import com.google.android.material.R$integer;
import com.google.android.material.navigation.C0213a1;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.WeakHashMap;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class gi0 extends ViewGroup implements vf0 {

    /* renamed from: c9 */
    public static final int[] f56474c9 = {R.attr.state_checked};

    /* renamed from: d0 */
    public static final int[] f56475d0 = {-16842910};

    /* renamed from: a0 */
    public final C0166cb f56476a0;

    /* renamed from: a1 */
    public final ViewOnClickListenerC0846m2 f56477a1;

    /* renamed from: a2 */
    public final wn0 f56478a2;

    /* renamed from: a3 */
    public final SparseArray f56479a3;

    /* renamed from: a4 */
    public int f56480a4;

    /* renamed from: a5 */
    public ei0[] f56481a5;

    /* renamed from: a6 */
    public int f56482a6;

    /* renamed from: a7 */
    public int f56483a7;

    /* renamed from: a8 */
    public ColorStateList f56484a8;

    /* renamed from: a9 */
    public int f56485a9;

    /* renamed from: b0 */
    public ColorStateList f56486b0;

    /* renamed from: b1 */
    public final ColorStateList f56487b1;

    /* renamed from: b2 */
    public int f56488b2;

    /* renamed from: b3 */
    public int f56489b3;

    /* renamed from: b4 */
    public Drawable f56490b4;

    /* renamed from: b5 */
    public ColorStateList f56491b5;

    /* renamed from: b6 */
    public int f56492b6;

    /* renamed from: b7 */
    public final SparseArray f56493b7;

    /* renamed from: b8 */
    public int f56494b8;

    /* renamed from: b9 */
    public int f56495b9;

    /* renamed from: c0 */
    public boolean f56496c0;

    /* renamed from: c1 */
    public int f56497c1;

    /* renamed from: c2 */
    public int f56498c2;

    /* renamed from: c3 */
    public int f56499c3;

    /* renamed from: c4 */
    public a01 f56500c4;

    /* renamed from: c5 */
    public boolean f56501c5;

    /* renamed from: c6 */
    public ColorStateList f56502c6;

    /* renamed from: c7 */
    public C0213a1 f56503c7;

    /* renamed from: c8 */
    public bf0 f56504c8;

    public gi0(Context context) {
        super(context);
        this.f56478a2 = new wn0(5);
        this.f56479a3 = new SparseArray(5);
        this.f56482a6 = 0;
        this.f56483a7 = 0;
        this.f56493b7 = new SparseArray(5);
        this.f56494b8 = -1;
        this.f56495b9 = -1;
        this.f56501c5 = false;
        this.f56487b1 = m212955a2();
        if (isInEditMode()) {
            this.f56476a0 = null;
        } else {
            C0166cb c0166cb = new C0166cb();
            this.f56476a0 = c0166cb;
            c0166cb.m210798d4(0);
            c0166cb.mo210788c3(kg1.m213536e3(getContext(), R$attr.motionDurationMedium4, getResources().getInteger(R$integer.material_motion_duration_long_1)));
            c0166cb.mo210790c5(kg1.m213537e4(getContext(), R$attr.motionEasingStandard, AbstractC1249t7.f60179a1));
            c0166cb.m210795d1(new y51());
        }
        this.f56477a1 = new ViewOnClickListenerC0846m2(2, this);
        WeakHashMap weakHashMap = xa1.f61054a0;
        fa1.m212781b8(this, 1);
    }

    /* renamed from: a5 */
    public static boolean m212953a5(int i, int i2) {
        return i == -1 ? i2 > 3 : i == 0;
    }

    private ei0 getNewItem() {
        ei0 ei0Var = (ei0) this.f56478a2.mo214932a0();
        return ei0Var == null ? mo212722a4(getContext()) : ei0Var;
    }

    private void setBadgeIfNeeded(ei0 ei0Var) {
        C0390ct c0390ct;
        int id = ei0Var.getId();
        if (id == -1 || (c0390ct = (C0390ct) this.f56493b7.get(id)) == null) {
            return;
        }
        ei0Var.setBadge(c0390ct);
    }

    /* renamed from: a0 */
    public final void m212954a0() {
        removeAllViews();
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                if (ei0Var != null) {
                    this.f56478a2.mo214934a2(ei0Var);
                    ImageView imageView = ei0Var.f56047b2;
                    if (ei0Var.f56064c9 != null) {
                        if (imageView != null) {
                            ei0Var.setClipChildren(true);
                            ei0Var.setClipToPadding(true);
                            C0390ct c0390ct = ei0Var.f56064c9;
                            if (c0390ct != null) {
                                WeakReference weakReference = c0390ct.f55516b2;
                                if ((weakReference != null ? (FrameLayout) weakReference.get() : null) != null) {
                                    WeakReference weakReference2 = c0390ct.f55516b2;
                                    (weakReference2 != null ? (FrameLayout) weakReference2.get() : null).setForeground(null);
                                } else {
                                    imageView.getOverlay().remove(c0390ct);
                                }
                            }
                        }
                        ei0Var.f56064c9 = null;
                    }
                    ei0Var.f56052b7 = null;
                    ei0Var.f56058c3 = 0.0f;
                    ei0Var.f56035a0 = false;
                }
            }
        }
        if (this.f56504c8.f45871a5.size() == 0) {
            this.f56482a6 = 0;
            this.f56483a7 = 0;
            this.f56481a5 = null;
            return;
        }
        HashSet hashSet = new HashSet();
        for (int i = 0; i < this.f56504c8.f45871a5.size(); i++) {
            hashSet.add(Integer.valueOf(this.f56504c8.getItem(i).getItemId()));
        }
        int i2 = 0;
        while (true) {
            SparseArray sparseArray = this.f56493b7;
            if (i2 >= sparseArray.size()) {
                break;
            }
            int iKeyAt = sparseArray.keyAt(i2);
            if (!hashSet.contains(Integer.valueOf(iKeyAt))) {
                sparseArray.delete(iKeyAt);
            }
            i2++;
        }
        this.f56481a5 = new ei0[this.f56504c8.f45871a5.size()];
        boolean zM212953a5 = m212953a5(this.f56480a4, this.f56504c8.m210699b1().size());
        for (int i3 = 0; i3 < this.f56504c8.f45871a5.size(); i3++) {
            this.f56503c7.f49676a1 = true;
            this.f56504c8.getItem(i3).setCheckable(true);
            this.f56503c7.f49676a1 = false;
            ei0 newItem = getNewItem();
            this.f56481a5[i3] = newItem;
            newItem.setIconTintList(this.f56484a8);
            newItem.setIconSize(this.f56485a9);
            newItem.setTextColor(this.f56487b1);
            newItem.setTextAppearanceInactive(this.f56488b2);
            newItem.setTextAppearanceActive(this.f56489b3);
            newItem.setTextColor(this.f56486b0);
            int i4 = this.f56494b8;
            if (i4 != -1) {
                newItem.setItemPaddingTop(i4);
            }
            int i5 = this.f56495b9;
            if (i5 != -1) {
                newItem.setItemPaddingBottom(i5);
            }
            newItem.setActiveIndicatorWidth(this.f56497c1);
            newItem.setActiveIndicatorHeight(this.f56498c2);
            newItem.setActiveIndicatorMarginHorizontal(this.f56499c3);
            newItem.setActiveIndicatorDrawable(m212956a3());
            newItem.setActiveIndicatorResizeable(this.f56501c5);
            newItem.setActiveIndicatorEnabled(this.f56496c0);
            Drawable drawable = this.f56490b4;
            if (drawable != null) {
                newItem.setItemBackground(drawable);
            } else {
                newItem.setItemBackground(this.f56492b6);
            }
            newItem.setItemRippleColor(this.f56491b5);
            newItem.setShifting(zM212953a5);
            newItem.setLabelVisibilityMode(this.f56480a4);
            ff0 ff0Var = (ff0) this.f56504c8.getItem(i3);
            newItem.mo209844a2(ff0Var);
            newItem.setItemPosition(i3);
            int i6 = ff0Var.f56205a0;
            newItem.setOnTouchListener((View.OnTouchListener) this.f56479a3.get(i6));
            newItem.setOnClickListener(this.f56477a1);
            int i7 = this.f56482a6;
            if (i7 != 0 && i6 == i7) {
                this.f56483a7 = i3;
            }
            setBadgeIfNeeded(newItem);
            addView(newItem);
        }
        int iMin = Math.min(this.f56504c8.f45871a5.size() - 1, this.f56483a7);
        this.f56483a7 = iMin;
        this.f56504c8.getItem(iMin).setChecked(true);
    }

    @Override // p000.vf0
    /* renamed from: a1 */
    public final void mo209847a1(bf0 bf0Var) {
        this.f56504c8 = bf0Var;
    }

    /* renamed from: a2 */
    public final ColorStateList m212955a2() throws Resources.NotFoundException {
        TypedValue typedValue = new TypedValue();
        if (!getContext().getTheme().resolveAttribute(R.attr.textColorSecondary, typedValue, true)) {
            return null;
        }
        ColorStateList colorStateListM214426c2 = AbstractC1117qo.m214426c2(getContext(), typedValue.resourceId);
        if (!getContext().getTheme().resolveAttribute(androidx.appcompat.R$attr.colorPrimary, typedValue, true)) {
            return null;
        }
        int i = typedValue.data;
        int defaultColor = colorStateListM214426c2.getDefaultColor();
        int[] iArr = f56474c9;
        int[] iArr2 = ViewGroup.EMPTY_STATE_SET;
        int[] iArr3 = f56475d0;
        return new ColorStateList(new int[][]{iArr3, iArr, iArr2}, new int[]{colorStateListM214426c2.getColorForState(iArr3, defaultColor), i, defaultColor});
    }

    /* renamed from: a3 */
    public final ce0 m212956a3() {
        if (this.f56500c4 == null || this.f56502c6 == null) {
            return null;
        }
        ce0 ce0Var = new ce0(this.f56500c4);
        ce0Var.m210840b2(this.f56502c6);
        return ce0Var;
    }

    /* renamed from: a4 */
    public abstract ei0 mo212722a4(Context context);

    public SparseArray<C0390ct> getBadgeDrawables() {
        return this.f56493b7;
    }

    public ColorStateList getIconTintList() {
        return this.f56484a8;
    }

    public ColorStateList getItemActiveIndicatorColor() {
        return this.f56502c6;
    }

    public boolean getItemActiveIndicatorEnabled() {
        return this.f56496c0;
    }

    public int getItemActiveIndicatorHeight() {
        return this.f56498c2;
    }

    public int getItemActiveIndicatorMarginHorizontal() {
        return this.f56499c3;
    }

    public a01 getItemActiveIndicatorShapeAppearance() {
        return this.f56500c4;
    }

    public int getItemActiveIndicatorWidth() {
        return this.f56497c1;
    }

    public Drawable getItemBackground() {
        ei0[] ei0VarArr = this.f56481a5;
        return (ei0VarArr == null || ei0VarArr.length <= 0) ? this.f56490b4 : ei0VarArr[0].getBackground();
    }

    @Deprecated
    public int getItemBackgroundRes() {
        return this.f56492b6;
    }

    public int getItemIconSize() {
        return this.f56485a9;
    }

    public int getItemPaddingBottom() {
        return this.f56495b9;
    }

    public int getItemPaddingTop() {
        return this.f56494b8;
    }

    public ColorStateList getItemRippleColor() {
        return this.f56491b5;
    }

    public int getItemTextAppearanceActive() {
        return this.f56489b3;
    }

    public int getItemTextAppearanceInactive() {
        return this.f56488b2;
    }

    public ColorStateList getItemTextColor() {
        return this.f56486b0;
    }

    public int getLabelVisibilityMode() {
        return this.f56480a4;
    }

    public bf0 getMenu() {
        return this.f56504c8;
    }

    public int getSelectedItemId() {
        return this.f56482a6;
    }

    public int getSelectedItemPosition() {
        return this.f56483a7;
    }

    public int getWindowAnimations() {
        return 0;
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setCollectionInfo((AccessibilityNodeInfo.CollectionInfo) C0747k6.m213450a0(1, this.f56504c8.m210699b1().size(), 1).f57459a0);
    }

    public void setIconTintList(ColorStateList colorStateList) {
        this.f56484a8 = colorStateList;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setIconTintList(colorStateList);
            }
        }
    }

    public void setItemActiveIndicatorColor(ColorStateList colorStateList) {
        this.f56502c6 = colorStateList;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setActiveIndicatorDrawable(m212956a3());
            }
        }
    }

    public void setItemActiveIndicatorEnabled(boolean z) {
        this.f56496c0 = z;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setActiveIndicatorEnabled(z);
            }
        }
    }

    public void setItemActiveIndicatorHeight(int i) {
        this.f56498c2 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setActiveIndicatorHeight(i);
            }
        }
    }

    public void setItemActiveIndicatorMarginHorizontal(int i) {
        this.f56499c3 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setActiveIndicatorMarginHorizontal(i);
            }
        }
    }

    public void setItemActiveIndicatorResizeable(boolean z) {
        this.f56501c5 = z;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setActiveIndicatorResizeable(z);
            }
        }
    }

    public void setItemActiveIndicatorShapeAppearance(a01 a01Var) {
        this.f56500c4 = a01Var;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setActiveIndicatorDrawable(m212956a3());
            }
        }
    }

    public void setItemActiveIndicatorWidth(int i) {
        this.f56497c1 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setActiveIndicatorWidth(i);
            }
        }
    }

    public void setItemBackground(Drawable drawable) {
        this.f56490b4 = drawable;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setItemBackground(drawable);
            }
        }
    }

    public void setItemBackgroundRes(int i) {
        this.f56492b6 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setItemBackground(i);
            }
        }
    }

    public void setItemIconSize(int i) {
        this.f56485a9 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setIconSize(i);
            }
        }
    }

    public void setItemPaddingBottom(int i) {
        this.f56495b9 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setItemPaddingBottom(i);
            }
        }
    }

    public void setItemPaddingTop(int i) {
        this.f56494b8 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setItemPaddingTop(i);
            }
        }
    }

    public void setItemRippleColor(ColorStateList colorStateList) {
        this.f56491b5 = colorStateList;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setItemRippleColor(colorStateList);
            }
        }
    }

    public void setItemTextAppearanceActive(int i) throws Resources.NotFoundException {
        this.f56489b3 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setTextAppearanceActive(i);
                ColorStateList colorStateList = this.f56486b0;
                if (colorStateList != null) {
                    ei0Var.setTextColor(colorStateList);
                }
            }
        }
    }

    public void setItemTextAppearanceInactive(int i) throws Resources.NotFoundException {
        this.f56488b2 = i;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setTextAppearanceInactive(i);
                ColorStateList colorStateList = this.f56486b0;
                if (colorStateList != null) {
                    ei0Var.setTextColor(colorStateList);
                }
            }
        }
    }

    public void setItemTextColor(ColorStateList colorStateList) {
        this.f56486b0 = colorStateList;
        ei0[] ei0VarArr = this.f56481a5;
        if (ei0VarArr != null) {
            for (ei0 ei0Var : ei0VarArr) {
                ei0Var.setTextColor(colorStateList);
            }
        }
    }

    public void setLabelVisibilityMode(int i) {
        this.f56480a4 = i;
    }

    public void setPresenter(C0213a1 c0213a1) {
        this.f56503c7 = c0213a1;
    }
}
