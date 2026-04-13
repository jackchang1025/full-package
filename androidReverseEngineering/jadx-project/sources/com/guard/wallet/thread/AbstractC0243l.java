package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.util.Log;
import com.guard.wallet.helper.C0179b;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.guard.wallet.thread.l */
/* loaded from: classes.dex */
public abstract class AbstractC0243l {

    /* renamed from: a */
    public static final ThreadPoolExecutor f391a = new ThreadPoolExecutor(10, 30, 5, TimeUnit.SECONDS, new LinkedBlockingQueue());

    /* renamed from: b */
    public static final ConcurrentHashMap f392b = new ConcurrentHashMap();

    /* renamed from: c */
    public static final ConcurrentHashMap f393c = new ConcurrentHashMap();

    /* renamed from: a */
    public static void m591a(String str) {
        try {
            ConcurrentLinkedQueue concurrentLinkedQueue = (ConcurrentLinkedQueue) f392b.get(str);
            if (concurrentLinkedQueue == null || concurrentLinkedQueue.isEmpty()) {
                return;
            }
            concurrentLinkedQueue.removeIf(new C0179b(1));
            concurrentLinkedQueue.clear();
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.thread.l", e2);
        }
    }

    /* renamed from: b */
    public static Future m592b(Callable callable, String str) {
        try {
            Future submit = f391a.submit(callable);
            ConcurrentHashMap concurrentHashMap = f392b;
            ConcurrentLinkedQueue concurrentLinkedQueue = (ConcurrentLinkedQueue) concurrentHashMap.get(str);
            if (concurrentLinkedQueue == null) {
                concurrentLinkedQueue = new ConcurrentLinkedQueue();
            }
            concurrentLinkedQueue.add(submit);
            concurrentHashMap.put(str, concurrentLinkedQueue);
            return submit;
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.thread.l", e2);
            return null;
        }
    }

    /* renamed from: c */
    public static void m593c(Runnable runnable, String str) {
        try {
            Future<?> submit = f391a.submit(runnable);
            ConcurrentHashMap concurrentHashMap = f392b;
            ConcurrentLinkedQueue concurrentLinkedQueue = (ConcurrentLinkedQueue) concurrentHashMap.get(str);
            if (concurrentLinkedQueue == null) {
                concurrentLinkedQueue = new ConcurrentLinkedQueue();
            }
            concurrentLinkedQueue.add(submit);
            concurrentHashMap.put(str, concurrentLinkedQueue);
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.thread.l", e2);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x002b, code lost:
    
        r2.remove(r6);
     */
    /* renamed from: d */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void m594d(CallableC0244m callableC0244m, String str) {
        try {
            if (AbstractC0026q.m151B(str)) {
                return;
            }
            ConcurrentHashMap concurrentHashMap = f393c;
            Future future = (Future) concurrentHashMap.get(str);
            if (future != null && !future.isDone() && !future.isCancelled()) {
                Log.d("com.guard.wallet.thread.l", str.concat(" is running"));
                return;
            }
            concurrentHashMap.put(str, f391a.submit(callableC0244m));
        } catch (Exception e2) {
            AbstractC0026q.m186s("com.guard.wallet.thread.l", e2);
        }
    }
}
