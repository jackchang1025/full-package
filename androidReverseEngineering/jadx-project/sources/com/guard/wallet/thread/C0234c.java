package com.guard.wallet.thread;

import a1.AbstractC0026q;
import android.util.Log;
import java.lang.Thread;

/* renamed from: com.guard.wallet.thread.c */
/* loaded from: classes.dex */
public final class C0234c implements Thread.UncaughtExceptionHandler {

    /* renamed from: b */
    public static volatile C0234c f355b;

    /* renamed from: a */
    public Thread.UncaughtExceptionHandler f356a;

    /* renamed from: a */
    public static C0234c m577a() {
        if (f355b == null) {
            synchronized (C0234c.class) {
                if (f355b == null) {
                    synchronized (C0234c.class) {
                        f355b = new C0234c();
                    }
                }
            }
        }
        return f355b;
    }

    @Override // java.lang.Thread.UncaughtExceptionHandler
    public final void uncaughtException(Thread thread, Throwable th) {
        boolean z2;
        if (th == null) {
            z2 = false;
        } else {
            AbstractC0026q.m187t("GlobalExceptionHandler", th);
            z2 = true;
        }
        if (z2) {
            Log.d("GlobalExceptionHandler", "全局异常已捕获");
            return;
        }
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = this.f356a;
        if (uncaughtExceptionHandler != null) {
            uncaughtExceptionHandler.uncaughtException(thread, th);
        }
    }
}
