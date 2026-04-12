package p000;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import androidx.appcompat.widget.C0041a1;
import androidx.appcompat.widget.Toolbar;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class f71 implements InterfaceC1119qq {

    /* renamed from: a0 */
    public Toolbar f56159a0;

    /* renamed from: a1 */
    public int f56160a1;

    /* renamed from: a2 */
    public View f56161a2;

    /* renamed from: a3 */
    public Drawable f56162a3;

    /* renamed from: a4 */
    public Drawable f56163a4;

    /* renamed from: a5 */
    public Drawable f56164a5;

    /* renamed from: a6 */
    public boolean f56165a6;

    /* renamed from: a7 */
    public CharSequence f56166a7;

    /* renamed from: a8 */
    public CharSequence f56167a8;

    /* renamed from: a9 */
    public CharSequence f56168a9;

    /* renamed from: b0 */
    public Window.Callback f56169b0;

    /* renamed from: b1 */
    public boolean f56170b1;

    /* renamed from: b2 */
    public C0041a1 f56171b2;

    /* renamed from: b3 */
    public int f56172b3;

    /* renamed from: b4 */
    public Drawable f56173b4;

    /* renamed from: a0 */
    public final void m212754a0(int i) {
        View view;
        Toolbar toolbar = this.f56159a0;
        int i2 = this.f56160a1 ^ i;
        this.f56160a1 = i;
        if (i2 != 0) {
            if ((i2 & 4) != 0) {
                if ((i & 4) != 0) {
                    m212755a1();
                }
                if ((this.f56160a1 & 4) != 0) {
                    Drawable drawable = this.f56164a5;
                    if (drawable == null) {
                        drawable = this.f56173b4;
                    }
                    toolbar.setNavigationIcon(drawable);
                } else {
                    toolbar.setNavigationIcon((Drawable) null);
                }
            }
            if ((i2 & 3) != 0) {
                m212756a2();
            }
            if ((i2 & 8) != 0) {
                if ((i & 8) != 0) {
                    toolbar.setTitle(this.f56166a7);
                    toolbar.setSubtitle(this.f56167a8);
                } else {
                    toolbar.setTitle((CharSequence) null);
                    toolbar.setSubtitle((CharSequence) null);
                }
            }
            if ((i2 & 16) == 0 || (view = this.f56161a2) == null) {
                return;
            }
            if ((i & 16) != 0) {
                toolbar.addView(view);
            } else {
                toolbar.removeView(view);
            }
        }
    }

    /* renamed from: a1 */
    public final void m212755a1() {
        Toolbar toolbar = this.f56159a0;
        if ((this.f56160a1 & 4) != 0) {
            if (TextUtils.isEmpty(this.f56168a9)) {
                toolbar.setNavigationContentDescription(this.f56172b3);
            } else {
                toolbar.setNavigationContentDescription(this.f56168a9);
            }
        }
    }

    /* renamed from: a2 */
    public final void m212756a2() {
        Drawable drawable;
        int i = this.f56160a1;
        if ((i & 2) == 0) {
            drawable = null;
        } else if ((i & 1) == 0 || (drawable = this.f56163a4) == null) {
            drawable = this.f56162a3;
        }
        this.f56159a0.setLogo(drawable);
    }
}
