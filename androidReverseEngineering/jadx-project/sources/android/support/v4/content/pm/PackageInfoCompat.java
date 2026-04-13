package android.support.v4.content.pm;

import android.content.pm.PackageInfo;
import android.os.Build;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public final class PackageInfoCompat {
    private PackageInfoCompat() {
    }

    public static long getLongVersionCode(@NonNull PackageInfo packageInfo) {
        long longVersionCode;
        if (Build.VERSION.SDK_INT < 28) {
            return packageInfo.versionCode;
        }
        longVersionCode = packageInfo.getLongVersionCode();
        return longVersionCode;
    }
}
