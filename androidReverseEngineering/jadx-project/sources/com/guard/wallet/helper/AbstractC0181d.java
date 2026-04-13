package com.guard.wallet.helper;

import a1.AbstractC0026q;
import android.util.Log;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/* renamed from: com.guard.wallet.helper.d */
/* loaded from: classes.dex */
public abstract class AbstractC0181d {

    /* renamed from: a */
    public static final ConcurrentHashMap f201a = new ConcurrentHashMap();

    /* renamed from: a */
    public static void m345a() {
        try {
            ConcurrentHashMap concurrentHashMap = f201a;
            if (concurrentHashMap.isEmpty()) {
                return;
            }
            concurrentHashMap.keySet().forEach(new C0180c());
            concurrentHashMap.clear();
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.d", e2);
        }
    }

    /* renamed from: b */
    public static void m346b(String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            ConcurrentHashMap concurrentHashMap = f201a;
            ConcurrentLinkedQueue concurrentLinkedQueue = (ConcurrentLinkedQueue) concurrentHashMap.get(str);
            if (concurrentLinkedQueue != null && !concurrentLinkedQueue.isEmpty()) {
                Log.d("com.guard.wallet.helper.d", "归还委托节点:" + str);
                concurrentLinkedQueue.removeIf(new C0179b(0));
                concurrentLinkedQueue.clear();
            }
            concurrentHashMap.remove(str);
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.helper.d", e2);
        }
    }
}
