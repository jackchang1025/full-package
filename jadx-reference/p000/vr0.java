package p000;

import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class vr0 {

    /* renamed from: a0 */
    public final ColorStateList f60679a0;

    /* renamed from: a1 */
    public final Configuration f60680a1;

    /* renamed from: a2 */
    public final int f60681a2;

    public vr0(ColorStateList colorStateList, Configuration configuration, Resources.Theme theme) {
        this.f60679a0 = colorStateList;
        this.f60680a1 = configuration;
        this.f60681a2 = theme == null ? 0 : theme.hashCode();
    }
}
