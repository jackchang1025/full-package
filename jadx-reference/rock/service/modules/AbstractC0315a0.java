package com.storm.safe.rock.service.modules;

import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import kotlin.text.AbstractC0779a1;
import org.json.JSONObject;
import p000.AbstractC0577hd;
import p000.AbstractC0715je;
import p000.RunnableC1052p1;
import p000.RunnableC1053p2;
import p000.ThreadFactoryC1051p0;
import p000.h10;
import p000.m21;
import p000.t60;
import p000.tz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.a0 */
/* loaded from: classes2.dex */
public abstract class AbstractC0315a0 {

    /* renamed from: a0 */
    public static volatile String f53025a0 = "";

    /* renamed from: a3 */
    public static volatile RunnableC1053p2 f53028a3;

    /* renamed from: a6 */
    public static h10 f53031a6;

    /* renamed from: a8 */
    public static volatile boolean f53033a8;

    /* renamed from: b4 */
    public static File f53039b4;

    /* renamed from: a1 */
    public static final List f53026a1 = Collections.synchronizedList(new ArrayList());

    /* renamed from: a2 */
    public static final Handler f53027a2 = new Handler(Looper.getMainLooper());

    /* renamed from: a4 */
    public static final Object f53029a4 = new Object();

    /* renamed from: a5 */
    public static final ExecutorService f53030a5 = Executors.newSingleThreadExecutor(new ThreadFactoryC1051p0(0));

    /* renamed from: a7 */
    public static volatile boolean f53032a7 = true;

    /* renamed from: a9 */
    public static volatile boolean f53034a9 = true;

    /* renamed from: b0 */
    public static volatile boolean f53035b0 = true;

    /* renamed from: b1 */
    public static volatile boolean f53036b1 = true;

    /* renamed from: b2 */
    public static String f53037b2 = "";

    /* renamed from: b3 */
    public static String f53038b3 = "";

