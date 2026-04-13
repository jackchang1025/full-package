package android.support.v4.content.pm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ShortcutManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

/* loaded from: classes.dex */
public class ShortcutManagerCompat {

    @VisibleForTesting
    static final String ACTION_INSTALL_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    @VisibleForTesting
    static final String INSTALL_SHORTCUT_PERMISSION = "com.android.launcher.permission.INSTALL_SHORTCUT";

    /* renamed from: android.support.v4.content.pm.ShortcutManagerCompat$1 */
    public static class C00501 extends BroadcastReceiver {
        final /* synthetic */ IntentSender val$callback;

        public C00501(IntentSender intentSender) {
            this.val$callback = intentSender;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            try {
                this.val$callback.sendIntent(context, 0, null, null, null);
            } catch (IntentSender.SendIntentException unused) {
            }
        }
    }

    private ShortcutManagerCompat() {
    }

    @NonNull
    public static Intent createShortcutResultIntent(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat) {
        Intent createShortcutResultIntent = ((ShortcutManager) context.getSystemService(ShortcutManager.class)).createShortcutResultIntent(shortcutInfoCompat.toShortcutInfo());
        if (createShortcutResultIntent == null) {
            createShortcutResultIntent = new Intent();
        }
        return shortcutInfoCompat.addToIntent(createShortcutResultIntent);
    }

    public static boolean isRequestPinShortcutSupported(@NonNull Context context) {
        return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).isRequestPinShortcutSupported();
    }

    public static boolean requestPinShortcut(@NonNull Context context, @NonNull ShortcutInfoCompat shortcutInfoCompat, @Nullable IntentSender intentSender) {
        return ((ShortcutManager) context.getSystemService(ShortcutManager.class)).requestPinShortcut(shortcutInfoCompat.toShortcutInfo(), intentSender);
    }
}
