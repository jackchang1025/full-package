package p000;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.view.LayoutInflater;
import androidx.appcompat.R$style;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: mu */
/* loaded from: classes.dex */
public final class C0875mu extends ContextWrapper {

    /* renamed from: a5 */
    public static Configuration f58397a5;

    /* renamed from: a0 */
    public int f58398a0;

    /* renamed from: a1 */
    public Resources.Theme f58399a1;

    /* renamed from: a2 */
    public LayoutInflater f58400a2;

    /* renamed from: a3 */
    public Configuration f58401a3;

    /* renamed from: a4 */
    public Resources f58402a4;

    public C0875mu(Context context, int i) {
        super(context);
        this.f58398a0 = i;
    }

    /* renamed from: a0 */
    public final void m214023a0(Configuration configuration) {
        if (this.f58402a4 != null) {
            throw new IllegalStateException("getResources() or getAssets() has already been called");
        }
        if (this.f58401a3 != null) {
            throw new IllegalStateException("Override configuration has already been set");
        }
        this.f58401a3 = new Configuration(configuration);
    }

    /* renamed from: a1 */
    public final void m214024a1() {
        if (this.f58399a1 == null) {
            this.f58399a1 = getResources().newTheme();
            Resources.Theme theme = getBaseContext().getTheme();
            if (theme != null) {
                this.f58399a1.setTo(theme);
            }
        }
        this.f58399a1.applyStyle(this.f58398a0, true);
    }

    @Override // android.content.ContextWrapper
    public final void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final AssetManager getAssets() {
        return getResources().getAssets();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0032  */
    @Override // android.content.ContextWrapper, android.content.Context
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Resources getResources() {
        if (this.f58402a4 == null) {
            Configuration configuration = this.f58401a3;
            if (configuration == null) {
                this.f58402a4 = super.getResources();
            } else {
                if (Build.VERSION.SDK_INT >= 26) {
                    if (f58397a5 == null) {
                        Configuration configuration2 = new Configuration();
                        configuration2.fontScale = 0.0f;
                        f58397a5 = configuration2;
                    }
                    if (configuration.equals(f58397a5)) {
                    }
                }
                this.f58402a4 = AbstractC0874mt.m214022a0(this, this.f58401a3).getResources();
            }
        }
        return this.f58402a4;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Object getSystemService(String str) {
        if (!"layout_inflater".equals(str)) {
            return getBaseContext().getSystemService(str);
        }
        if (this.f58400a2 == null) {
            this.f58400a2 = LayoutInflater.from(getBaseContext()).cloneInContext(this);
        }
        return this.f58400a2;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final Resources.Theme getTheme() {
        Resources.Theme theme = this.f58399a1;
        if (theme != null) {
            return theme;
        }
        if (this.f58398a0 == 0) {
            this.f58398a0 = R$style.Theme_AppCompat_Light;
        }
        m214024a1();
        return this.f58399a1;
    }

    @Override // android.content.ContextWrapper, android.content.Context
    public final void setTheme(int i) {
        if (this.f58398a0 != i) {
            this.f58398a0 = i;
            m214024a1();
        }
    }
}
