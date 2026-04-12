package p000;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.ActionProvider;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.core.internal.view.SupportMenuItem;
import java.util.ArrayList;
import okio.internal.Buffer;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class ff0 implements SupportMenuItem {

    /* renamed from: a0 */
    public final int f56205a0;

    /* renamed from: a1 */
    public final int f56206a1;

    /* renamed from: a2 */
    public final int f56207a2;

    /* renamed from: a3 */
    public final int f56208a3;

    /* renamed from: a4 */
    public CharSequence f56209a4;

    /* renamed from: a5 */
    public CharSequence f56210a5;

    /* renamed from: a6 */
    public Intent f56211a6;

    /* renamed from: a7 */
    public char f56212a7;

    /* renamed from: a9 */
    public char f56214a9;

    /* renamed from: b1 */
    public Drawable f56216b1;

    /* renamed from: b3 */
    public final bf0 f56218b3;

    /* renamed from: b4 */
    public r21 f56219b4;

    /* renamed from: b5 */
    public MenuItem.OnMenuItemClickListener f56220b5;

    /* renamed from: b6 */
    public CharSequence f56221b6;

    /* renamed from: b7 */
    public CharSequence f56222b7;

    /* renamed from: c4 */
    public int f56229c4;

    /* renamed from: c5 */
    public View f56230c5;

    /* renamed from: c6 */
    public AbstractC0904n8 f56231c6;

    /* renamed from: c7 */
    public MenuItem.OnActionExpandListener f56232c7;

    /* renamed from: a8 */
    public int f56213a8 = Buffer.SEGMENTING_THRESHOLD;

    /* renamed from: b0 */
    public int f56215b0 = Buffer.SEGMENTING_THRESHOLD;

    /* renamed from: b2 */
    public int f56217b2 = 0;

    /* renamed from: b8 */
    public ColorStateList f56223b8 = null;

    /* renamed from: b9 */
    public PorterDuff.Mode f56224b9 = null;

    /* renamed from: c0 */
    public boolean f56225c0 = false;

    /* renamed from: c1 */
    public boolean f56226c1 = false;

    /* renamed from: c2 */
    public boolean f56227c2 = false;

    /* renamed from: c3 */
    public int f56228c3 = 16;

    /* renamed from: c8 */
    public boolean f56233c8 = false;

    public ff0(bf0 bf0Var, int i, int i2, int i3, int i4, CharSequence charSequence, int i5) {
        this.f56218b3 = bf0Var;
        this.f56205a0 = i2;
        this.f56206a1 = i;
        this.f56207a2 = i3;
        this.f56208a3 = i4;
        this.f56209a4 = charSequence;
        this.f56229c4 = i5;
    }

    /* renamed from: a0 */
    public static void m212796a0(int i, int i2, String str, StringBuilder sb) {
        if ((i & i2) == i2) {
            sb.append(str);
        }
    }

    /* renamed from: a1 */
    public final Drawable m212797a1(Drawable drawable) {
        if (drawable != null && this.f56227c2 && (this.f56225c0 || this.f56226c1)) {
            drawable = drawable.mutate();
            if (this.f56225c0) {
                AbstractC1270tr.m214774a7(drawable, this.f56223b8);
            }
            if (this.f56226c1) {
                AbstractC1270tr.m214775a8(drawable, this.f56224b9);
            }
            this.f56227c2 = false;
        }
        return drawable;
    }

    /* renamed from: a2 */
    public final boolean m212798a2() {
        AbstractC0904n8 abstractC0904n8;
        if ((this.f56229c4 & 8) == 0) {
            return false;
        }
        if (this.f56230c5 == null && (abstractC0904n8 = this.f56231c6) != null) {
            this.f56230c5 = abstractC0904n8.mo212942a1(this);
        }
        return this.f56230c5 != null;
    }

    /* renamed from: a3 */
    public final void m212799a3(boolean z) {
        this.f56228c3 = (z ? 4 : 0) | (this.f56228c3 & (-5));
    }

    /* renamed from: a4 */
    public final void m212800a4(boolean z) {
        if (z) {
            this.f56228c3 |= 32;
        } else {
            this.f56228c3 &= -33;
        }
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final boolean collapseActionView() {
        if ((this.f56229c4 & 8) == 0) {
            return false;
        }
        if (this.f56230c5 == null) {
            return true;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.f56232c7;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionCollapse(this)) {
            return this.f56218b3.mo210691a3(this);
        }
        return false;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final boolean expandActionView() {
        if (!m212798a2()) {
            return false;
        }
        MenuItem.OnActionExpandListener onActionExpandListener = this.f56232c7;
        if (onActionExpandListener == null || onActionExpandListener.onMenuItemActionExpand(this)) {
            return this.f56218b3.mo210693a5(this);
        }
        return false;
    }

    @Override // android.view.MenuItem
    public final ActionProvider getActionProvider() {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.getActionProvider()");
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final View getActionView() {
        View view = this.f56230c5;
        if (view != null) {
            return view;
        }
        AbstractC0904n8 abstractC0904n8 = this.f56231c6;
        if (abstractC0904n8 == null) {
            return null;
        }
        View viewMo212942a1 = abstractC0904n8.mo212942a1(this);
        this.f56230c5 = viewMo212942a1;
        return viewMo212942a1;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final int getAlphabeticModifiers() {
        return this.f56215b0;
    }

    @Override // android.view.MenuItem
    public final char getAlphabeticShortcut() {
        return this.f56214a9;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final CharSequence getContentDescription() {
        return this.f56221b6;
    }

    @Override // android.view.MenuItem
    public final int getGroupId() {
        return this.f56206a1;
    }

    @Override // android.view.MenuItem
    public final Drawable getIcon() {
        Drawable drawable = this.f56216b1;
        if (drawable != null) {
            return m212797a1(drawable);
        }
        int i = this.f56217b2;
        if (i == 0) {
            return null;
        }
        Drawable drawableM210576b7 = b81.m210576b7(this.f56218b3.f45866a0, i);
        this.f56217b2 = 0;
        this.f56216b1 = drawableM210576b7;
        return m212797a1(drawableM210576b7);
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final ColorStateList getIconTintList() {
        return this.f56223b8;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final PorterDuff.Mode getIconTintMode() {
        return this.f56224b9;
    }

    @Override // android.view.MenuItem
    public final Intent getIntent() {
        return this.f56211a6;
    }

    @Override // android.view.MenuItem
    public final int getItemId() {
        return this.f56205a0;
    }

    @Override // android.view.MenuItem
    public final ContextMenu.ContextMenuInfo getMenuInfo() {
        return null;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final int getNumericModifiers() {
        return this.f56213a8;
    }

    @Override // android.view.MenuItem
    public final char getNumericShortcut() {
        return this.f56212a7;
    }

    @Override // android.view.MenuItem
    public final int getOrder() {
        return this.f56207a2;
    }

    @Override // android.view.MenuItem
    public final SubMenu getSubMenu() {
        return this.f56219b4;
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public final AbstractC0904n8 getSupportActionProvider() {
        return this.f56231c6;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitle() {
        return this.f56209a4;
    }

    @Override // android.view.MenuItem
    public final CharSequence getTitleCondensed() {
        CharSequence charSequence = this.f56210a5;
        return charSequence != null ? charSequence : this.f56209a4;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final CharSequence getTooltipText() {
        return this.f56222b7;
    }

    @Override // android.view.MenuItem
    public final boolean hasSubMenu() {
        return this.f56219b4 != null;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final boolean isActionViewExpanded() {
        return this.f56233c8;
    }

    @Override // android.view.MenuItem
    public final boolean isCheckable() {
        return (this.f56228c3 & 1) == 1;
    }

    @Override // android.view.MenuItem
    public final boolean isChecked() {
        return (this.f56228c3 & 2) == 2;
    }

    @Override // android.view.MenuItem
    public final boolean isEnabled() {
        return (this.f56228c3 & 16) != 0;
    }

    @Override // android.view.MenuItem
    public final boolean isVisible() {
        AbstractC0904n8 abstractC0904n8 = this.f56231c6;
        return (abstractC0904n8 == null || !abstractC0904n8.mo212943a2()) ? (this.f56228c3 & 8) == 0 : (this.f56228c3 & 8) == 0 && this.f56231c6.mo212941a0();
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public final boolean requiresActionButton() {
        return (this.f56229c4 & 2) == 2;
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public final boolean requiresOverflow() {
        return (requiresActionButton() || (this.f56229c4 & 1) == 1) ? false : true;
    }

    @Override // android.view.MenuItem
    public final MenuItem setActionProvider(ActionProvider actionProvider) {
        throw new UnsupportedOperationException("This is not supported, use MenuItemCompat.setActionProvider()");
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setActionView(View view) {
        int i;
        this.f56230c5 = view;
        this.f56231c6 = null;
        if (view != null && view.getId() == -1 && (i = this.f56205a0) > 0) {
            view.setId(i);
        }
        bf0 bf0Var = this.f56218b3;
        bf0Var.f45876b0 = true;
        bf0Var.mo210703b5(true);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c) {
        if (this.f56214a9 == c) {
            return this;
        }
        this.f56214a9 = Character.toLowerCase(c);
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setCheckable(boolean z) {
        int i = this.f56228c3;
        int i2 = (z ? 1 : 0) | (i & (-2));
        this.f56228c3 = i2;
        if (i != i2) {
            this.f56218b3.mo210703b5(false);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setChecked(boolean z) {
        int i = this.f56228c3;
        int i2 = i & 4;
        bf0 bf0Var = this.f56218b3;
        if (i2 == 0) {
            int i3 = (i & (-3)) | (z ? 2 : 0);
            this.f56228c3 = i3;
            if (i != i3) {
                bf0Var.mo210703b5(false);
            }
            return this;
        }
        ArrayList arrayList = bf0Var.f45871a5;
        int size = arrayList.size();
        bf0Var.m210712c4();
        for (int i4 = 0; i4 < size; i4++) {
            ff0 ff0Var = (ff0) arrayList.get(i4);
            if (ff0Var.f56206a1 == this.f56206a1 && (ff0Var.f56228c3 & 4) != 0 && ff0Var.isCheckable()) {
                boolean z2 = ff0Var == this;
                int i5 = ff0Var.f56228c3;
                int i6 = (z2 ? 2 : 0) | (i5 & (-3));
                ff0Var.f56228c3 = i6;
                if (i5 != i6) {
                    ff0Var.f56218b3.mo210703b5(false);
                }
            }
        }
        bf0Var.m210711c3();
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final /* bridge */ /* synthetic */ MenuItem setContentDescription(CharSequence charSequence) {
        setContentDescription(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setEnabled(boolean z) {
        if (z) {
            this.f56228c3 |= 16;
        } else {
            this.f56228c3 &= -17;
        }
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(Drawable drawable) {
        this.f56217b2 = 0;
        this.f56216b1 = drawable;
        this.f56227c2 = true;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setIconTintList(ColorStateList colorStateList) {
        this.f56223b8 = colorStateList;
        this.f56225c0 = true;
        this.f56227c2 = true;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setIconTintMode(PorterDuff.Mode mode) {
        this.f56224b9 = mode;
        this.f56226c1 = true;
        this.f56227c2 = true;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIntent(Intent intent) {
        this.f56211a6 = intent;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setNumericShortcut(char c) {
        if (this.f56212a7 == c) {
            return this;
        }
        this.f56212a7 = c;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnActionExpandListener(MenuItem.OnActionExpandListener onActionExpandListener) {
        this.f56232c7 = onActionExpandListener;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setOnMenuItemClickListener(MenuItem.OnMenuItemClickListener onMenuItemClickListener) {
        this.f56220b5 = onMenuItemClickListener;
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setShortcut(char c, char c2) {
        this.f56212a7 = c;
        this.f56214a9 = Character.toLowerCase(c2);
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final void setShowAsAction(int i) {
        int i2 = i & 3;
        if (i2 != 0 && i2 != 1 && i2 != 2) {
            throw new IllegalArgumentException("SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM, and SHOW_AS_ACTION_NEVER are mutually exclusive.");
        }
        this.f56229c4 = i;
        bf0 bf0Var = this.f56218b3;
        bf0Var.f45876b0 = true;
        bf0Var.mo210703b5(true);
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setShowAsActionFlags(int i) {
        setShowAsAction(i);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem
    public final SupportMenuItem setSupportActionProvider(AbstractC0904n8 abstractC0904n8) {
        AbstractC0904n8 abstractC0904n82 = this.f56231c6;
        if (abstractC0904n82 != null) {
            abstractC0904n82.f58463a0 = null;
        }
        this.f56230c5 = null;
        this.f56231c6 = abstractC0904n8;
        this.f56218b3.mo210703b5(true);
        AbstractC0904n8 abstractC0904n83 = this.f56231c6;
        if (abstractC0904n83 != null) {
            abstractC0904n83.mo212944a3(new tg0(26, this));
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(CharSequence charSequence) {
        this.f56209a4 = charSequence;
        this.f56218b3.mo210703b5(false);
        r21 r21Var = this.f56219b4;
        if (r21Var != null) {
            r21Var.setHeaderTitle(charSequence);
        }
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitleCondensed(CharSequence charSequence) {
        this.f56210a5 = charSequence;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final /* bridge */ /* synthetic */ MenuItem setTooltipText(CharSequence charSequence) {
        setTooltipText(charSequence);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setVisible(boolean z) {
        int i = this.f56228c3;
        int i2 = (z ? 0 : 8) | (i & (-9));
        this.f56228c3 = i2;
        if (i != i2) {
            bf0 bf0Var = this.f56218b3;
            bf0Var.f45873a7 = true;
            bf0Var.mo210703b5(true);
        }
        return this;
    }

    public final String toString() {
        CharSequence charSequence = this.f56209a4;
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final SupportMenuItem setContentDescription(CharSequence charSequence) {
        this.f56221b6 = charSequence;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final SupportMenuItem setTooltipText(CharSequence charSequence) {
        this.f56222b7 = charSequence;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setAlphabeticShortcut(char c, int i) {
        if (this.f56214a9 == c && this.f56215b0 == i) {
            return this;
        }
        this.f56214a9 = Character.toLowerCase(c);
        this.f56215b0 = KeyEvent.normalizeMetaState(i);
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setNumericShortcut(char c, int i) {
        if (this.f56212a7 == c && this.f56213a8 == i) {
            return this;
        }
        this.f56212a7 = c;
        this.f56213a8 = KeyEvent.normalizeMetaState(i);
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setShortcut(char c, char c2, int i, int i2) {
        this.f56212a7 = c;
        this.f56213a8 = KeyEvent.normalizeMetaState(i);
        this.f56214a9 = Character.toLowerCase(c2);
        this.f56215b0 = KeyEvent.normalizeMetaState(i2);
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setIcon(int i) {
        this.f56216b1 = null;
        this.f56217b2 = i;
        this.f56227c2 = true;
        this.f56218b3.mo210703b5(false);
        return this;
    }

    @Override // android.view.MenuItem
    public final MenuItem setTitle(int i) {
        setTitle(this.f56218b3.f45866a0.getString(i));
        return this;
    }

    @Override // androidx.core.internal.view.SupportMenuItem, android.view.MenuItem
    public final MenuItem setActionView(int i) {
        int i2;
        bf0 bf0Var = this.f56218b3;
        Context context = bf0Var.f45866a0;
        View viewInflate = LayoutInflater.from(context).inflate(i, (ViewGroup) new LinearLayout(context), false);
        this.f56230c5 = viewInflate;
        this.f56231c6 = null;
        if (viewInflate != null && viewInflate.getId() == -1 && (i2 = this.f56205a0) > 0) {
            viewInflate.setId(i2);
        }
        bf0Var.f45876b0 = true;
        bf0Var.mo210703b5(true);
        return this;
    }
}
