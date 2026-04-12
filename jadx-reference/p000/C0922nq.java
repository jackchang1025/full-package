package p000;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.provider.Settings;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import kotlin.text.AbstractC0779a1;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nq */
/* loaded from: classes2.dex */
public final class C0922nq {
    public /* synthetic */ C0922nq(AbstractC1120qr abstractC1120qr) {
        this();
    }

    private final void uploadPendingCrashLogs(Context context) {
        new Thread(new RunnableC1322v2(context, 1)).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void uploadPendingCrashLogs$lambda$4(Context context) {
        Iterator it;
        SharedPreferences sharedPreferences;
        String str = "unknown";
        t60.m214695b6(context, "$context");
        try {
            SharedPreferences sharedPreferences2 = context.getSharedPreferences("crash_logs", 0);
            long j = sharedPreferences2.getLong("last_crash_time", 0L);
            if (j == 0) {
                return;
            }
            try {
                String strM213688e1 = AbstractC0779a1.m213688e1(hz0.m213094a0(context), '/');
                if (strM213688e1.length() == 0) {
                    return;
                }
                try {
                    String string = Settings.Secure.getString(context.getContentResolver(), "android_id");
                    if (string != null) {
                        str = string;
                    }
                } catch (Exception unused) {
                }
                Map<String, ?> all = sharedPreferences2.getAll();
                t60.m214694b5(all, "allEntries");
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry<String, ?> entry : all.entrySet()) {
                    String key = entry.getKey();
                    t60.m214694b5(key, "it.key");
                    if (AbstractC0779a1.m213679d2(key, false, "last_crash_") && !t60.m214686a2(entry.getKey(), "last_crash_time")) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                if (linkedHashMap.isEmpty()) {
                    return;
                }
                JSONArray jSONArray = new JSONArray();
                Iterator it2 = linkedHashMap.entrySet().iterator();
                while (it2.hasNext()) {
                    Object value = ((Map.Entry) it2.next()).getValue();
                    String str2 = value instanceof String ? (String) value : null;
                    if (str2 != null) {
                        String strM213937e5 = m21.m213937e5(3000, str2);
                        JSONObject jSONObject = new JSONObject();
                        jSONObject.put("type", "ARTS");
                        int i = Build.VERSION.SDK_INT;
                        String str3 = Build.MANUFACTURER;
                        String str4 = Build.MODEL;
                        it = it2;
                        StringBuilder sb = new StringBuilder();
                        sharedPreferences = sharedPreferences2;
                        sb.append("💥 PENDING CRASH [Android ");
                        sb.append(i);
                        sb.append("] [");
                        sb.append(str3);
                        sb.append(" ");
                        sb.append(str4);
                        sb.append("] ");
                        sb.append(strM213937e5);
                        jSONObject.put("content", sb.toString());
                        jSONObject.put("timestamp", j);
                        jSONArray.put(jSONObject);
                    } else {
                        it = it2;
                        sharedPreferences = sharedPreferences2;
                    }
                    it2 = it;
                    sharedPreferences2 = sharedPreferences;
                }
                SharedPreferences sharedPreferences3 = sharedPreferences2;
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("deviceId", str);
                jSONObject2.put("logs", jSONArray);
                jSONObject2.put("timestamp", System.currentTimeMillis());
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                TimeUnit timeUnit = TimeUnit.SECONDS;
                OkHttpClient okHttpClientBuild = builder.connectTimeout(10L, timeUnit).writeTimeout(10L, timeUnit).readTimeout(10L, timeUnit).build();
                MediaType mediaType = MediaType.Companion.get("application/json; charset=utf-8");
                Request.Builder builderUrl = new Request.Builder().url(strM213688e1 + "/api/client/logs");
                RequestBody.Companion companion = RequestBody.Companion;
                String string2 = jSONObject2.toString();
                t60.m214694b5(string2, "body.toString()");
                Response responseExecute = okHttpClientBuild.newCall(builderUrl.post(companion.create(string2, mediaType)).addHeader("X-Client-ID", str).build()).execute();
                if (responseExecute.isSuccessful()) {
                    sharedPreferences3.edit().clear().apply();
                    t60.m214714d6("CrashRecovery", "✅ 残留崩溃日志已上传并清除(" + linkedHashMap.size() + "条)");
                }
                responseExecute.close();
            } catch (Exception unused2) {
            }
        } catch (Exception e) {
            tz0.m214810b0("⚠️ 上传残留崩溃日志失败: ", e.getMessage(), "CrashRecovery");
        }
    }

    public final void install(Context context) {
        t60.m214695b6(context, "context");
        Context applicationContext = context.getApplicationContext();
        t60.m214694b5(applicationContext, "context.applicationContext");
        Thread.setDefaultUncaughtExceptionHandler(new C0923nr(applicationContext));
        Context applicationContext2 = context.getApplicationContext();
        t60.m214694b5(applicationContext2, "context.applicationContext");
        uploadPendingCrashLogs(applicationContext2);
    }

    private C0922nq() {
    }
}
