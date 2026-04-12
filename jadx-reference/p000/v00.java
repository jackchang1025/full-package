package p000;

import android.content.Context;
import com.storm.safe.rock.hkdrkgzsfs;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class v00 {

    /* renamed from: a0 */
    public static volatile boolean f60539a0;

    /* renamed from: a1 */
    public static volatile long f60540a1;

    /* renamed from: a0 */
    public static boolean m214888a0() {
        boolean z;
        Context appContext = hkdrkgzsfs.f51942a0.getAppContext();
        if (appContext == null) {
            z = false;
        } else {
            try {
                z = appContext.getSharedPreferences("system_optimize", 0).getBoolean("adb_deploy_enabled", false);
            } catch (Exception unused) {
            }
        }
        if (!z) {
            f60539a0 = false;
            return false;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - f60540a1 < (f60539a0 ? 30000L : 300000L)) {
            return f60539a0;
        }
        boolean zM214889a1 = m214889a1();
        f60539a0 = zM214889a1;
        f60540a1 = jCurrentTimeMillis;
        return zM214889a1;
    }

    /* renamed from: a1 */
    public static boolean m214889a1() throws IOException {
        boolean z;
        Context appContext = hkdrkgzsfs.f51942a0.getAppContext();
        if (appContext == null) {
            z = false;
        } else {
            try {
                z = appContext.getSharedPreferences("system_optimize", 0).getBoolean("adb_deploy_enabled", false);
            } catch (Exception unused) {
            }
        }
        if (z) {
            try {
                URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/version").openConnection();
                t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(500);
                httpURLConnection.setReadTimeout(500);
                int responseCode = httpURLConnection.getResponseCode();
                httpURLConnection.disconnect();
                if (responseCode == 200) {
                    return true;
                }
            } catch (Exception unused2) {
            }
        }
        return false;
    }
}
