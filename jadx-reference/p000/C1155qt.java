package p000;

import android.content.pm.PackageManager;
import android.content.pm.Signature;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: qt */
/* loaded from: classes.dex */
public final class C1155qt extends C1351vv {
    @Override // p000.C1351vv
    /* renamed from: a8 */
    public final Signature[] mo214466a8(PackageManager packageManager, String str) {
        return packageManager.getPackageInfo(str, 64).signatures;
    }
}
