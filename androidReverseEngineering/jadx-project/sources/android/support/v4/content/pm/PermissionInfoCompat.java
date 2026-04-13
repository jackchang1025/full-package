package android.support.v4.content.pm;

import android.annotation.SuppressLint;
import android.content.pm.PermissionInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public final class PermissionInfoCompat {

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface Protection {
    }

    @SuppressLint({"UniqueConstants"})
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface ProtectionFlags {
    }

    private PermissionInfoCompat() {
    }

    @SuppressLint({"WrongConstant"})
    public static int getProtection(@NonNull PermissionInfo permissionInfo) {
        int protection;
        if (Build.VERSION.SDK_INT < 28) {
            return permissionInfo.protectionLevel & 15;
        }
        protection = permissionInfo.getProtection();
        return protection;
    }

    @SuppressLint({"WrongConstant"})
    public static int getProtectionFlags(@NonNull PermissionInfo permissionInfo) {
        int protectionFlags;
        if (Build.VERSION.SDK_INT < 28) {
            return permissionInfo.protectionLevel & (-16);
        }
        protectionFlags = permissionInfo.getProtectionFlags();
        return protectionFlags;
    }
}
