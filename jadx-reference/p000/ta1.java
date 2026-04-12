package p000;

import android.view.ContentInfo;
import android.view.View;
import java.util.Objects;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public abstract class ta1 {
    /* renamed from: a0 */
    public static String[] m214730a0(View view) {
        return view.getReceiveContentMimeTypes();
    }

    /* renamed from: a1 */
    public static C0862mi m214731a1(View view, C0862mi c0862mi) {
        ContentInfo contentInfoMo213997a8 = c0862mi.f58373a0.mo213997a8();
        Objects.requireNonNull(contentInfoMo213997a8);
        ContentInfo contentInfoM213979a6 = AbstractC0858me.m213979a6(contentInfoMo213997a8);
        ContentInfo contentInfoPerformReceiveContent = view.performReceiveContent(contentInfoM213979a6);
        if (contentInfoPerformReceiveContent == null) {
            return null;
        }
        return contentInfoPerformReceiveContent == contentInfoM213979a6 ? c0862mi : new C0862mi(new tg0(contentInfoPerformReceiveContent));
    }

    /* renamed from: a2 */
    public static void m214732a2(View view, String[] strArr, cl0 cl0Var) {
        if (cl0Var == null) {
            view.setOnReceiveContentListener(strArr, null);
        } else {
            view.setOnReceiveContentListener(strArr, new ua1(cl0Var));
        }
    }
}