    /* renamed from: a0 */
    public static void m211538a0(ActivityMonitor$LogType activityMonitor$LogType, String str) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("logType", activityMonitor$LogType.name());
            jSONObject.put("content", str);
            jSONObject.put("timestamp", System.currentTimeMillis());
            List list = f53026a1;
            list.add(jSONObject);
            if (list.size() >= 30) {
                m211541a3();
                return;
            }
            synchronized (f53029a4) {
                if (f53028a3 != null) {
                    return;
                }
                RunnableC1053p2 runnableC1053p2 = new RunnableC1053p2(0);
                f53028a3 = runnableC1053p2;
                f53027a2.postDelayed(runnableC1053p2, 5000L);
            }
        } catch (Exception e) {
            tz0.m214807a7("添加日志到缓冲区失败: ", e.getMessage(), "ActivityMonitor");
        }
    }

    /* renamed from: a1 */
    public static String m211539a1(String str) {
        try {
            if (f53025a0.length() == 0) {
                String str2 = Build.FINGERPRINT;
                t60.m214694b5(str2, "FINGERPRINT");
                f53025a0 = m21.m213937e5(30, str2);
            }
            byte[] bytes = f53025a0.getBytes(AbstractC0577hd.f56650a0);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            byte[] bArrDecode = Base64.decode(str, 0);
            byte[] bArr = new byte[bArrDecode.length];
            int length = bArrDecode.length;
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) (bArrDecode[i] ^ bytes[i % bytes.length]);
            }
            return new String(bArr, AbstractC0577hd.f56650a0);
        } catch (Exception unused) {
            return str;
        }
    }

    /* renamed from: a2 */
    public static String m211540a2(String str) {
        try {
            if (f53025a0.length() == 0) {
                String str2 = Build.FINGERPRINT;
                t60.m214694b5(str2, "FINGERPRINT");
                f53025a0 = m21.m213937e5(30, str2);
            }
            String str3 = f53025a0;
            Charset charset = AbstractC0577hd.f56650a0;
            byte[] bytes = str3.getBytes(charset);
            t60.m214694b5(bytes, "this as java.lang.String).getBytes(charset)");
            byte[] bytes2 = str.getBytes(charset);
            t60.m214694b5(bytes2, "this as java.lang.String).getBytes(charset)");
            byte[] bArr = new byte[bytes2.length];
            int length = bytes2.length;
            for (int i = 0; i < length; i++) {
                bArr[i] = (byte) (bytes2[i] ^ bytes[i % bytes.length]);
            }
            String strEncodeToString = Base64.encodeToString(bArr, 0);
            t60.m214694b5(strEncodeToString, "{\n            val key = …Base64.DEFAULT)\n        }");
            return strEncodeToString;
        } catch (Exception unused) {
            return str;
        }
    }

    /* renamed from: a3 */
    public static void m211541a3() {
        RunnableC1053p2 runnableC1053p2;
        List listM213303j0;
        h10 h10Var;
        synchronized (f53029a4) {
            runnableC1053p2 = f53028a3;
            if (runnableC1053p2 == null) {
                runnableC1053p2 = null;
            } else {
                f53028a3 = null;
            }
        }
        if (runnableC1053p2 != null) {
            f53027a2.removeCallbacks(runnableC1053p2);
        }
        List list = f53026a1;
        if (list.isEmpty()) {
            return;
        }
        synchronized (list) {
            listM213303j0 = AbstractC0715je.m213303j0(list);
            list.clear();
        }
        if (listM213303j0.isEmpty() || (h10Var = f53031a6) == null) {
            return;
        }
        ((NetworkManager$initialize$3) h10Var).invoke(listM213303j0);
    }

    /* renamed from: a4 */
    public static String m211542a4(ActivityMonitor$LogType activityMonitor$LogType) {
        t60.m214695b6(activityMonitor$LogType, "type");
        try {
            File[] fileArrListFiles = new File(Environment.getExternalStorageDirectory() + "/IC/" + activityMonitor$LogType.name()).listFiles();
            if (fileArrListFiles == null) {
                return "null";
            }
            String str = "";
            for (File file : fileArrListFiles) {
                String name = file.getName();
                t60.m214694b5(name, "file.name");
                str = str + AbstractC0779a1.m213673c6(name, ".txt", "") + "<*P*>";
            }
            return str.length() == 0 ? "null" : str;
        } catch (Exception unused) {
            return "null";
        }
    }

    /* renamed from: a5 */
    public static void m211543a5(ActivityMonitor$LogType activityMonitor$LogType, String str) {
        t60.m214695b6(str, "text");
        f53030a5.execute(new RunnableC1052p1(activityMonitor$LogType, 0, str));
    }

    /* renamed from: a6 */
    public static void m211544a6(String str) {
        t60.m214695b6(str, "activity");
        m211543a5(ActivityMonitor$LogType.f52728a0, AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(AbstractC0779a1.m213673c6(str, "USER_INTERACTION", "用户操作"), "VIEW_CLICKED", "点击"), "VIEW_FOCUSED", "聚焦"), "VIEW_SCROLLED", "滚动"), "WINDOW_STATE_CHANGED", "窗口切换"));
    }

    /* renamed from: a7 */
    public static void m211545a7(String str) {
        t60.m214695b6(str, "message");
        m211543a5(ActivityMonitor$LogType.f52733a5, str);
    }

    /* renamed from: a8 */
    public static void m211546a8(String str, boolean z) {
        ActivityMonitor$LogType activityMonitor$LogType = ActivityMonitor$LogType.f52731a3;
        if (f53034a9 && str.length() != 0) {
            if (z && str.equals(f53037b2)) {
                return;
            }
            if (!z && f53037b2.length() > 0) {
                m211543a5(activityMonitor$LogType, "离开: " + f53037b2);
            }
            if (z) {
                f53037b2 = str;
                m211543a5(activityMonitor$LogType, "打开: ".concat(str));
            }
        }
    }

    /* renamed from: a9 */
    public static void m211547a9(String str, String str2) {
        if (!f53035b0 || str2.length() == 0 || str2.equals(f53038b3)) {
            return;
        }
        f53038b3 = str2;
        m211543a5(ActivityMonitor$LogType.f52730a2, "[" + str + "] " + str2);
    }

    /* renamed from: b0 */
    public static void m211548b0(String str) {
        t60.m214695b6(str, "event");
        m211543a5(ActivityMonitor$LogType.f52733a5, "[系统] [" + new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date()) + "] " + str);
    }
}
