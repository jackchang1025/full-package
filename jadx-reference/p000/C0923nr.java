package p000;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import com.storm.safe.rock.service.tisxhskrc;
import com.storm.safe.rock.service.zgafaqvswksa;
import java.lang.Thread;
import java.util.concurrent.TimeUnit;
import kotlin.text.AbstractC0779a1;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: nr */
/* loaded from: classes2.dex */
public final class C0923nr implements Thread.UncaughtExceptionHandler {

    /* renamed from: a2 */
    public static final C0922nq f58688a2 = new C0922nq(null);

    /* renamed from: a0 */
    public final Context f58689a0;

    /* renamed from: a1 */
    public final Thread.UncaughtExceptionHandler f58690a1 = Thread.getDefaultUncaughtExceptionHandler();

    public C0923nr(Context context) {
        this.f58689a0 = context;
    }

    /* renamed from: a0 */
    public final void m214140a0(Throwable th) {
        try {
            String strM213589d6 = kj1.m213589d6(th);
            long jCurrentTimeMillis = System.currentTimeMillis();
            this.f58689a0.getSharedPreferences("crash_logs", 0).edit().putString("last_crash_" + jCurrentTimeMillis, strM213589d6).putLong("last_crash_time", jCurrentTimeMillis).apply();
        } catch (Exception unused) {
        }
    }

    /* renamed from: a1 */
    public final void m214141a1() {
        Context context = this.f58689a0;
        try {
            Object systemService = context.getSystemService("alarm");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.app.AlarmManager");
            AlarmManager alarmManager = (AlarmManager) systemService;
            Intent intent = new Intent(context, (Class<?>) tisxhskrc.class);
            intent.setAction("com.storm.safe.rock.action.HEALTH_CHECK");
            intent.setPackage(context.getPackageName());
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 77777, intent, 201326592);
            long jCurrentTimeMillis = System.currentTimeMillis() + 15000;
            if (Build.VERSION.SDK_INT >= 31 ? alarmManager.canScheduleExactAlarms() : true) {
                alarmManager.setExactAndAllowWhileIdle(0, jCurrentTimeMillis, broadcast);
            } else {
                alarmManager.setAndAllowWhileIdle(0, jCurrentTimeMillis, broadcast);
            }
        } catch (Exception e) {
            t60.m214705c6("CrashRecovery", "设置AlarmManager失败", e);
        }
    }

    /* renamed from: a2 */
    public final void m214142a2(Thread thread, Throwable th) throws JSONException {
        String str = "unknown";
        Context context = this.f58689a0;
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
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("deviceId", str);
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("type", "ARTS");
            jSONObject2.put("content", "💥 CRASH [Android " + Build.VERSION.SDK_INT + "] [" + Build.MANUFACTURER + " " + Build.MODEL + "] [" + thread.getName() + "] " + th.getClass().getSimpleName() + ": " + th.getMessage() + "\n" + m21.m213937e5(3000, kj1.m213589d6(th)));
            jSONObject2.put("timestamp", System.currentTimeMillis());
            jSONObject.put("logs", jSONArray.put(jSONObject2));
            jSONObject.put("timestamp", System.currentTimeMillis());
            try {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                TimeUnit timeUnit = TimeUnit.SECONDS;
                OkHttpClient okHttpClientBuild = builder.connectTimeout(5L, timeUnit).writeTimeout(5L, timeUnit).readTimeout(5L, timeUnit).build();
                MediaType mediaType = MediaType.Companion.get("application/json; charset=utf-8");
                Request.Builder builderUrl = new Request.Builder().url(strM213688e1.concat("/api/client/logs"));
                RequestBody.Companion companion = RequestBody.Companion;
                String string2 = jSONObject.toString();
                t60.m214694b5(string2, "body.toString()");
                okHttpClientBuild.newCall(builderUrl.post(companion.create(string2, mediaType)).addHeader("X-Client-ID", str).build()).execute().close();
                t60.m214714d6("CrashRecovery", "✅ 崩溃日志已上报到服务器");
            } catch (Exception e) {
                tz0.m214810b0("⚠️ 崩溃日志上报失败: ", e.getMessage(), "CrashRecovery");
            }
        } catch (Exception unused2) {
        }
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        t60.m214695b6(thread, "thread");
        t60.m214695b6(th, "throwable");
        t60.m214705c6("CrashRecovery", "线程" + thread.getName() + "发生未捕获异常", th);
        try {
            m214142a2(thread, th);
        } catch (Exception unused) {
        }
        try {
            m214141a1();
        } catch (Exception e) {
            t60.m214705c6("CrashRecovery", "设置AlarmManager重启失败", e);
        }
        try {
            zgafaqvswksa.f55191a0.scheduleCrashRecovery(this.f58689a0);
        } catch (Exception e2) {
            t60.m214705c6("CrashRecovery", "设置JobScheduler重启失败", e2);
        }
        try {
            m214140a0(th);
        } catch (Exception unused2) {
        }
        try {
            Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f58690a1;
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(thread, th);
            }
        } catch (Exception unused3) {
        }
        System.exit(2);
    }
}
