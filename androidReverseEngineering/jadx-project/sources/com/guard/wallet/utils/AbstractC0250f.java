package com.guard.wallet.utils;

import a1.AbstractC0026q;
import com.google.json.Gson;
import com.google.json.reflect.TypeToken;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: com.guard.wallet.utils.f */
/* loaded from: classes.dex */
public abstract class AbstractC0250f {

    /* renamed from: a */
    public static final ConcurrentHashMap f410a = new ConcurrentHashMap();

    /* renamed from: b */
    public static final AtomicBoolean f411b = new AtomicBoolean(false);

    /* renamed from: a */
    public static void m626a() {
        ConcurrentHashMap concurrentHashMap = f410a;
        if (concurrentHashMap.keySet().isEmpty()) {
            String i02 = AbstractC0251g.i0();
            if (AbstractC0026q.m151B(i02)) {
                return;
            }
            String concat = i02.concat("/").concat("locateValues.json");
            if (AbstractC0026q.m190w(concat)) {
                String m160K = AbstractC0026q.m160K(concat);
                if (AbstractC0026q.m151B(m160K)) {
                    return;
                }
                HashMap hashMap = (HashMap) new Gson().fromJson(m160K, new TypeToken<HashMap<String, String>>() { // from class: com.guard.wallet.utils.LocateValuesUtils$1
                }.getType());
                if (hashMap == null || hashMap.keySet().isEmpty()) {
                    return;
                }
                concurrentHashMap.putAll(hashMap);
            }
        }
    }

    /* renamed from: b */
    public static String m627b(String str) {
        if (AbstractC0026q.m151B(str)) {
            return BuildConfig.FLAVOR;
        }
        try {
            ConcurrentHashMap concurrentHashMap = f410a;
            if (concurrentHashMap.keySet().isEmpty()) {
                m626a();
            }
            return (String) concurrentHashMap.get(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("LocateValuesUtils", e2);
            return BuildConfig.FLAVOR;
        }
    }
}
